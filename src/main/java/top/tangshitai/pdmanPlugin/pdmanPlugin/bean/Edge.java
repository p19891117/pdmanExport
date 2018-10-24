package top.tangshitai.pdmanPlugin.pdmanPlugin.bean;

import java.util.List;

public class Edge {
	private String shape;
	private String source;
	private String target;
	private String id;
	private List<ControlPoint> controlPoints;
	private int sourceAnchor;
	private int targetAnchor;
	public String getShape() {
		return shape;
	}
	public void setShape(String shape) {
		this.shape = shape;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<ControlPoint> getControlPoints() {
		return controlPoints;
	}
	public void setControlPoints(List<ControlPoint> controlPoints) {
		this.controlPoints = controlPoints;
	}
	public int getSourceAnchor() {
		return sourceAnchor;
	}
	public void setSourceAnchor(int sourceAnchor) {
		this.sourceAnchor = sourceAnchor;
	}
	public int getTargetAnchor() {
		return targetAnchor;
	}
	public void setTargetAnchor(int targetAnchor) {
		this.targetAnchor = targetAnchor;
	}
	
}
