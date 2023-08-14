package game.Component.Utku.Boss;

import game.Component.DynamicCollider;
import game.Component.GameObject;
import game.Component.HitBox;
import game.Component.RigidBody;
import game.Component.Transform;
import game.Component.Test.DecorSprite;
import game.Component.Utku.Player1;
import game.Engine.TAGS;

public class Boss1Bullet extends GameObject {
	
	public Boss1Bullet(Transform boss,double vecX,double vecY) {
		
		super(50,75);
		RigidBody rb = new RigidBody(this);
		rb.addVelocity(vecX, vecY);
		rb.gravitiy=2;
		
		addComponent(new HitBox(this));
		addComponent(rb);
		addComponent(new DecorSprite(this, "img/boss_bullet_2.png"));
		
		tag=TAGS.alien_bullet;
		
		transform.setPosition(((Boss1)boss.gameobject).bullet_point.transform.getWorldMiddlePosition());
		transform.scale.x=boss.scale.x;
		
		wakeUp();
		
		Destroy(700);
	}
	
	@Override
	public void onDynamicCollision(DynamicCollider col) {
		if(col.isContains(TAGS.player)) {
			((Player1)col.getGameObjectByTag(TAGS.player)).hitDamage(2.25);
			Destroy();
		}
	}
	
	@Override
	protected void onDestroy() {
		if(transform.position!=null)
			new Boss1BulletImpact(transform.position.x,transform.position.y);
	}
	
}
