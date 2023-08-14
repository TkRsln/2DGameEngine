package game.Component.A;

import game.Component.GameObject;
import game.Component.Utku.SingleAnimation;

public class BossBulletImpact extends GameObject{
	public BossBulletImpact(int posX,int posY) {
		transform.size.x=60;
		transform.size.y=60;
		transform.setPosition(posX,posY);
		
	addComponent(new SingleAnimation(this,"BossRock", 100));	
	wakeUp();
	Destroy(700);

	}
	
}
