package go.zlslog;

public class ZlsLogApi {
	public static void main(String[] args) throws Exception {
		String filePath = args[0];
		int batchLines = 10000;
		if (args.length == 2) {
			batchLines = Integer.parseInt(args[1].substring(0,args[1].length()-5));
		}
		LogToModel.readFileByLines(filePath, batchLines);
	}
}
