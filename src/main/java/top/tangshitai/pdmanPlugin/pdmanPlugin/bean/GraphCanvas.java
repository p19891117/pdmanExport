package top.tangshitai.pdmanPlugin.pdmanPlugin.bean;

import java.util.ArrayList;
import java.util.List;

public class GraphCanvas {
	private List<Node> nodes = new ArrayList<>();
	private List<Edge> edges;
	public List<Node> getNodes() {
		return nodes;
	}
	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}
	public List<Edge> getEdges() {
		return edges;
	}
	public void setEdges(List<Edge> edges) {
		this.edges = edges;
	}
	
}
