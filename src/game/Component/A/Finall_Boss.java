package game.Component.A;

import java.awt.Color;

import game.Main;
import game.Component.DynamicCollider;
import game.Component.GameObject;
import game.Component.HitBox;
import game.Component.OvalSprite;
import game.Component.RectSprite;
import game.Component.RigidBody;
import game.Component.Utku.SingleAnimation;
import game.Engine.TAGS;
import game.Requirments.Collider;

public class Finall_Boss extends GameObject {
	double boss_health = 10;
	public GameObject shootPoint;
	public GameObject healthBar;
	public RectSprite rs;
	
	
	public Finall_Boss(int posX, int posY) {
		tag = TAGS.alien;
		
		transform.size.x = 400;
    	transform.size.y = 400;
    	transform.setPosition(posX,posY);
    //	transform.scale.x = -1;
    	
    	
    	RigidBody rbFboss = new RigidBody(this);
    	addComponent(rbFboss);
    	rbFboss.speed.x = -8;
    	addComponent(new BossAI(this));
    	
    	HitBox hbFboss = new HitBox(this);
    	addComponent(hbFboss);
    	
    	
    	//addComponent(new SingleAnimation(this,"Evil Grass2_" , 300,true));
    	//addComponent(new SingleAnimation(this,"FinalBossIdle", 400, true));
    	addComponent(new Boss_Animations(this));
    	healthBar = new GameObject(80,5);
		rs = new RectSprite(healthBar);
		rs.color=Color.magenta.darker();
		healthBar.addComponent(rs);
		healthBar.transform.setParent(transform);
		healthBar.transform.setPosition(-15, 150);
		
    	wakeUp();
    	
    	
    	shootPoint = new GameObject(25,25);
    	shootPoint.transform.setPosition(-85, 220);
    	shootPoint.addComponent(new OvalSprite(shootPoint));
    	shootPoint.transform.setParent(transform);
    	
    }
		@Override
		public void onDynamicCollision(DynamicCollider col) {
			if (col.isContains(TAGS.player_bullet)) {
				boss_health -= 5;
				healthBar.transform.size.x=boss_health*8;
				if (boss_health<0) {
					
					Main.addScore(7);
					new Final_Boss_Death(transform.position);
					doorScene3.active.openDoor();		
					
					Destroy();
					
					
				}
			}
		}	
}
