package top.tangshitai.pdmanPlugin.pdmanPlugin.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.util.UUID;

import top.tangshitai.pdmanPlugin.pdmanPlugin.App;

public class Utils {
	public static String[] abc = new String[] { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n",
			"o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8",
			"9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
			"U", "V", "W", "X", "Y", "Z" };
	/**
	 * 类路径中读取配置文件
	 * 
	 * @param name 文件名
	 * @return
	 * @throws IOException
	 */
	public static StringBuilder readFileContent(String name) throws IOException {
		Reader reader = null;
		try {
			InputStream in = null;
			File file = new File(name);
			if(file.exists()&&file.isFile()) {
				in = new  FileInputStream(file);
			}else {
				in = App.class.getClassLoader().getResourceAsStream(name);
			}
			reader = new InputStreamReader(in, "UTF-8");
			StringBuilder sb = new StringBuilder();
			int len = -1;
			char[] buffer = new char[1024 * 10];
			while ((len = reader.read(buffer)) != -1) {
				sb.append(new String(buffer, 0, len));
			}
			return sb;
		} finally {
			if (reader != null)
				reader.close();
		}
	}

	/**
	 * 写入内容到配置文件中
	 * 
	 * @param name
	 * @param sb
	 * @throws IOException
	 */
	public static void writeFileContent(String name, String content) throws IOException {
		OutputStream out = null;
		try {
			out = new FileOutputStream(name);
			out.write(content.getBytes("utf-8"));
		} finally {
			if (out != null)
				out.close();
		}
	}

	public static String genShortUuid() {
		StringBuffer shortBuffer = new StringBuffer();
		String uuid = UUID.randomUUID().toString().replace("-", "");
		for (int i = 0; i < 8; i++) {
			String str = uuid.substring(i * 4, i * 4 + 4);
			int x = Integer.parseInt(str, 16);
			shortBuffer.append(abc[x % 0x3E]);
		}
		return shortBuffer.toString();

	}
	public static String genUuid() {
		String uuid = UUID.randomUUID().toString().replace("-", "");
		return uuid;

	}
}
