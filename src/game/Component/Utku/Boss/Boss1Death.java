package game.Component.Utku.Boss;

import game.Component.GameObject;
import game.Component.HitBox;
import game.Component.RigidBody;
import game.Component.Utku.SingleAnimation;
import game.Requirments.Vector;

public class Boss1Death extends GameObject{
	
	public Boss1Death(Vector position) {
		super(355*0.7f,315*0.7f);
		addComponent(new HitBox(this));
		RigidBody rb=new RigidBody(this);
		rb.bounce=0;
		addComponent(rb);
		addComponent(new SingleAnimation(this, "Boss1Death", 75));
		transform.setPosition(position);
		wakeUp();
		Destroy(5000);
	}
	
}
