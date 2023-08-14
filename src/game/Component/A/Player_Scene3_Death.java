package game.Component.A;

import game.Component.GameObject;
import game.Component.Utku.SingleAnimation;
import game.Requirments.Vector;

public class Player_Scene3_Death extends GameObject {
	
	public Player_Scene3_Death(Vector pos) {
	super(90,100);
	addComponent(new SingleAnimation(this, "pd", 30));
	transform.setPosition(pos);
	wakeUp();
	Destroy(4000);
	}
}
