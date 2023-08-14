package game.Component;

import java.util.ArrayList;

import javax.security.auth.Destroyable;
import javax.swing.JLabel;

import game.Engine.GameEngine;
import game.Requirments.Vector;

public class Transform {
	public Vector position=new Vector();
	public Vector size=new Vector();
	public Vector scale=new Vector(1, 1);
	
	public GameObject gameobject;
	private Transform parent;
	private ArrayList<Transform> childs=new ArrayList<Transform>();
	
	public Transform(GameObject obj) {
		gameobject=obj;
		setParent(null);
	}
	public Transform(GameObject obj,Transform parent) {
		gameobject=obj;
		this.parent=parent;
	}
	
	public Vector getMidPoint() {
		return new Vector(getMidX(),getMidY());
	}
	public double getMidX() {
		return position.x+size.x/2;
	}
	public double getMidY() {
		return position.y+size.y/2;
	}
	public double getEndX() {
		return position.x+size.x*getWorldScaleX();
	}
	public double getEndY() {
		return position.y+size.y;
	}
	public double getStartX() {
		return position.x;
	}
	public double getStartY() {
		return position.y;
	}
	public Transform getParent() {
		return parent;
	}
	public double findDistance(Transform target) {
		double x=target.position.x-position.x;
		double y=target.position.y-position.y;
		return Math.abs(x*x+y*y);
	}
	public void setParent(Transform tr) {
		if(tr==parent && parent !=null)return;
		if(tr==null) {
			if(parent!=null) {
				parent.removeChild(tr);
			}
			else if(!GameEngine.root.contains(tr))
				synchronized (GameEngine.root) {
					GameEngine.root.add(this);
				}
			parent=null;
		}
		else {
			if(parent!=null) {
				parent.removeChild(tr);
			}
			else {
				synchronized (GameEngine.root) {
					GameEngine.root.remove(this);
				}
			}
			parent=tr;
			parent.addChild(this);
		}
	}
	public double getWorldScaleX() {
		return GameEngine.objectScaleX(this);
	}
	private void removeChild(Transform child) {
		childs.remove(child);
	}
	private void addChild(Transform child) {
		childs.add(child);
	}
	public Transform getChild(int index) {
		if(index<childs.size())
			return childs.get(index);
		return null;
	}
	public int getChildCount() {
		return childs.size();
	}

	public double getDistance(Transform tr) {
		double a=tr.getMidX()-getMidX();
		double b=tr.getMidY()-getMidY();
		return Math.sqrt(a*a+b*b);
	}
	public double getDistance(Vector tr) {
		double a=tr.x-getMidX();
		double b=tr.y-getMidY();
		return Math.sqrt(a*a+b*b);
	}
	public double getDistance(double x,double y) {
		double a=x-getMidX();
		double b=y-getMidY();
		return Math.sqrt(a*a+b*b);
	}
	public double getDistanceX(double x) {
		return Math.abs(x-getMidX());
	}
	public double getDistanceY(double y) {
		return Math.abs(y-getMidY());
	}
	public void setPosition(Vector pos) {
		if(position==null)return;
		position.x=pos.x;
		position.y=pos.y;
	}
	public void setPositionEnd(Vector pos) {
		if(position==null)return;
		position.x=pos.x-size.x;
		position.y=pos.y-size.y;
	}
	public void setPosition(double x,double y) {
		if(position==null)return;
		position.x=x;
		position.y=y;
	}
	public void addPosition(double x,double y) {
		if(position==null)return;
		position.x+=x;
		position.y+=y;
	}
	/*
	public double getPosX() {
		return position.x;
	}
	public double getPosY() {
		return position.y;
	}
	//*/
	public void setScale(Vector scale) {
		this.scale=scale;
	}
	public void setScale(double x,double y) {
		scale.x=x;
		scale.y=y;
	}
	public Vector getScale() {
		return scale.clone();
	}
	public double getScaleX() {
		return scale.x;
	}
	public double getScaleY() {
		return scale.y;
	}
	public double getEndScaleY() {
		return getEndY()*scale.y;
	}
	public double getEndScaleX() {
		return getEndX()*scale.x;
	}
	public double getStartScaleX() {
		return getStartX()*scale.x;
	}
	public double getStartScaleY() {
		return getStartY()*scale.y;
	}
	public void Destroy() {
		synchronized(GameEngine.root) {
			if(parent==null)
				GameEngine.root.remove(this);
		}
		synchronized (this) {
			if(childs!=null) {
				for(int i=0;i<childs.size();i++) {
					try {
						getChild(i).gameobject.Destroy();
					}catch (Exception e) {e.printStackTrace();}
				}
				childs.clear();
				childs=null;
			}
			position=null;
			size=null;
			scale=null;
			gameobject=null;
			parent=null;
		}
	}
	public void setPositionX(double x) {
		position.x=x;
	}
	public void setPositionY(double y) {
		position.y=y;
	}
	
	public boolean isPointXInside(double point) {
		return getWorldPosition().x<point&&getWorldPosition().x+size.x>point;
	}
	public boolean isPointYInside(double point) {
		//System.out.println(position.y+"<"+point+"<"+(position.y+size.y));
		return getWorldPosition().y<point&&getWorldPosition().y+size.y>point;
	}
	public Vector getWorldPosition() {
		return GameEngine.objectWorldPosition(this);
	}
	public Vector getWorldMiddlePosition() {
		return GameEngine.objectStartPosition(this);
	}
	public double getWorldPositionEndX() {
		return GameEngine.objectWorldPosition(this).x+getEndScaleX();
	}
	public double getWorldPositionStartX() {
		return GameEngine.objectWorldPosition(this).x+getStartScaleX();
	}
	public Vector getWorldPositionEnd() {
		return GameEngine.objectEndPosition(this);
	}
	public Vector getWorldPositionStart() {
		return GameEngine.objectStartPosition(this);
	}
	
	public boolean isInHierarchy(Transform tr) {
		if(tr==this)return true;
		boolean b=checkHierarchyParent(tr);
		if(b)return true;
		return checkHierarchyChilds(tr);
	}
	private boolean checkHierarchyParent(Transform tr) {
		Transform parent = getParent();
		while(parent!=null) {
			if(parent==tr)return true;
			parent=parent.parent;
		}
		return false;
	}
	private boolean checkHierarchyChilds(Transform tr) {
		for(Transform ch : childs) {
			if(ch==null)continue;
			if(ch==tr)return true;
			boolean b = ch.checkHierarchyChilds(tr);
			if(b==false)
				continue;
			else 
				return true;
		}
		return false;
	}
}
