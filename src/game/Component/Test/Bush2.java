package game.Component.Test;

import java.awt.HeadlessException;

import game.Component.DynamicCollider;
import game.Component.GameObject;
import game.Component.HitBox;
import game.Component.OvalSprite;
import game.Component.RectSprite;
import game.Component.RigidBody;
import game.Component.Transform;
import game.Component.Utku.Alien.Alien1AI;
import game.Engine.TAGS;
import game.Requirments.Collider;

public class Bush2 extends GameObject {
	

	private double health=10;
	private Transform PlayerToAttack;
	
	public Bush2(double x,double y) {
	super(90,100);
	//GameObject tree = new GameObject(25,30);
	name="Bush";
	tag=TAGS.alien;
	addComponent(new HitBox(this));
	RigidBody rb =new RigidBody(this);
	addComponent(rb);
	rb.friction_ground=15;
	addComponent(new DecorSprite(this,"C:\\Users\\utkua\\Downloads\\Fantasy Free Game Kit\\Background and Tilesets\\Objects\\PNG\\Bush_2.png"));
	//addComponent(new EnemyAITest(this));
	
	wakeUp();
	transform.setPosition(x,y);
	
	
	
	}
	
	@Override
	public void onDynamicCollision(DynamicCollider col) {
		System.out.println(col);
		if(col.isContains(TAGS.player_bullet)) {
			hitDamage(1);
		}
	}
	
	public void hitDamage(double damage) {
		health-=damage;
		if(health<=0) {
			health=10;
			setEnable(false);
			//transform.position.x=PlayerObject.active_player.transform.position.x+1000;
			//transform.position.y=400;
		}
			
	}

}
