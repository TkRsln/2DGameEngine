package game.Component.A;

import game.Component.GameObject;
import game.Component.Utku.SingleAnimation;
import game.Requirments.Vector;

public class Final_Boss_Death extends GameObject {
	
	
	public Final_Boss_Death(Vector pos) {
		super(400,400);
		addComponent(new SingleAnimation(this, "FinalBossDeath", 60));
		transform.setPosition(pos);
		wakeUp();
		Destroy(2000);
		}
	
	
}
