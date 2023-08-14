package game.Component.S;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.Random;

import game.Main;
import game.Component.DynamicCollider;
import game.Component.GameObject;
import game.Component.HitBox;
import game.Component.RectSprite;
import game.Component.RigidBody;
import game.Component.S.comp.ObjectSprite;
import game.Component.S.comp.ShipControl;
import game.Component.Test.ParticilarCreater;
import game.Component.Utku.HealthObject;
import game.Engine.TAGS;
import game.Requirments.Collider;
import game.Requirments.Vector;

public class EnemyShip extends GameObject {

	Random rand = new Random();
	
	double health = 10;
	GameObject healthBar;
	RectSprite rs;
	
	public EnemyShip(double posx, double posy) {
		RigidBody rb = new RigidBody(this);
		rb.gravitiy = 0;
		rb.bounce=0;
		rb.friction_air_x=2;
		rb.friction_air_y=2;
		rb.friction_ground=20;
		tag= TAGS.alien;
		
		HitBox hb = new HitBox(this);
		
		transform.size = new Vector(75,75);
		transform.setPosition(posx, posy);
		
		addComponent(rb);
		addComponent(hb);
		
		ObjectSprite oS = new ObjectSprite(this, "img\\Ship1.png"); 
		addComponent(oS);
		GameObject smoke = new GameObject();
		
//		addComponent(new ShipControl(this));
		addComponent(new EnemyShipAI(this,smoke));
		wakeUp();
		
		healthBar = new GameObject(30,3);
		rs = new RectSprite(healthBar);
		rs.color=Color.GREEN;
		healthBar.addComponent(rs);
		healthBar.transform.setParent(transform);
		healthBar.transform.setPosition(15, 10);
		
		smoke.addComponent(new ParticilarCreater(smoke,50,15,500,Color.GREEN,Color.YELLOW));
		smoke.transform.setParent(this.transform);
		smoke.transform.setPosition(20,20);
		smoke.wakeUp();
	
		
	}
	@Override
	public void onDynamicCollision(DynamicCollider col) {
		if(col.isContains(TAGS.player_bullet)) {
			health -= 1;
			healthBar.transform.size.x=health*3;
			if(health<3) {
				rs.color=Color.red.darker();
			}
			else if(health<6) {
				rs.color=Color.yellow.darker();
			}
			if(health <= 0) {
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						try {
							Thread.sleep(dt);
							Thread.sleep(1000+ rand.nextInt(6)*1000);
							if(PlayerShip.active_player!=null)
								new EnemyShip(PlayerShip.active_player.transform.position.x+1000, PlayerShip.active_player.transform.position.y);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}).start();
				if(rand.nextInt(100)<30) {
					ShipHealth hO = new ShipHealth(transform);					
				}
				Destroy();
			}
		}
	}
	
	@Override
	protected void onDestroy() {
		Main.addScore(4);
	}
}
