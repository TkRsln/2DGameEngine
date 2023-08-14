package game.Requirments;

import game.Component.GameObject;
import game.Engine.TAGS;

public class Collider {
	
	public Collider(GameObject referens) {
		// TODO Auto-generated constructor stub
		objects[0]=referens;
	}
	
	public GameObject[] objects= new GameObject[4];
	public int collided_objects=1;
	
	
	public void addGameObject(GameObject obj) {
		if(collided_objects<objects.length) {
			objects[collided_objects]=obj;
			collided_objects++;
		}
	}
	public boolean isContains(GameObject obj) {
		for(int i=0;i<collided_objects;i++) {
			if(objects[i]==obj)return true;
		}
		return false;
	}
	public boolean isContains(TAGS tag) {
		for(int i=0;i<collided_objects;i++) {
			if(objects[i].tag==tag)return true;
		}
		return false;
	}
	public boolean isContains(Class cls) {
		for(int i=0;i<collided_objects;i++) {
			if(objects[i].getClass()==cls)return true;
		}
		return false;
	}
	public GameObject getObjectByTag(TAGS tag) {
		for(int i=0;i<collided_objects;i++) {
			if(objects[i].tag==tag)return objects[i];
		}
		return null;
	}
	@Override
	public String toString() {
		String nm="";
		for(GameObject go : objects)
			if(go!=null)
				nm+=go.name+" ";
		return nm;
		
	}
}
