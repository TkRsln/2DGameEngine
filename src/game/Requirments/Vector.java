package game.Requirments;

import game.Component.Transform;

public class Vector {
	
	public double x=0;
	public double y=0;
	
	public Vector() {}
	public Vector(double x,double y) {
		this.x=x;
		this.y=y;
	}
	
	public Vector addVector(Vector v) {
		x+=v.x;
		y+=v.y;
		return this;
	}
	public Vector addVector(double x,double y) {
		this.x+=x;
		this.y+=y;
		return this;
	}
	public Vector addVectorClone(Vector v) {
		return this.clone().addVector(v);
	}
	public Vector subVector(Vector v) {
		x-=v.x;
		y-=v.y;
		return this;
	}public Vector divideVector(double div) {
		x/=div;
		y/=div;
		return this;
	}public Vector divideVector(Vector v) {
		x/=v.x;
		y/=v.y;
		return this;
	}public Vector multipleVector(Vector v) {
		x*=v.x;
		y*=v.y;
		return this;
	}
	public double magnitude() {
		return (double)Math.sqrt(x*x + y*y);
	}
	public Vector clone() {
		return new Vector(x,y);
	}
	public void setVector(Vector v) {
		x=v.x;
		y=v.y;
	}public void setVector(double x,double y) {
		x=this.x;
		y=this.y;
	}
	public Vector getUnitVector() {
		return new Vector(x/magnitude(), y/magnitude());
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "["+x+":"+y+"]";
	}
	
	
}
