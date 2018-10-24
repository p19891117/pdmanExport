package top.tangshitai.pdmanPlugin.pdmanPlugin.bean;

public class Field {
	private String name;
	private String type;
	private String remark;
	private String chnname;
	private boolean pk = false;
	private boolean notNull =false;
	private boolean relationNoShow;
	
	public Field() {
		super();
	}

	public Field(String name, String type, String remark, String chnname, boolean pk, boolean notNull,
			boolean relationNoShow) {
		super();
		this.name = name;
		this.type = type;
		this.remark = remark;
		this.chnname = chnname;
		this.pk = pk;
		this.notNull = notNull;
		this.relationNoShow = relationNoShow;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getChnname() {
		return chnname;
	}

	public void setChnname(String chnname) {
		this.chnname = chnname;
	}

	public boolean isPk() {
		return pk;
	}

	public void setPk(boolean pk) {
		this.pk = pk;
	}

	public boolean isNotNull() {
		return notNull;
	}

	public void setNotNull(boolean notNull) {
		this.notNull = notNull;
	}

	public boolean isRelationNoShow() {
		return relationNoShow;
	}

	public void setRelationNoShow(boolean relationNoShow) {
		this.relationNoShow = relationNoShow;
	}
	
	
}
