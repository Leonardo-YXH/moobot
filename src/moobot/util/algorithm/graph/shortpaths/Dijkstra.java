package moobot.util.algorithm.graph.shortpaths;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import moobot.util.algorithm.graph.Edge;
import moobot.util.algorithm.graph.Vertex;
/**
 * DIJKSTRA(G, s, w)
 *</br>  d[s] := 0 
 *</br>  INSERT(Q, s)										discover vertex s
 *</br>  while (Q != Ø)
 *</br>    u := EXTRACT-MIN(Q)								examine vertex u
 *</br>    for each vertex v in Adj[u]						examine edge (u,v)
 *</br>      if (w(u,v) + d[u] < d[v])						edge (u,v) relaxed
 *</br>        d[v] := w(u,v) + d[u]
 *</br>        p[v] := u 
 *</br>        if (d[v] was originally infinity) 
 *</br>          INSERT(Q, v)   							discover vertex v
 *</br>        else
 *</br>          DECREASE-KEY(Q, v)						edge (u,v) not relaxed
 *</br>      else
 *</br>      	...
 *</br>    end for											finish vertex u
 *</br>  end while
 *</br>  return (d, p)
 * @author leonardo
 *
 */
public class Dijkstra {

	public static<T> void relax(){
		
	}
	/**
	 * 
	 * @param graph
	 * @param source
	 * @param distance 保存每个点到源的距离
	 * @param previousVertex 保存该点的前一个点
	 */
	public static<T> void paths(Map<Vertex<T>,List<Edge<Vertex<T>>>> graph,Vertex<T> source,Map<Vertex<T>,Edge<Vertex<T>>> distance,Map<Vertex<T>,Vertex<T>> previousVertex){
		distance.put(source, new Edge<Vertex<T>>(source,source,0));
		PriorityQueue<Edge<Vertex<T>>> Q=new PriorityQueue<>(new Comparator<Edge<Vertex<T>>>() {

			@Override
			public int compare(Edge<Vertex<T>> o1, Edge<Vertex<T>> o2) {
				
				return o1.weight>=o2.weight?1:-1;
			}
		});
		List<Edge<Vertex<T>>> firstChildren=graph.get(source);
		if(firstChildren==null){
			return;
		}
		for(Edge<Vertex<T>> child:firstChildren){//init root-child
			Edge<Vertex<T>> de=new Edge<Vertex<T>>(source,child.target,child.weight);
			distance.put(child.target, de);
			previousVertex.put(child.target, source);
			Q.add(de);
		}
		while(!Q.isEmpty()){
			Edge<Vertex<T>> edge=Q.poll();
			Vertex<T> u=edge.target;
			
			List<Edge<Vertex<T>>> edges=graph.get(u);
			if(edges==null){
				continue;
			}
			for(Edge<Vertex<T>> e:edges){
				
				double weight=e.weight+distance.get(e.source).weight;
				if(!distance.containsKey(e.target)){//add
					Edge<Vertex<T>> ne=new Edge<Vertex<T>>(source,e.target,weight);
					distance.put(e.target, ne);
					previousVertex.put(e.target, u);
					Q.add(ne);
				}
				else{//update distance in Q
					Edge<Vertex<T>> ue=distance.get(e.target);
					if(weight<ue.weight){
						ue.weight=weight;
						//distance.put(e.target, ue);
						previousVertex.put(e.target, u);
					}
				}
			}
		}
	}
	public static<T> List<Vertex<T>> getPath(Map<Vertex<T>,Vertex<T>> previousVertex,Vertex<T> source,Vertex<T> target){
		List<Vertex<T>> path=new LinkedList<Vertex<T>>();
		Vertex<T> it=target;
		while(previousVertex.containsKey(it)){
			path.add(0, it);
			it=previousVertex.get(it);
		}
		path.add(0, source);
		return path;
	}
	public static void main(String[] args) {
		List<Vertex<String>> vertices=new ArrayList<>();
		Vertex<String> v1=new Vertex<String>("1");
		vertices.add(v1);
		
		Vertex<String> v2=new Vertex<String>("2");
		vertices.add(v2);
		Vertex<String> v3=new Vertex<String>("3");
		vertices.add(v3);
		Vertex<String> v4=new Vertex<String>("4");
		vertices.add(v4);
		Vertex<String> v5=new Vertex<String>("5");
		vertices.add(v5);
		
		Edge<Vertex<String>> e12=new Edge<>(v1, v2, 10);
		Edge<Vertex<String>> e14=new Edge<>(v1, v4, 30);
		Edge<Vertex<String>> e15=new Edge<>(v1, v5, 100);
		Map<Vertex<String>,List<Edge<Vertex<String>>>> graph = new HashMap<>();
		graph.put(v1, Arrays.asList(e12,e14,e15));
		
		Edge<Vertex<String>> e23=new Edge<>(v2, v3, 50);
		graph.put(v2, Arrays.asList(e23));
		
		Edge<Vertex<String>> e35=new Edge<>(v3, v5, 10);
		graph.put(v3, Arrays.asList(e35));
		
		Edge<Vertex<String>> e43=new Edge<>(v4, v3, 20);
		Edge<Vertex<String>> e45=new Edge<>(v4, v5, 60);
		graph.put(v4, Arrays.asList(e43,e45));
		
		
		Map<Vertex<String>,Edge<Vertex<String>>> distance=new HashMap<>();
		Map<Vertex<String>,Vertex<String>> previousVertex=new HashMap<>();
		paths(graph, v1, distance, previousVertex);
	}
}
