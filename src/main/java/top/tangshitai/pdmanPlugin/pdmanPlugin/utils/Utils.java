package top.tangshitai.pdmanPlugin.pdmanPlugin.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;

import top.tangshitai.pdmanPlugin.pdmanPlugin.App;

public class Utils {
	/**
	 * 类路径中读取配置文件
	 * @param name 文件名
	 * @return
	 * @throws IOException
	 */
	public static StringBuilder readFileContent(String name) throws IOException {
		Reader reader = null;
		try {
			reader = new InputStreamReader(App.class.getClassLoader().getResourceAsStream(name),"UTF-8");
			StringBuilder sb = new StringBuilder();
			int len = -1;
			char[] buffer = new char[1024*10];
			while((len=reader.read(buffer))!=-1) {
				sb.append(new String(buffer,0,len));
			}
			return sb;
		} finally {
			if(reader!=null)
				reader.close();
		}
	}
	/**
	 * 写入内容到配置文件中
	 * @param name
	 * @param sb
	 * @throws IOException
	 */
	public static void writeFileContent(String name,String content) throws IOException {
		OutputStream out = null;
		try {
			out = new FileOutputStream(name);
			out.write(content.getBytes("utf-8"));
		} finally {
			if(out!=null)
				out.close();
		}
	}
	
}
