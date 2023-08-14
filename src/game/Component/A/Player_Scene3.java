package game.Component.A;

import java.awt.Color;

import game.Component.Component;
import game.Component.DynamicCollider;
import game.Component.GameObject;
import game.Component.HitBox;
import game.Component.RectSprite;
import game.Component.RigidBody;
import game.Component.A.movesandetc.PlayerMove;
import game.Component.A.movesandetc.Scene3_PNG_Creator;
import game.Component.S.PlayerShip;
import game.Component.S.comp.ObjectSprite;
import game.Component.S.comp.ShipControl;
import game.Component.Test.GunObject;
import game.Component.Utku.WalkAnimationControl;
import game.Engine.TAGS;
import game.Requirments.Vector;

public class Player_Scene3 extends GameObject {
	
	
	public static Player_Scene3 active_pl;
	double health = 10;
	public GameObject healthBar;
	public RectSprite rs;
	
	public Player_Scene3(char[] keys) {
		RigidBody rb3 = new RigidBody(this);
		rb3.friction_ground=15;
		rb3.friction_air_y = 0.2;
		rb3.friction_air_x = 3;
		rb3.bounce=0.2f;

		GunObject gun3 = new GunObject();
		
		HitBox hb3 = new HitBox(this);
		transform.size = new Vector(100,100);
		addComponent(rb3);
		addComponent(hb3);
		//addComponent(new Scene3_PNG_Creator(this,"img/Animations/pidle1.png"));
		addComponent(new WalkAnimationControl(this, "pw", "pidle",50));
		addComponent(new PlayerMove(this,gun3,rb3,keys));
		gun3.transform.setParent(this.transform);
		gun3.transform.setPosition(-20, 51);
		
		tag = TAGS.player;
		active_pl = this;
		
		healthBar = new GameObject(50,3);
		rs = new RectSprite(healthBar);
		rs.color=Color.RED.darker();
		healthBar.addComponent(rs);
		healthBar.transform.setParent(transform);
		healthBar.transform.setPosition(-20, 8);
		wakeUp();
	}
	
	@Override
	public void onDynamicCollision(DynamicCollider col) {
		if (col.isContains(TAGS.alien_bullet)) {
			health -= 1;
			healthBar.transform.size.x=health*5;
			if (health<0) {
				setEnable(false);
				new Player_Scene3_Death(transform.position);			
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						try {
							Thread.sleep(4000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if(transform==null) return;
						transform.setPosition(0,0);
						setEnable(true);
						health = 10;
					}
				}).start();
				
			}
			
		}
	}
	
	public void setHealth(int health) {
		this.health = health;
		healthBar.transform.size.x=health*5;
	}
	
	@Override
	protected void onDestroy() {
		active_pl=null;
	}
}
