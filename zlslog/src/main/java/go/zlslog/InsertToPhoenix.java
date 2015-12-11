package go.zlslog;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class InsertToPhoenix {

	public static void InsertToPhoenix(List<LogModel> LogModelList) {
		Logger logger = Logger.getLogger(InsertToPhoenix.class);

		String sql = "upsert into zls_log (cmd, app_id, userid,duration,ok,client_addr,server_addr,ver_full,ver,vc,"
				+ "vd,dbqcnt,mccnt,mongocnt,memory,params,trace2,version,createtimestamp,platform,os,lines,logdate,request_time)"
				+ " values (?, ?, ?,?, ?, ?,?, ?, ?,?, ?, ?,?, ?, ?,?, ?, ?,?, ?, ?,?, ?, ?)";
		try {
			Connection connection = PhoenixConnection.getInstance().GetConnection();
			PreparedStatement ps = connection.prepareStatement(sql);
			for (LogModel logmodel : LogModelList) {
				ps.setString(1, logmodel.getCmd());
				ps.setString(2, logmodel.getApp_id());
				ps.setString(3, logmodel.getUserid());
				ps.setString(4, logmodel.getDuration());
				ps.setString(5, logmodel.getOk());
				ps.setString(6, logmodel.getClient_addr());
				ps.setString(7, logmodel.getServer_addr());
				ps.setString(8, logmodel.getVer_full());
				ps.setString(9, logmodel.getVer());
				ps.setString(10, logmodel.getVc());
				ps.setString(11, logmodel.getVd());
				ps.setString(12, logmodel.getDbqcnt());
				ps.setString(13, logmodel.getMccnt());
				ps.setString(14, logmodel.getMongocnt());
				ps.setString(15, logmodel.getMemory());
				ps.setString(16, logmodel.getParams());
				ps.setString(17, logmodel.getTrace());
				ps.setString(18, logmodel.getVersion());
				ps.setString(19, logmodel.getTimestamp());
				ps.setString(20, logmodel.getPlatform());
				ps.setString(21, logmodel.getOs());
				ps.setString(22, logmodel.getLine());
				ps.setString(23, logmodel.getLogDate());
				ps.setString(24, logmodel.getRequest_time());
				ps.addBatch();
			}
			ps.executeBatch();
			ps.close();
			connection.close();
		} catch (SQLException ex) {
			logger.error("SQL error", ex);
			logger.error("error sql is " + sql);
		}
	}

}
