package game.Component.S;

import game.Component.DynamicCollider;
import game.Component.GameObject;
import game.Component.HitBox;
import game.Component.RigidBody;
import game.Component.Transform;
import game.Component.Utku.Player1;
import game.Component.Utku.SingleAnimation;
import game.Engine.TAGS;

public class ShipHealth extends GameObject {

public ShipHealth( double posx,double posy) {
		
		super(75,75);
		transform.setPosition(posx, posy);
		
		addComponent(new HitBox(this));
		RigidBody rb = new RigidBody(this);
		addComponent(rb);
		rb.gravitiy=0;
		addComponent(new SingleAnimation(this, "Heart", 100,true));
		wakeUp();
		
		Destroy(10000);
		
	}
	public ShipHealth( Transform tr) {
		
		super(75,75);
		transform.setPosition(tr.position.x, tr.position.y);
		
		addComponent(new HitBox(this));
		RigidBody rb = new RigidBody(this);
		addComponent(rb);
		rb.gravitiy=0;
		addComponent(new SingleAnimation(this, "Heart", 100,true));
		wakeUp();
		
		Destroy(10000);
		
	}
	
	@Override
	public void onDynamicCollision(DynamicCollider col) {
		//+-System.out.println("Chest:: "+col);
		if(col.isContains(TAGS.player)) {
			//Main.target.transform.setPosition(col.getObjectByTag(TAGS.player).transform.position);
			//if(col.getGameObjectByTag(TAGS.player).transform.getDistance(transform)<50) {
			((PlayerShip)col.getGameObjectByTag(TAGS.player)).setHealth(10);
			
			Destroy();
			//}
			
		}
	}
}
