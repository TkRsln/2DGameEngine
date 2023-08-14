package game.Component.Utku;

import game.Component.DynamicCollider;
import game.Component.GameObject;
import game.Engine.TAGS;

public class Player1BulletImpact extends GameObject{
	
	public Player1BulletImpact(double posX,double posY) {
		
		transform.setPosition(posX, posY);
		transform.size.x=30;
		transform.size.y=30;
		//tag=TAGS.player_bullet;
		addComponent(new SingleAnimation(this, "Alien8 Impact", 50, false));
		wakeUp();
		Destroy(150);
	}
	
}
