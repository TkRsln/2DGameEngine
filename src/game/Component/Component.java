package game.Component;

import java.awt.image.BufferedImage;

import game.Requirments.Vector;

public class Component {
	public Component(GameObject obj) {
		object=obj;
		transform=obj.transform;
	}
	public Transform transform;
	public GameObject object;
	protected void onStart() {}
	protected void onLoop() {}
	protected void onDestroyComponent() {}
	protected BufferedImage onRender() {return null;}
	protected boolean isLoopRequired() {return false;}
}
