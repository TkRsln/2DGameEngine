package game.Component.S;

import game.Component.GameObject;
import game.Component.HitBox;
import game.Requirments.Vector;

public class EdgeControl extends GameObject {

	public EdgeControl(int posx, int posy) {
		
		transform.size = new Vector(6500,10);
		transform.setPosition(posx, posy);
		addComponent(new HitBox(this));
		
		wakeUp();
	}

}
