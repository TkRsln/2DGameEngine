package game.Component;

import game.Engine.PhysicEngine;
import game.Engine.TAGS;
import game.Requirments.Vector;

public class RigidBody extends Component{
	
	public Vector acceleration=new Vector();
	public Vector speed=new Vector(0,0);
	
	private int virtual_ground=700;
	private int virtual_wall=1500;
	
	
	public float bounce=0.5f;
	public double gravitiy=9.8;
	public double friction_ground=1.2f;
	public double friction_air_x=0.3f;
	public double friction_air_y=0;
	public double mass=5.5; 
	public boolean onGround=false;
	
	private double dt=0;
	
	public RigidBody(GameObject obj) {
		super(obj);
	}
	public float getVelocity() {
		return 10;
	}
	public float getMass() {
		return 10;
	}
	public void onPhysicLoop(int dtt) {
		if(transform==null)return; // SON sahne hatasý
		this.dt=dtt/1000.0;
		
		////////////////////////////////////////[X ekseni sýnýrlama]
		/*
		if(transform.getStartX()<0 && speed.x<0)
			speed.x=speed.x*(-bounce);
		else if(transform.getEndX()>virtual_wall && speed.x>0)
			speed.x=speed.x*(-bounce);
		*/
		//////////////////////////////////////////////////////[ÝVME HESABI]

		//if(object.tag==TAGS.player)System.out.println("RB-B:"+speed.y);
		synchronized (speed) {
			speed.y+=(-gravitiy+acceleration.y)*dt + speed.y*friction_air_y*-dt;
			speed.x+=(acceleration.x)*dt+(onGround?speed.x*friction_ground*-dt:speed.x*friction_air_x*-dt);
		}
		//if(object.tag==TAGS.player)System.out.println("RB-A:"+speed.y);
		//decreaseAcceleration();
		////////////////////////////////////////[y ekseni sýnýrlama]
		/*
		if(transform.getEndY()>virtual_ground) {
			if(speed.y<0.5) {
				acceleration.y=0;
				speed.y=speed.y*-bounce;
			}
			if(Math.abs(speed.y)<0.3)
				speed.y=0;
		}
		*/
		//System.out.println("S"+speed+" A"+acceleration);
		//////////////////////////////////////////////////////
		transform.addPosition(speed.x, -speed.y);
	}
	
	public void addVelocity(double x,double y) {
		synchronized (speed) {

			speed.x+=x;
			speed.y+=y;
		}
	}public void setVelocity(double x,double y) {
		synchronized (speed) {

			speed.x=x;
			speed.y=y;
		}
	}public void setVelocityX(double x) {
		synchronized (speed) {
			speed.x=x;
		}
	}public void setVelocityY(double y) {
		synchronized (speed) {
			speed.y=y;
		}
	}
	public void decreaseAcceleration() {
		//acceleration.x+=((speed.x)*(-dt)*friction);
		acceleration.y+=(acceleration.y*(-dt)*friction_air_y);
	}
	public void opositeX(boolean setPositive) {
		if(speed.x<0&&setPositive)
			opositeX();
		else if(speed.x>0&&!setPositive)
			opositeX();
	}
	public void opositeX() {
		synchronized (speed) {
			speed.x=-1*bounce;
		}
	}
	public void opositeY(boolean setPositive) {
		if(speed.y<0&&setPositive)
			opositeY();
		else if(speed.y>0&&!setPositive)
			opositeY();
	}
	public void opositeY() {
		//if(object.name=="Granade")System.out.print(speed.y+" - ");
		synchronized (speed) {

			speed.y*=-1*bounce;
		}
		//if(object.name=="Granade")System.out.println(speed.y+" /"+bounce);
	}
	
}
