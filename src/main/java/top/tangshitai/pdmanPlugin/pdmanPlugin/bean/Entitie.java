package top.tangshitai.pdmanPlugin.pdmanPlugin.bean;

import java.io.IOException;
import java.util.List;

import com.alibaba.fastjson.JSONArray;

import top.tangshitai.pdmanPlugin.pdmanPlugin.utils.Utils;

public class Entitie {
	static StringBuilder sb = null;
	private String title;
    private List<Field> fields;
    private List<Header> headers;
    private String chnname;
    private String nameTemplate;
    
    
	public Entitie() {
		super();
		try {
			sb = Utils.readFileContent("headers.json");
		} catch (IOException e) {
			System.out.println("读取header配置文件失败，程序停止");
			System.exit(0);
		}
		JSONArray jobj = (JSONArray) JSONArray.parse(sb.toString());
		List<Header> tmp = jobj.toJavaList(Header.class);
		this.headers = tmp;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<Field> getFields() {
		return fields;
	}
	public void setFields(List<Field> fields) {
		this.fields = fields;
	}
	public List<Header> getHeaders() {
		return headers;
	}
	public void setHeaders(List<Header> headers) {
		this.headers = headers;
	}
	public String getChnname() {
		return chnname;
	}
	public void setChnname(String chnname) {
		this.chnname = chnname;
	}
	public String getNameTemplate() {
		return nameTemplate;
	}
	public void setNameTemplate(String nameTemplate) {
		this.nameTemplate = nameTemplate;
	}
    
}
