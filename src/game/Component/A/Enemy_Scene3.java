package game.Component.A;

import java.awt.Color;
import java.util.Random;

import game.Main;
import game.Component.DynamicCollider;
import game.Component.GameObject;
import game.Component.HitBox;
import game.Component.RectSprite;
import game.Component.RigidBody;
import game.Component.S.ShipHealth;
import game.Component.Utku.SingleAnimation;
import game.Engine.TAGS;
import game.Requirments.Vector;

public class Enemy_Scene3 extends GameObject {
	
	
	double enemy_health = 10;
	public GameObject healthBar;
	public RectSprite rs;
	
	public Enemy_Scene3(double posX, double posY) {
		
		tag = TAGS.alien;
		
			
		RigidBody rbEn = new RigidBody(this);
		rbEn.friction_air_y = 0.2;
		rbEn.gravitiy = 0;
		
		HitBox hbEn = new HitBox(this);
		transform.size = new Vector(100,100);
		addComponent(rbEn);
		addComponent(hbEn);
		
		EnemyAI_Scene3 enemyAI = new EnemyAI_Scene3(this);
		addComponent(enemyAI);
		
		
		
		transform.size.x = 192;
		transform.size.y = 150;
		transform.setPosition(posX,posY);
		addComponent(new SingleAnimation(this,"Boss2-Attack", 300,true));
		
		healthBar = new GameObject(50,3);
		rs = new RectSprite(healthBar);
		rs.color=Color.green.darker();
		healthBar.addComponent(rs);
		healthBar.transform.setParent(transform);
		healthBar.transform.setPosition(5, -10);
		wakeUp();
	}
	
	@Override
	public void onDynamicCollision(DynamicCollider col) {
		if (col.isContains(TAGS.player_bullet)) {
			enemy_health -= 0.5;
			healthBar.transform.size.x=enemy_health*5;
			if(enemy_health<3) {
				rs.color=Color.red.darker();
			}
			else if(enemy_health<6) {
				rs.color=Color.yellow.darker();
			}
			if (enemy_health<=0) {
				
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						new Enemy_Scene3(Player_Scene3.active_pl.transform.position.x+1000, 100);
					}
				}).start();
				Random rand = new Random();
				
				if(rand.nextInt(100)<30) {
					Health_Scene3 h3 = new Health_Scene3(transform);					
				}
				Main.addScore(4);
				Destroy();
				
			}
		}
	}
}
