package moobot.vehicle.path;

import moobot.util.algorithm.graph.Vertex;
/**
 * rfid所代表的点位置
 * @author leonardo
 *
 */
public class RFIDPoint extends Vertex<String> {

	/**
	 * 坐标x
	 */
	public double x;
	/**
	 * 坐标y
	 */
	public double y;
	public RFIDPoint(String id,double x,double y) {
		super(id);
		this.x=x;
		this.y=y;
	}

}
