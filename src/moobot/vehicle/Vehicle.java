package moobot.vehicle;

import moobot.util.algorithm.transform.YVector3;

/**
 * 
 * @author Leonardo
 *
 */
public class Vehicle {

	/**
	 * 两轮子到中心的距离，即两轮距离的一半
	 */
	public double wheelDistance;
	/**
	 * 轮子半径
	 */
	public double wheelRadius;
	/**
	 * 小车位置
	 */
	public YVector3 position;
	/**
	 * 小车朝向
	 */
	public YVector3 orientation;
	/**
	 * 轮子周长
	 */
	private double wheelPerimeter;
	
	/**
	 * 
	 * @param wheelRadius 轮半径
	 * @param wheelDistance 轮心距
	 * @param position 位置
	 * @param orientation 朝向
	 */
	public Vehicle(double wheelRadius,double wheelDistance, YVector3 position, YVector3 orientation) {
		super();
		this.wheelRadius=wheelRadius;
		this.wheelDistance = wheelDistance;
		this.position = new YVector3(position);
		orientation.normalize();
		this.orientation = YVector3.add(position, orientation);
		
		this.wheelPerimeter=this.wheelRadius*2*Math.PI;
		 
	}

	/**
	 * orientation-position
	 * @return
	 */
	public YVector3 getOrientation(){
		return YVector3.minus(orientation, position);
	}
	
	public void rotate(YVector3 axis,double angle){
		this.position.rotateZSelf(axis, angle);
		this.orientation.rotateZSelf(axis, angle);
	}
	/**
	 * 待完成
	 * @param dt
	 * @param vl
	 * @param vr
	 * @see Vehicle#next(double, double, double)
	 */
	@Deprecated
	public void next2(double dt,double vl,double vr){
		double rl=vl*dt;
		double rr=vr*dt;
		if(rl>rr){//顺时针转动
			if(rr>=0){
				double dr=2*wheelDistance*rr/(rl-rr);
				double dAngle=rl/(dr+2*wheelDistance);//弧度制
				YVector3 o=getOrientation().rotateZ(-Math.PI/2);
				o.normalize();
				o.multiply(dr+wheelDistance);
				o.add(position);
				rotate(o, -dAngle);
			}
			else if(rl<=0){
				double dr=2*wheelDistance*rl/(rr-rl);
				double dAngle=-rr/(dr+2*wheelDistance);//弧度制
				YVector3 o=getOrientation().rotateZ(Math.PI/2);
				o.normalize();
				o.multiply(dr+wheelDistance);
				o.add(position);
				rotate(o, -dAngle);
			}
			else{
				double dr=-2*wheelDistance*rr/(rl-rr);
				double dAngle=rl/(-dr+2*wheelDistance);//弧度制
				YVector3 o=getOrientation().rotateZ(-Math.PI/2);
				o.normalize();
				o.multiply(-dr+wheelDistance);
				o.add(position);
				rotate(o, -dAngle);
			}
			
		}
		else if(rl<rr){//逆时针
			//TODO 
			double dr=2*wheelDistance*rl/(rr-rl);
			double dAngle=rl/(dr+2*wheelDistance);//弧度制
			YVector3 o=getOrientation().rotateZ(Math.PI/2);
			o.normalize();
			o.multiply(dr+wheelDistance);
			o.add(position);
			rotate(o, dAngle);
		}
		else{//直线
			YVector3 L=getOrientation();
			L.normalize();
			L.multiply(rl);
			this.position.add(L);
			this.orientation.add(L);
		}
	}
	/**
	 * 计算下一刻的位置
	 * @param dt
	 * @param avl 左轮角速度，单位rps(revolution per second每秒钟的转数)
	 * @param avr 右轮角速度，单位rps
	 */
	public void nextLocation(double dt,double avl,double avr){
		//先转成每秒的线速度
		double vl=avl*this.wheelPerimeter;
		double vr=avl*this.wheelPerimeter;
		next(dt, vl, vr);
	}
	/**
	 * 计算小车下一步的位置,两轮的速度可正可负
	 * @param dt 时长
	 * @param vl 左轮线速度
	 * @param vr 右轮线速度
	 */
	public void next(double dt,double vl,double vr){
		double rl=vl*dt;
		double rr=vr*dt;
		if(rl>rr){//顺时针转动
			double dr=2*wheelDistance*rr/(rl-rr);
			double dAngle=rr/dr;//弧度制
			YVector3 o=getOrientation().rotateZ(-Math.PI/2);
			o.normalize();
			o.multiply(dr+wheelDistance);
			o.add(position);
			rotate(o, -dAngle);
		}
		else if(rl<rr){//逆时针
			double dr=2*wheelDistance*rl/(rr-rl);
			double dAngle=rl/dr;//弧度制
			YVector3 o=getOrientation().rotateZ(Math.PI/2);
			o.normalize();
			o.multiply(dr+wheelDistance);
			o.add(position);
			rotate(o, dAngle);
		}
		else{//直线
			YVector3 L=getOrientation();
			L.normalize();
			L.multiply(rl);
			this.position.add(L);
			this.orientation.add(L);
		}
	}
	/**
	 * 重新修正定位
	 * @param position
	 * @param orientation
	 */
	public void relocated(YVector3 position,YVector3 orientation){
		this.position = new YVector3(position);
		orientation.normalize();
		this.orientation = YVector3.add(position, orientation);
	}
}
