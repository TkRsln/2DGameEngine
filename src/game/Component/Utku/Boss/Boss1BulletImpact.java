package game.Component.Utku.Boss;

import game.Component.GameObject;
import game.Component.Utku.SingleAnimation;

public class Boss1BulletImpact extends GameObject{
	
	public Boss1BulletImpact(double posX,double posY) {
		
		transform.setPosition(posX, posY);
		transform.size.x=60;
		transform.size.y=60;
		addComponent(new SingleAnimation(this, "boss_bullet_impact_", 50, false));
		wakeUp();
		Destroy(150);
	}
	
}
