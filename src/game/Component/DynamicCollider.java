package game.Component;

import game.Engine.TAGS;

public class DynamicCollider {
	public DynamicCollider(final Transform referans,final Transform hitter) {
		collision[0]=referans;
		collision[1]=hitter;
	}
	
	public Transform[] collision=new Transform[2];
	
	public boolean isContains(TAGS tag) {
		for(Transform go: collision) {
			if(go.gameobject==null)continue;
			if(go.gameobject.tag==tag)
				return true;
		}
		return false;
	}
	public GameObject getGameObjectByTag(TAGS tag) {
		for(Transform go: collision) {
			if(go.gameobject==null)continue;
			if(go.gameobject.tag==tag)
				return go.gameobject;
		}
		return null;
	}
	public boolean isContains(Transform object) {
		for(Transform go: collision)
			if(go==object)
				return true;
		return false;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		
		return "O:("+collision[0].gameobject+collision[1].gameobject+")";
	}
}
