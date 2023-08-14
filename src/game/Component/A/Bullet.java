package game.Component.A;

import game.Component.DynamicCollider;
import game.Component.GameObject;
import game.Component.HitBox;
import game.Component.RigidBody;
import game.Component.Test.DecorSprite;
import game.Engine.TAGS;
import game.Requirments.Vector;

public class Bullet extends GameObject  {
	
	
	
	public Bullet(double posX,double  posY,int sizeX,int sizeY, double vecX, double vecY, String path) {
	
		RigidBody rbB = new RigidBody(this);
		rbB.friction_air_y = 0.2;
		
		transform.position.x = posX;
		transform.position.y = posY;
		
		HitBox hbB = new HitBox(this);
		transform.size = new Vector(sizeX,sizeY);
		rbB.addVelocity(vecX,vecY);
		addComponent(rbB);
		addComponent(hbB);
		addComponent(new DecorSprite(this, path));		
		
		wakeUp();
		Destroy(2000);
	}	
	@Override
	public void onDynamicCollision(DynamicCollider col) {
		if (col.isContains(TAGS.player)) {
			Destroy();
		}
	}
}
