package top.tangshitai.pdmanPlugin.pdmanPlugin.bean;

public class Node {
	private String shape;
	private String title;
	private boolean moduleName = false;
	private long x;
	private long y;
	private String id;
	public String getShape() {
		return shape;
	}
	public void setShape(String shape) {
		this.shape = shape;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public long getX() {
		return x;
	}
	public void setX(long x) {
		this.x = x;
	}
	public long getY() {
		return y;
	}
	public void setY(long y) {
		this.y = y;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public boolean isModuleName() {
		return moduleName;
	}
	public void setModuleName(boolean moduleName) {
		this.moduleName = moduleName;
	}
	
}
