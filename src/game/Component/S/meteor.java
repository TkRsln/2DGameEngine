package game.Component.S;

import java.util.Random;

import game.Component.DynamicCollider;
import game.Component.GameObject;
import game.Component.HitBox;
import game.Component.RigidBody;
import game.Component.Test.DecorSprite;
import game.Component.Utku.SingleAnimation;
import game.Engine.TAGS;

public class meteor extends GameObject{

	public Random rand = new Random();
	String[] arr = new String[] {"img/meteor1.png", "img/meteor2.png"};
	RigidBody rb;
	
	public meteor(int posx, int posy) {
		rb = new RigidBody(this);
		addComponent(new HitBox(this));
		addComponent(rb);
		rb.addVelocity((-5 + rand.nextInt(10))*0.25, (-5 + rand.nextInt(10))*0.25);
		rb.gravitiy = 0;
		rb.bounce = 1;
		rb.friction_air_x = 0.1;
		rb.friction_air_y = 0.1;
		
		transform.size.x=50;
		transform.size.y=50;
		transform.setPosition(posx, posy);
//		addComponent(new SingleAnimation(this, "object", 200,true));
		addComponent(new DecorSprite(this, arr[rand.nextInt(arr.length)]));
		
		wakeUp();
		
		Destroy((5+rand.nextInt(8))*1000);
		
	}
	
	@Override
	protected void onDestroy() {
		if(PlayerShip.active_player==null || PlayerShip.active_player.transform==null)return;
		new meteor((int)PlayerShip.active_player.transform.position.x+1000, 400-rand.nextInt(500));
	}
	
	@Override
	public void onDynamicCollision(DynamicCollider col) {
		if(transform==null)return;
		if(col.isContains(TAGS.player)) {
			rb.speed.y = 3*Math.signum(PlayerShip.active_player.transform.getMidPoint().y - transform.getMidPoint().y);
		}
	}
}
