package moobot.vehicle.path;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import moobot.util.algorithm.graph.Edge;
import moobot.util.algorithm.graph.shortpaths.FloydWarshallAllPairsShortestPaths;

public class YunShangGraph {

	private List<RFIDEdge> edges;
	private List<RFIDPoint> vertices;
	private int[][] paths;
	
	public YunShangGraph(List<RFIDEdge> edges,List<RFIDPoint> vertices) {
		this.vertices=vertices;
		this.edges=edges;
	}
	
	public void reBuild(){
		RFIDEdge[][] rfidPaths=new RFIDEdge[this.vertices.size()][this.vertices.size()];
		Edge.getAdjacencyMatrix(edges, vertices,rfidPaths);
		for(int i=0;i<vertices.size();i++){
			for(int j=0;j<vertices.size();j++){
				if(i==j){
					rfidPaths[i][j]=new RFIDEdge(vertices.get(i),vertices.get(j),0);
				}
				else{
					rfidPaths[i][j]=new RFIDEdge(vertices.get(i),vertices.get(j));
				}
			}
		}
		this.paths=FloydWarshallAllPairsShortestPaths.paths(rfidPaths, vertices, edges);
	}
	
	public JSONArray getPath(RFIDPoint source,double startAngle,RFIDPoint target){
		JSONArray path=new JSONArray();
		List<RFIDPoint> points=FloydWarshallAllPairsShortestPaths.getPath(source, target, paths, vertices);
		int len=points.size()-1;
		for(int i=0;i<len;i++){
			
			RFIDPoint p=points.get(i);
			RFIDPoint pend=points.get(i+1);
			JSONObject item=new JSONObject();
			item.put("point", p);
			RFIDEdge edge=null;
			int eIndex=Collections.binarySearch(this.edges, new RFIDEdge(p, pend));
			if(eIndex>=0){
				edge=this.edges.get(eIndex);
				double yaw=edge.getOutAngle();
				double angle=yaw-startAngle;
				
				if(angle>180){
					angle-=180;
				}
				else if(angle<-180){
					angle+=180;
				}
				item.put("angle", angle);
				startAngle=edge.getInAngle();
				path.add(item);
			}
			else{//未找到，直接计算两点间直线
				double yaw=RFIDEdge.calAngle(pend.x-p.x, pend.y-p.y);
				double angle=yaw-startAngle;
				
				if(angle>180){
					angle-=180;
				}
				else if(angle<-180){
					angle+=180;
				}
				item.put("angle", angle);
				startAngle=yaw;
				path.add(item);
			}
			
		}
		JSONObject item=new JSONObject();
		
		item.put("point", points.get(len));
		
		item.put("angle", 0);
		path.add(item);
		return path;
	}

	public static void main(String[] args) {
		List<RFIDPoint> vertices=new ArrayList<>();
		
		/*RFIDPoint v1=new RFIDPoint("1", 0, 0);
		vertices.add(v1);
		RFIDPoint v2=new RFIDPoint("2", 0, 1);
		vertices.add(v2);
		RFIDPoint v3=new RFIDPoint("3", 0, 0.5);
		vertices.add(v3);
		RFIDPoint v4=new RFIDPoint("4", 1, 0);
		vertices.add(v4);
		RFIDPoint v5=new RFIDPoint("5", 1, 1);
		vertices.add(v5);
		RFIDPoint v6=new RFIDPoint("6", 2, 1);
		vertices.add(v6);*/
		
		RFIDPoint v1=new RFIDPoint("1", 0, 0);
		vertices.add(v1);
		RFIDPoint v2=new RFIDPoint("2", 195, 0);
		vertices.add(v2);
		RFIDPoint v3=new RFIDPoint("3", 98, 100);
		vertices.add(v3);
		RFIDPoint v4=new RFIDPoint("4", 0, 210);
		vertices.add(v4);
		RFIDPoint v5=new RFIDPoint("5", 98, 210);
		vertices.add(v5);
		RFIDPoint v6=new RFIDPoint("6", 195, 210);
		vertices.add(v6);
		RFIDPoint v7=new RFIDPoint("7", 98, 465);
		vertices.add(v7);
		RFIDPoint v8=new RFIDPoint("8", 195, 465);
		vertices.add(v8);
		
		List<RFIDEdge> edges=new ArrayList<>();
		
		addEdge(v1, v2, 195, edges);
		addEdge(v1, v3, 146, edges);	
		addEdge(v3, v2, 135, edges);	
		addEdge(v1, v4, 210, edges);	
		addEdge(v3, v5, 110, edges);
		addEdge(v4, v5, 98, edges);	
		addEdge(v5, v6, 98, edges);
		addEdge(v5, v7, 255, edges);
		addEdge(v6, v8, 255, edges);
		addEdge(v7, v8, 98, edges);
		addEdge(v2, v6, 210, edges);

		/*addEdge(v1, v3, 0.5, edges);
		addEdge(v1, v4, 1, edges);	
		addEdge(v2, v3, 0.5, edges);	
		addEdge(v2, v5, 1, edges);	
		addEdge(v4, v5, 1, edges);
		addEdge(v4, v6, 1.414, edges);	
		addEdge(v5, v6, 1, edges);	*/
		
		YunShangGraph g=new YunShangGraph(edges, vertices);
		g.reBuild();
		
//		for(int i=0;i<vertices.size();i++){
//			for(int j=0;j<vertices.size();j++){
//				System.out.println(i+"-->"+j);
//				g.getPath(vertices.get(i), 0, vertices.get(j)).forEach((x)->System.out.print(x));
//				System.out.println();
//			}
//		}
		
		System.out.println(g.getPath(v7, -90, v1).toJSONString());
	}
	
	public static void addEdge(RFIDPoint v0,RFIDPoint v1,double weight,List<RFIDEdge> edges){
		edges.add(new RFIDEdge(v0,v1,weight));
		edges.add(new RFIDEdge(v1,v0,weight));
	}
	
	
}
