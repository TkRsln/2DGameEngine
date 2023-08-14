package game.Component.Utku;

import game.Main;
import game.Component.DynamicCollider;
import game.Component.GameObject;
import game.Component.HitBox;
import game.Component.RigidBody;
import game.Component.Transform;
import game.Engine.TAGS;
import game.Requirments.Collider;

public class ChestObject extends GameObject {
	
	public ChestObject( double posx,double posy) {
		
		super(75,75);
		transform.setPosition(posx, posy);
		
		addComponent(new HitBox(this));
		addComponent(new RigidBody(this));
		addComponent(new SingleAnimation(this, "TreasureB", 100,true));
		wakeUp();
		
		Destroy(10000);
		
	}
	public ChestObject( Transform tr) {
		
		super(75,75);
		transform.setPosition(tr.position.x, tr.position.y);
		
		addComponent(new HitBox(this));
		addComponent(new RigidBody(this));
		addComponent(new SingleAnimation(this, "TreasureB", 100,true));
		wakeUp();
		
		Destroy(10000);
		
	}
	
	@Override
	public void onDynamicCollision(DynamicCollider col) {
		//+-System.out.println("Chest:: "+col);
		if(col.isContains(TAGS.player)) {
			//Main.target.transform.setPosition(col.getObjectByTag(TAGS.player).transform.position);
			if(col.getGameObjectByTag(TAGS.player).transform.getDistance(transform)<50) {
				Destroy();
				Player1.active_player.health=10;
			}
			
		}
	}
	@Override
	public void onConstantCollision(Collider col) {
		
	}
	
}
