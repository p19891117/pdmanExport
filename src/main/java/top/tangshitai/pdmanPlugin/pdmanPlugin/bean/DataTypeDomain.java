package top.tangshitai.pdmanPlugin.pdmanPlugin.bean;

import java.util.List;

public class DataTypeDomain {
	private List<Datatype> datatype;
	private List<Database> database;
	public List<Datatype> getDatatype() {
		return datatype;
	}
	public void setDatatype(List<Datatype> datatype) {
		this.datatype = datatype;
	}
	public List<Database> getDatabase() {
		return database;
	}
	public void setDatabase(List<Database> database) {
		this.database = database;
	}
}
