package moobot.util.algorithm.graph;

import java.util.Collections;
import java.util.List;

public class Edge<T extends Vertex<?>> implements Comparable<Edge<T>>{
	
	/**
	 * 出发点
	 */
	public T source;
	/**
	 * 目标点
	 */
	public T target;
	/**
	 * 权值
	 */
	public double weight;
	
	public Edge(T source,T target) {
		this.source=source;
		this.target=target;
		this.weight=Double.MAX_VALUE;
	}

	public Edge(T source,T target,double weight) {
		this.source=source;
		this.target=target;
		this.weight=weight;
	}


	/**
	 * 用于快速排序所有的edge(并不是按权值的大小排序)
	 * @param o
	 */
	@Override
	public int compareTo(Edge<T> o) {
		if(this.source.getIdentity()>o.source.getIdentity()){
			return 1;
		}
		else if(this.source.getIdentity()<o.source.getIdentity()){
			return -1;
		}
		else{
			if(this.target.getIdentity()>o.target.getIdentity()){
				return 1;
			}
			else if(this.target.getIdentity()<o.target.getIdentity()){
				return -1;
			}
		}
		return 0;
	}
	/**
	 * 通过已知的顶点构造邻接矩阵.O(n)=max(V*V,E*logV)
	 * @param edges 先排序，再构造
	 * @param vertexs 顶点
	 * @param adjM _out返回值
	 * @return
	 */
	public static <S extends Vertex<?>,U extends Edge<S>> void getAdjacencyMatrix(List<U> edges,List<S> vertexs,U[][] adjM){
		Collections.sort(edges);
		Collections.sort(vertexs);
		//U[][] adjM=new U[vertexs.size()][vertexs.size()];
		//initialization
//		for(int i=0;i<vertexs.size();i++){
//			for(int j=0;j<vertexs.size();j++){
//				if(i==j){
//					adjM[i][j]=new U(vertexs.get(i),vertexs.get(j),0);
//				}
//				else{
//					adjM[i][j]=new U(vertexs.get(i),vertexs.get(j));
//				}
//			}
//		}
		
		for(U edge:edges){
			S u=edge.source;
			S v=edge.target;
			int uIndex=Collections.binarySearch(vertexs, u);
			int vIndex=Collections.binarySearch(vertexs, v);
			adjM[uIndex][vIndex]=edge;
		}
	}
}
