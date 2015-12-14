package go.zlslog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class LogToModel {

	/**
	 * @throws SQLException
	 * @throws ParseException
	 * @throws IOException 
	 * 
	 */
	public static void readFileByLines(String filePath, int batchLines) throws SQLException, ParseException, IOException {
		Logger logger = Logger.getLogger(InsertToPhoenix.class);
		logger.info("start to read file");
		File file = new File(filePath);
		String logDate = filePath.substring(filePath.length() - 10);
		LineIterator it = FileUtils.lineIterator(file, "UTF-8");
		//BufferedReader reader = null;
		try {
			//reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			int line = 1;
			int batchNumber = 1;
			List<LogModel> logmodelList = new ArrayList();
			// 1万行日志开始解析时间
			while (it.hasNext()) {
				Date date = new Date();
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String StartTime = format.format(date);
				
				JSONArray jsonarray = new JSONArray("[" + it.nextLine() + "]");
				for (int i = 0; i < jsonarray.length(); i++) {
					LogModel logmodel = new LogModel();
					JSONObject jsonobj = jsonarray.getJSONObject(i);
					try {
						// params
						String params = jsonobj.getString("params");
						if (params != null && !"".equals(params)) {
							String param[] = params.split("\\\\n");
							HashMap key = new HashMap();
							if (param.length >= 0) {
								for (int j = 0; j < param.length; j++) {
									if (param[j].contains("=")) {
										key.put(param[j].split("=")[0], param[j].split("=")[1]);
									} else {
										key.put(param[j].split(":")[0], param[j].split(":")[1]);
										// logger.warn("this record params
										// format is not formated" +
										// getToString(param));
									}
								}
								String paramJson = hashMapToJson(key);
								logmodel.setParams(paramJson);
							}
						} else {
							logmodel.setParams("");
						}
						// platform & os
						String ver_full = jsonobj.getString("ver_full");
						if (ver_full == null || "".equals(ver_full)) {
							logmodel.setPlatform("web");// no need for os
						} else if (ver_full.startsWith("t")) {
							logmodel.setPlatform("tzls");
							if (ver_full.startsWith("ti")) {
								logmodel.setOs("ios");
							} else {
								logmodel.setOs("android");
							}
						} else if (ver_full.startsWith("i")) {
							logmodel.setPlatform("zls");
							logmodel.setOs("ios");
						} else if (ver_full.startsWith("a")) {
							logmodel.setPlatform("zls");
							logmodel.setOs("android");
						} else if (ver_full.startsWith("m")) {
							logmodel.setPlatform("zls");
							logmodel.setOs("tv");
						} else if (ver_full.startsWith("p")) {
							logmodel.setPlatform("zls");
							logmodel.setOs("ipad");
						} else if (ver_full.startsWith("w")) {
							logmodel.setPlatform("zls");
							logmodel.setOs("iwatch");
						} else {
							logmodel.setPlatform("unknown");
							logmodel.setOs("unknown");
						}
					} catch (Exception e) {
						logger.error("error record:" + tempString);
					}
					logmodel.setCmd(jsonobj.getString("cmd"));
					logmodel.setApp_id(jsonobj.getString("app_id"));
					logmodel.setUserid(jsonobj.getInt("userid") + "");
					logmodel.setDuration(jsonobj.getDouble("duration") + "");
					logmodel.setOk(jsonobj.getInt("ok") + "");
					logmodel.setClient_addr(jsonobj.getString("client_addr"));
					logmodel.setServer_addr(jsonobj.getString("server_addr"));
					logmodel.setVer_full(jsonobj.getString("ver_full"));
					logmodel.setVer(jsonobj.getString("ver"));
					logmodel.setVc(jsonobj.getString("vc"));
					logmodel.setVd(jsonobj.getString("vd"));
					logmodel.setDbqcnt(jsonobj.getInt("dbqcnt") + "");
					logmodel.setMccnt(jsonobj.getInt("mccnt") + "");
					logmodel.setMongocnt(jsonobj.getInt("mongocnt") + "");
					logmodel.setMemory(jsonobj.getInt("memory") + "");
					logmodel.setTrace(jsonobj.getString("trace"));
					logmodel.setVersion(jsonobj.getString("@version"));
					logmodel.setTimestamp(jsonobj.getString("@timestamp"));
					logmodel.setLine(line + "");
					logmodel.setRequest_time(jsonobj.getString("request_time"));
					logmodel.setLogDate(logDate);
					logmodelList.add(logmodel);
				}
				if (batchNumber % batchLines == 0) {
					logger.info(logDate + "one batch is finished");
					Date b = new Date();
					String endTime = format.format(b);
					InsertToPhoenix.InsertToPhoenix(logmodelList);
					Date b2 = new Date();
					String endTime2 = format.format(b2);
					logmodelList.clear();
					long diff = format.parse(endTime).getTime() - format.parse(StartTime).getTime();
					long diff2 = format.parse(endTime2).getTime() - format.parse(endTime).getTime();
					// long nd = 1000*24*60*60;//一天的毫秒数
					// long nh = 1000*60*60;//一小时的毫秒数
					// long nm = 1000*60;//一分钟的毫秒数
					// long ns = 1000;//一秒钟的毫秒数
					long sec = diff / 1000;
					logger.warn("10000 records analysis seconds:"+StartTime+"-"+endTime);
					logger.warn("10000 records insert time:"+endTime+"-"+endTime2);
				}
				line++;
				batchNumber++;
			}
			if (batchNumber % batchLines != 0) {
				logger.info(logDate + "this is for last batch ,number is :" + batchNumber);
				InsertToPhoenix.InsertToPhoenix(logmodelList);
				logmodelList.clear();
			}
			InsertToPhoenix.closeConnection();
			logger.info(logDate + "log is synchornized");
		} finally {
			/*if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}*/
			LineIterator.closeQuietly(it);
		}
	}

	public static String hashMapToJson(HashMap map) {
		String string = "{";
		for (Iterator it = map.entrySet().iterator(); it.hasNext();) {
			Entry e = (Entry) it.next();
			string += "'" + e.getKey() + "':";
			string += "'" + e.getValue() + "',";
		}
		string = string.substring(0, string.lastIndexOf(","));
		string += "}";
		return string;
	}

	public static String getToString(String param[]) {
		StringBuffer a = new StringBuffer();
		for (int i = 0; i < param.length; i++) {
			a.append(param[i] + ",");
		}
		return a.toString().substring(0, a.toString().length() - 2);
	}

	public static void main(String[] args) {
		String filePath = "e:/api_road-2015.11.04";
		String logDate = filePath.substring(filePath.length() - 10);
		System.out.println(logDate);
	}
}
