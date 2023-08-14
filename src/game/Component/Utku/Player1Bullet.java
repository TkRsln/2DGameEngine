package game.Component.Utku;

import game.Main;
import game.Component.DynamicCollider;
import game.Component.GameObject;
import game.Component.HitBox;
import game.Component.RigidBody;
import game.Component.Transform;
import game.Component.Test.DecorSprite;
import game.Component.Utku.Boss.Boss1BulletImpact;
import game.Engine.TAGS;
import game.Requirments.Collider;
import game.Requirments.Vector;

public class Player1Bullet extends GameObject {
	
	public Player1Bullet(Vector player,double vecX,double vecY) {
		
		super(20,20);
		RigidBody rb = new RigidBody(this);
		rb.addVelocity(vecX, vecY);
		rb.gravitiy=2;
		
		addComponent(new HitBox(this));
		addComponent(rb);
		addComponent(new DecorSprite(this, "img/Alien8 Bullet.png"));
		
		tag=TAGS.player_bullet;
		
		transform.setPosition(player);
//		((Player1)player.gameobject).gun.transform.getWorldPositionEnd()
		//transform.scale.x=player.scale.x;
		
		wakeUp();
		
		Destroy(500);
		synchronized (Main.bulletInfo) {
			Vector unit_vector=rb.speed.getUnitVector();
			Main.bulletInfo.setText("velocity: "+((int)(rb.speed.magnitude()*100))/100.0+"| Angle: "+(((int)(Math.toDegrees(Math.atan2(rb.speed.x,rb.speed.y)))*100))/100.0);
		}
	}
	

	@Override
	public void onDynamicCollision(DynamicCollider col) {
		if(col.isContains(TAGS.alien)) {
			new Player1BulletImpact(transform.position.x,transform.position.y);
			Destroy();
		}
	}
	
	@Override
	public void onConstantCollision(Collider col) {
		if(col.isContains(TAGS.alien)&&transform!=null) {
			new Player1BulletImpact(transform.position.x,transform.position.y);
			Destroy();
		}
	}
}
