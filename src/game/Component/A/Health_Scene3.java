package game.Component.A;

import game.Component.DynamicCollider;
import game.Component.GameObject;
import game.Component.HitBox;
import game.Component.RigidBody;
import game.Component.Transform;
import game.Component.S.PlayerShip;
import game.Component.Utku.SingleAnimation;
import game.Engine.TAGS;

public class Health_Scene3 extends GameObject {
	
public Health_Scene3( double posx,double posy) {
		
		super(75,75);
		transform.setPosition(posx, posy);
		
		addComponent(new HitBox(this));
		RigidBody rb = new RigidBody(this);
		addComponent(rb);
		//rb.gravitiy=0;
		addComponent(new SingleAnimation(this, "Heart", 100,true));
		wakeUp();
		
		Destroy(10000);
		
	}
	public Health_Scene3(Transform tr) {
		
		super(75,75);
		transform.setPosition(tr.position.x, tr.position.y);
		
		addComponent(new HitBox(this));
		RigidBody rb = new RigidBody(this);
		addComponent(rb);
		//rb.gravitiy=0;
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
			((Player_Scene3)col.getGameObjectByTag(TAGS.player)).setHealth(10);
			
			Destroy();
			//}
			
		}
	}

}
