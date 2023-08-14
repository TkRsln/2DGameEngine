package game.Component.Test;

import game.Component.Component;
import game.Component.GameObject;
import game.Requirments.Vector;

public class RigidTesst extends Component{
	
	public Vector acceleration=new Vector();
	public Vector speed=new Vector();
	
	private int virtual_ground=700;
	private int virtual_wall=1500;
	
	
	public float bounce=0.5f;
	public double gravitiy=9.8;
	public double friction=1.35f;
	public double mass=5.5; 
	
	private double dt=0;
	
	public RigidTesst(GameObject obj) {
		super(obj);
	}
	
	@Override
	protected boolean isLoopRequired() {
		return true;
	}
	@Override
	protected void onLoop() {
		dt=object.dt/1000.0;
		
		////////////////////////////////////////[X ekseni sýnýrlama]
		if(transform.getStartX()<0 && speed.x<0)
			speed.x=speed.x*(-bounce);
		else if(transform.getEndX()>virtual_wall && speed.x>0)
			speed.x=speed.x*(-bounce);

		//////////////////////////////////////////////////////[ÝVME HESABI]
		speed.y+=(-gravitiy+acceleration.y)*dt;
		speed.x+=(acceleration.x)*dt;
		decreaseAcceleration();
		////////////////////////////////////////[y ekseni sýnýrlama]
		if(transform.getEndY()>virtual_ground) {
			if(speed.y<0.5) {
				acceleration.y=0;
				speed.y=speed.y*-bounce;
			}
			if(Math.abs(speed.y)<0.3)
				speed.y=0;
		}
		//System.out.println("S"+speed+" A"+acceleration);
		//////////////////////////////////////////////////////
		transform.addPosition(speed.x, -speed.y);
		
	}
	/*
	public void addForceY(double force) {
		force/=mass;
		acceleration.y=force; 
	}*/
	public void addVelocity(double x,double y) {
		speed.x+=x;
		speed.y+=y;
	}public void setVelocity(double x,double y) {
		speed.x=x;
		speed.y=y;
	}
	public void decreaseAcceleration() {
		//acceleration.x+=((speed.x)*(-dt)*friction);
		acceleration.y+=(acceleration.y*(-dt)*friction);
	}
	
	
	
}
