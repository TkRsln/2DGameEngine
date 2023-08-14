package game.Component.Utku;

import game.Component.GameObject;
import game.Requirments.Vector;

public class Player1Death extends GameObject {
	public Player1Death(Vector position) {
		super(90,100);
		addComponent(new SingleAnimation(this, "pd", 30));
		transform.setPosition(position);
		wakeUp();
		Destroy(5000);
	}
}
