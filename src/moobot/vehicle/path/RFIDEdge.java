package moobot.vehicle.path;

import moobot.util.algorithm.graph.Edge;
/**
 * 
 * @author Leonardo
 *
 */
public class RFIDEdge extends Edge<RFIDPoint> {
	
	/**
	 * 入射角，该条边targetpoint的角度，非弧度制
	 */
	private double inAngle;
	/**
	 * 出射角，该条边sourcepoint的角度，非弧度制
	 */
	private double outAngle;
	
	public RFIDEdge(RFIDPoint source, RFIDPoint target, double weight) {
		super(source, target, weight);
		this.inAngle=calAngle(target.x-source.x, target.y-source.y);
		this.outAngle=this.inAngle;
	}

	public RFIDEdge(RFIDPoint source, RFIDPoint target) {
		super(source, target);
		this.inAngle=calAngle(target.x-source.x, target.y-source.y);
		this.outAngle=this.inAngle;
	}
	/**
	 * 如果边不是直线的话则需要使用该方法配置入射角和出射角
	 * @param source
	 * @param target
	 * @param weight
	 * @param inAngle
	 * @param outAngle
	 */
	public RFIDEdge(RFIDPoint source, RFIDPoint target, double weight,double inAngle,double outAngle) {
		super(source, target, weight);
		this.inAngle=inAngle;
		this.outAngle=outAngle;
	}

	public RFIDEdge(RFIDPoint source, RFIDPoint target,double inAngle,double outAngle) {
		super(source, target);
		this.inAngle=inAngle;
		this.outAngle=outAngle;
	}
	
	/**
	 * 入射角，该条边targetpoint的角度，非弧度制
	 * @return
	 */
	public double getInAngle() {
		return inAngle;
	}

	public void setInAngle(double inAngle) {
		this.inAngle = inAngle;
	}

	/**
	 * 出射角，该条边sourcepoint的角度，非弧度制
	 * @return
	 */
	public double getOutAngle() {
		return outAngle;
	}

	public void setOutAngle(double outAngle) {
		this.outAngle = outAngle;
	}

	public static double calAngle(double x,double y){
		if(y==0){
			return x>0?0:180;
		}
		if(x==0){
			return y<0?90:270;
		}
		double angle= Math.atan(-y/x)*180/Math.PI;
		if(y>0){
			angle+=180;
		}
		if(angle>180){
			angle-=180;
		}
		else if(angle<-180){
			angle+=180;
		}
		return angle;
	}
}
