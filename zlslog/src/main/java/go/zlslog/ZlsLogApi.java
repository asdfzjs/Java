package go.zlslog;

public class ZlsLogApi {
	public static void main(String[] args) throws Exception {
		String filePath = args[0];
		int batchLines = 1000;
		if (args.length == 2) {
			batchLines = Integer.parseInt(args[1]);
		}
		LogToModel.readFileByLines(filePath, batchLines);
	}
}
