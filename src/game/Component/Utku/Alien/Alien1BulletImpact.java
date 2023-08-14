package game.Component.Utku.Alien;

import game.Component.GameObject;
import game.Component.Utku.SingleAnimation;
import game.Engine.TAGS;

public class Alien1BulletImpact  extends GameObject{
	public Alien1BulletImpact(double posX,double posY) {
		
		transform.setPosition(posX, posY);
		transform.size.x=30;
		transform.size.y=30;
		//tag=TAGS.none;
		addComponent(new SingleAnimation(this, "Alien_Impact_", 50, false));
		wakeUp();
		Destroy(150);
	}
}
