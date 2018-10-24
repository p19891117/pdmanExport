package top.tangshitai.pdmanPlugin.pdmanPlugin.bean;

import java.util.ArrayList;
import java.util.List;

public class Module {
	private String name;
    private List<Entitie> entities = new ArrayList<>();
    private GraphCanvas graphCanvas;
    private List<Association> associations;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Entitie> getEntities() {
		return entities;
	}
	public void setEntities(List<Entitie> entities) {
		this.entities = entities;
	}
	public GraphCanvas getGraphCanvas() {
		return graphCanvas;
	}
	public void setGraphCanvas(GraphCanvas graphCanvas) {
		this.graphCanvas = graphCanvas;
	}
	public List<Association> getAssociations() {
		return associations;
	}
	public void setAssociations(List<Association> associations) {
		this.associations = associations;
	}
    
}
