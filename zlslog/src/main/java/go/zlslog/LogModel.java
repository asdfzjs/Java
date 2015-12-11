package go.zlslog;

public class LogModel {

	private String cmd;
	private String app_id;
	private String userid;
	private String duration;
	private String ok;
	private String client_addr;
	private String server_addr;
	private String ver_full;
	private String ver;
	private String vc;
	private String vd;
	private String dbqcnt;
	private String mccnt;
	private String mongocnt;
	private String memory;
	private String params; // json
	private String trace;
	private String version;
	private String timestamp; // times

	private String platform;
	private String os;

	// key
	private String line;
	private String logDate;
	private String request_time;

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public String getApp_id() {
		return app_id;
	}

	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getOk() {
		return ok;
	}

	public void setOk(String ok) {
		this.ok = ok;
	}

	public String getClient_addr() {
		return client_addr;
	}

	public void setClient_addr(String client_addr) {
		this.client_addr = client_addr;
	}

	public String getServer_addr() {
		return server_addr;
	}

	public void setServer_addr(String server_addr) {
		this.server_addr = server_addr;
	}

	public String getVer_full() {
		return ver_full;
	}

	public void setVer_full(String ver_full) {
		this.ver_full = ver_full;
	}

	public String getVer() {
		return ver;
	}

	public void setVer(String ver) {
		this.ver = ver;
	}

	public String getVc() {
		return vc;
	}

	public void setVc(String vc) {
		this.vc = vc;
	}

	public String getVd() {
		return vd;
	}

	public void setVd(String vd) {
		this.vd = vd;
	}

	public String getRequest_time() {
		return request_time;
	}

	public void setRequest_time(String request_time) {
		this.request_time = request_time;
	}

	public String getDbqcnt() {
		return dbqcnt;
	}

	public void setDbqcnt(String dbqcnt) {
		this.dbqcnt = dbqcnt;
	}

	public String getMccnt() {
		return mccnt;
	}

	public void setMccnt(String mccnt) {
		this.mccnt = mccnt;
	}

	public String getMongocnt() {
		return mongocnt;
	}

	public void setMongocnt(String mongocnt) {
		this.mongocnt = mongocnt;
	}

	public String getMemory() {
		return memory;
	}

	public void setMemory(String memory) {
		this.memory = memory;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getTrace() {
		return trace;
	}

	public void setTrace(String trace) {
		this.trace = trace;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public String getLogDate() {
		return logDate;
	}

	public void setLogDate(String logDate) {
		this.logDate = logDate;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public LogModel() {
		super();
	}

	public LogModel(String cmd, String app_id, String userid, String duration, String ok, String client_addr,
			String server_addr, String ver_full, String ver, String vc, String vd, String dbqcnt, String mccnt,
			String mongocnt, String memory, String params, String trace, String version, String timestamp,
			String platform, String os, String line, String logDate, String request_time) {
		super();
		this.cmd = cmd;
		this.app_id = app_id;
		this.userid = userid;
		this.duration = duration;
		this.ok = ok;
		this.client_addr = client_addr;
		this.server_addr = server_addr;
		this.ver_full = ver_full;
		this.ver = ver;
		this.vc = vc;
		this.vd = vd;
		this.dbqcnt = dbqcnt;
		this.mccnt = mccnt;
		this.mongocnt = mongocnt;
		this.memory = memory;
		this.params = params;
		this.trace = trace;
		this.version = version;
		this.timestamp = timestamp;
		this.platform = platform;
		this.os = os;
		this.line = line;
		this.logDate = logDate;
		this.request_time = request_time;
	}

	@Override
	public String toString() {
		return "cmd=" + cmd + ", app_id=" + app_id + ", userid=" + userid + ", duration=" + duration + ", ok="
				+ ok + ", client_addr=" + client_addr + ", server_addr=" + server_addr + ", ver_full=" + ver_full
				+ ", ver=" + ver + ", vc=" + vc + ", vd=" + vd + ", dbqcnt=" + dbqcnt + ", mccnt=" + mccnt
				+ ", mongocnt=" + mongocnt + ", memory=" + memory + ", params=" + params + ", trace=" + trace
				+ ", version=" + version + ", timestamp=" + timestamp + ", platform=" + platform + ", os=" + os
				+ ", line=" + line + ", logDate=" + logDate + ", request_time=" + request_time ;
	}
	

}
