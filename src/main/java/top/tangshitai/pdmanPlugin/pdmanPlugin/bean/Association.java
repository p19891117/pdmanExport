package top.tangshitai.pdmanPlugin.pdmanPlugin.bean;

public class Association {
	private String relation;
    private AssociationPosition from;
	private AssociationPosition to;
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	public AssociationPosition getFrom() {
		return from;
	}
	public void setFrom(AssociationPosition from) {
		this.from = from;
	}
	public AssociationPosition getTo() {
		return to;
	}
	public void setTo(AssociationPosition to) {
		this.to = to;
	}
	
}
