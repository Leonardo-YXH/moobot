package moobot.util.algorithm.graph;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AdjacencyVertex<T> {

	public Map<Vertex<T>,List<Edge<Vertex<T>>>> graph;
	
	public Vertex<T> source;
	
	public List<Edge<Vertex<T>>> adjacencyList;
	
	public AdjacencyVertex(Vertex<T> source) {
		this.source=source;
		this.adjacencyList=new LinkedList<Edge<Vertex<T>>>();
	}
	
	public void addAdjacencyVertex(Edge<Vertex<T>> adjEdge){
		this.adjacencyList.add(adjEdge);
	}
}
