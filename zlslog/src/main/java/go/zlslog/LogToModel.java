package go.zlslog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.json.JSONArray;
import org.json.JSONObject;

public class LogToModel {

	/**
	 * @throws SQLException
	 * 
	 */
	public static void readFileByLines(String filePath, int batchLines) throws SQLException {
		Logger logger = Logger.getLogger(InsertToPhoenix.class);
		logger.info("start to read file");
		File file = new File(filePath);
		String logDate = filePath.substring(filePath.length() - 10);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			int line = 1;
			int batchNumber = 1;
			List<LogModel> logmodelList = new ArrayList();
			while ((tempString = reader.readLine()) != null) {
				JSONArray jsonarray = new JSONArray("[" + tempString + "]");
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
										logger.warn("this record params format is not formated" + param.toString());
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
					logger.info("one record:" + logmodelList.get(0).toString());
					InsertToPhoenix.InsertToPhoenix(logmodelList);
					logmodelList.clear();
				}
				line++;
				batchNumber++;
			}
			if (batchNumber % batchLines != 0) {
				logger.info(logDate + "this is for last batch ,number is :" + batchNumber);
				InsertToPhoenix.InsertToPhoenix(logmodelList);
				logmodelList.clear();
			}
			reader.close();
			logger.info(logDate + "log is synchornized");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
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

	public static void main(String[] args) {
		String filePath = "e:/api_road-2015.11.04";
		String logDate = filePath.substring(filePath.length() - 10);
		System.out.println(logDate);
	}
}
