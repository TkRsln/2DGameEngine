package game.Component.S;

import game.Component.GameObject;
import game.Component.Utku.SingleAnimation;
import game.Requirments.Vector;

public class ShipExplosion extends GameObject {

	public ShipExplosion(Vector position) {
		super(270*1.2,300*1.2);
		addComponent(new SingleAnimation(this, "fire", 75));
		transform.setPosition(position.clone().addVector(-100, -100));
		wakeUp();
		Destroy(4000);
	}
}
