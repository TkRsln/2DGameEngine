package game.Component.Test;

import java.awt.Color;

import game.Component.DynamicCollider;
import game.Component.GameObject;
import game.Component.HitBox;
import game.Component.OvalSprite;
import game.Component.RectSprite;
import game.Component.RigidBody;
import game.Component.Transform;
import game.Engine.TAGS;
import game.Requirments.Collider;
import game.Requirments.Vector;

public class BulletTest extends GameObject{
	
	public BulletTest(Transform player,double speedX,double speedY,TAGS bulletTag,Color bulletColor) {
		super(15,5);
		RectSprite vl = new RectSprite(this);
		
		//Color[] clr = new Color[] {Color.RED,Color.ORANGE,Color.GREEN,Color.PINK,Color.CYAN,Color.WHITE};
		//vl.size=15;
		vl.color=bulletColor;//clr[new Random().nextInt(clr.length)];
		addComponent(vl);
		HitBox hb =new HitBox(this);
		RigidBody t =new RigidBody(this);
		addComponent(t);
		addComponent(hb);
		t.addVelocity(speedX, speedY);
		transform.setPosition(player.getEndX(), player.getMidY());
		wakeUp();
		tag=bulletTag;
		Destroy(500);
	}
	
	
	public BulletTest(Vector player/* POSITION*/,double speedX,double speedY,TAGS bulletTag,Color bulletColor) {
		super(15,5);
		RectSprite vl = new RectSprite(this);
		name="bullet";
		//Color[] clr = new Color[] {Color.RED,Color.ORANGE,Color.GREEN,Color.PINK,Color.CYAN,Color.WHITE};
		//vl.size=15;
		vl.color=bulletColor;//clr[new Random().nextInt(clr.length)];
		addComponent(vl);
		HitBox hb =new HitBox(this);
		RigidBody t =new RigidBody(this);
		addComponent(t);
		addComponent(hb);
		t.addVelocity(speedX, speedY);
		transform.setPosition(player.x, player.y);
		wakeUp();
		tag=bulletTag;
		Destroy(500);
	}
	
	@Override
	public void onConstantCollision(Collider col) {
		//System.out.println("BULLET: "+col);
		if(col.isContains(TAGS.player)&&tag==TAGS.player_bullet)return;
		if(col.isContains(TAGS.alien)&&tag==TAGS.alien_bullet)return;
		if(col.isContains(TAGS.decor))return;
		Destroy(this);
	}
	
	@Override
	public void onDynamicCollision(DynamicCollider col) {
		
		//System.out.println("Bullet1::: "+col+" / "+tag);
		if(col.isContains(TAGS.player)&&tag==TAGS.player_bullet)return;
		if(col.isContains(TAGS.alien)&&tag==TAGS.alien_bullet)return;
		//System.out.println("Bullet::: "+col);
		Destroy(this);
		//*/
	}
	
	
}
