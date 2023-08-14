package game.Component.Utku.Alien;

import game.Component.GameObject;
import game.Component.Utku.SingleAnimation;
import game.Requirments.Vector;

public class Alien1Dead extends GameObject{
	
	public Alien1Dead(Vector position) {
		super(90,100);
		addComponent(new SingleAnimation(this, "ad", 30));
		transform.setPosition(position);
		wakeUp();
		Destroy(5000);
	}
	
}
