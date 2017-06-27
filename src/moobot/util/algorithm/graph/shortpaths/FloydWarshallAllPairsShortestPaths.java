package moobot.util.algorithm.graph.shortpaths;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import moobot.util.algorithm.graph.Edge;
import moobot.util.algorithm.graph.Vertex;


/**
 * 计算图中每对定点间的最短路径。
 * </br>边的权值不能为负
 * </br>用于计算稠密图.若要计算稀疏图建议使用JohnsonAllPairsShortestPaths
 * @author leonardo
 * @see JohnsonAllPairsShortestPaths
 *
 */
public class FloydWarshallAllPairsShortestPaths {

	/**
	 * 求每对顶点间的最短距离
	 * @param adjacencyMatrix 邻接矩阵
	 * @param vertices
	 * @param edges
	 * @return 每对顶点间最短路径上的经过的点
	 */
	public static<T extends Vertex<?>,U extends Edge<T>> int[][] paths(U [][] adjacencyMatrix,List<T> vertices,List<U> edges){
		int[][] paths=new int[vertices.size()][vertices.size()];
		for(int i=0;i<vertices.size();i++){
			for(int j=0;j<vertices.size();j++){
				if(i==j){
					paths[i][j]=j;//vertices[i]到vertices[j]的最短路径经过j
				}
				else{
					paths[i][j]=-1;
				}
			}
		}
		for(int k=0;k<vertices.size();k++){
			for(int i=0;i<vertices.size();i++){
				if(adjacencyMatrix[i][k].weight!=Double.MAX_VALUE){
					for(int j=0;j<vertices.size();j++){
						if(adjacencyMatrix[k][j].weight!=Double.MAX_VALUE){
							//double tmpWeight=(adjacencyMatrix[i][k].weight==Double.MAX_VALUE||adjacencyMatrix[k][j].weight==Double.MAX_VALUE)
									//?Double.MAX_VALUE:(adjacencyMatrix[i][k].weight+adjacencyMatrix[k][j].weight);
							double tmpWeight=adjacencyMatrix[i][k].weight+adjacencyMatrix[k][j].weight;
							if(tmpWeight<adjacencyMatrix[i][j].weight){
								
								adjacencyMatrix[i][j].weight=tmpWeight;
								//paths[i][j]=paths[i][k];//vertices[i]到vertices[j]的最短路径经过k
								paths[i][j]=k;
							}
						}
						
					}
				}
				
			}
		}
		return paths;
	}
	/**
	 * 获取最短路径上的所有点
	 * @param source
	 * @param target
	 * @param paths
	 * @param vertices
	 * @return
	 */
	public static<T extends Vertex<?>> List<T> getPath(T source,T target,int[][] paths,List<T> vertices){
		List<T> spath=new LinkedList<>();
		
		int uIndex=Collections.binarySearch(vertices, source);
		int vIndex=Collections.binarySearch(vertices, target);
		
		
		spath.add(source);
		findPath(uIndex, vIndex, paths, vertices, spath);
		spath.add(target);
		//System.out.print("-->"+vertices.get(vIndex).id);
		return spath;
	}
	
	private static<T extends Vertex<?>> void findPath(int i,int j,int[][] paths,List<T> vertices,List<T> result){
		int k=paths[i][j];
		if(k==-1){
			return ;
		}
		findPath(i,k,paths,vertices,result);
		result.add(vertices.get(k));
		findPath(k,j,paths,vertices,result);
	}
	public static void main(String[] args) {
		List<Vertex<String>> vertices=new ArrayList<>();
		Vertex<String> va=new Vertex<String>("a");
		vertices.add(va);
		
		Vertex<String> vb=new Vertex<String>("b");
		vertices.add(vb);
		Vertex<String> vc=new Vertex<String>("c");
		vertices.add(vc);
		Vertex<String> vd=new Vertex<String>("d");
		vertices.add(vd);
		
		List<Edge<Vertex<String>>> edges=new ArrayList<>();
		Edge<Vertex<String>> edge=new Edge<>(va, vb, 3);
		edges.add(edge);
		edge=new Edge<>(vb, va, 3);
		edges.add(edge);
		
		edge=new Edge<>(va, vc, 2);
		edges.add(edge);
		edge=new Edge<>(vc, va, 2);
		edges.add(edge);
		
		edge=new Edge<>(va, vd, 8);
		edges.add(edge);
		edge=new Edge<>(vd, va, 8);
		edges.add(edge);
		
		edge=new Edge<>(vb, vc, 7);
		edges.add(edge);
		edge=new Edge<>(vc, vb, 7);
		edges.add(edge);
		
		edge=new Edge<>(vd, vb, 6);
		edges.add(edge);
		edge=new Edge<>(vb, vd, 6);
		edges.add(edge);
		
		edge=new Edge<>(vc, vd, 5);
		edges.add(edge);
		edge=new Edge<>(vd, vc, 5);
		edges.add(edge);
		
		int[][] paths=new int[vertices.size()][vertices.size()];
		//paths=paths(Edge.getAdjacencyMatrix(edges, vertices), vertices, edges);
		
		getPath(vc, vb, paths, vertices);
	}
}
