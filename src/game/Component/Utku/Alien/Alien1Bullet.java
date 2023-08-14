package game.Component.Utku.Alien;

import game.Component.DynamicCollider;
import game.Component.GameObject;
import game.Component.HitBox;
import game.Component.RigidBody;
import game.Component.Transform;
import game.Component.Test.DecorSprite;
import game.Component.Utku.Player1BulletImpact;
import game.Component.Utku.Player1;
import game.Engine.TAGS;
import game.Requirments.Vector;

public class Alien1Bullet extends GameObject {
	public Alien1Bullet(Vector alien,double vecX,double vecY) {
		super(20,20);
		RigidBody rb = new RigidBody(this);
		rb.addVelocity(vecX, vecY);
		rb.gravitiy=2;
		
		addComponent(new HitBox(this));
		addComponent(rb);
		addComponent(new DecorSprite(this, "img/Alien_Bullet.png"));
		
		tag=TAGS.alien_bullet;
		
		transform.setPosition(alien);
		//((Alien1)alien.gameobject).gun.transform.getWorldPositionEnd()
		//transform.scale.x=player.scale.x;
		
		wakeUp();
		
		Destroy(500);
	}
	

	@Override
	public void onDynamicCollision(DynamicCollider col) {
		if(col.isContains(TAGS.player)) {
			new Alien1BulletImpact(transform.position.x,transform.position.y);
			Destroy();
		}
	}
}
