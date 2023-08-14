package game.Component.S;

import java.awt.Color;

import game.Main;
import game.Component.DynamicCollider;
import game.Component.GameObject;
import game.Component.HitBox;
import game.Component.OvalSprite;
import game.Component.RectSprite;
import game.Component.RigidBody;
import game.Component.Transform;
import game.Component.S.comp.ObjectSprite;
import game.Component.S.comp.ShipControl;
import game.Component.Test.GunObject;
import game.Component.Test.ParticilarCreater;
import game.Component.Utku.SingleAnimation;
import game.Engine.TAGS;
import game.Requirments.Collider;
import game.Requirments.Vector;

public class PlayerShip extends GameObject{

	public static PlayerShip active_player;
	GameObject jet1;
	GameObject jet2;
	public RigidBody rb;
	boolean firstHit;
	double health = 10;
	GameObject healthBar;
	RectSprite rs;
	
	public PlayerShip(char[] keys) {
		active_player = this;
		rb = new RigidBody(this);
		rb.gravitiy = 0;
		rb.friction_air_x=2;
		rb.friction_air_y=2;
		rb.friction_ground=20;
		rb.bounce=0;
		tag=TAGS.player;
		
		GameObject gO = new GameObject(25,25);
//		gO.addComponent(new RectSprite(gO));
		gO.transform.setParent(this.transform);
		gO.transform.setPosition(30, 25);
		
		
		HitBox hb = new HitBox(this);
		
		transform.size = new Vector(83,58);
		
		addComponent(rb);
		addComponent(hb);
		
		ObjectSprite oS = new ObjectSprite(this, "img\\ship_r.png"); 
		addComponent(oS);
		
		addComponent(new ShipControl(this,keys,gO.transform));
		
		healthBar = new GameObject(50,3);
		rs = new RectSprite(healthBar);
		rs.color=Color.RED.darker();
		healthBar.addComponent(rs);
		healthBar.transform.setParent(transform);
		healthBar.transform.setPosition(5, -10);
		
		wakeUp();
		
		enableJets();
	}
	
	public void setHealth(int health) {
		this.health=health;
		healthBar.transform.size.x=health*5;
	}
	
	@Override
	public void onDynamicCollision(DynamicCollider col) {
		try {
		if(transform==null)return;
		if(col.isContains(TAGS.alien_bullet)){
			health -=0.2;
			healthBar.transform.size.x=health*5;
			if(health<0) {
				setEnable(false);
				jet1.setEnable(false);
				jet2.setEnable(false);
				new ShipExplosion(transform.position);
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if(transform == null )return;
						transform.setPosition(0, 0);
						jet1.setEnable(true);
						jet2.setEnable(true);
						setEnable(true);
						health = 10;
					}
				}).start();
			}
		}
		}catch (Exception e) {
			// TODO: handle exception
		}
		if(col.isContains(TAGS.black_hole) && false) {
			if(!firstHit) {
				firstHit = true;
				
				Main.nextScene2();
			}
			new SingleAnimation(this, "ship_r", 50, true);
		}
	}

	
	private void enableJets() {
		jet1=new GameObject(50,50);
		jet1.transform.setParent(this.transform);
		//jet1.addComponent(new OvalSprite(jet1));
		jet1.transform.setPosition(-50, 10);
		jet1.addComponent(new ParticilarCreater(jet1,10,10,300,Color.RED,Color.YELLOW));
		jet1.wakeUp();
		

		jet2=new GameObject(50,50);
		jet2.transform.setParent(this.transform);
//		jet2.addComponent(new OvalSprite(jet2));
		jet2.transform.setPosition(-50, 25);
		jet2.addComponent(new ParticilarCreater(jet2,10,10,300,Color.RED,Color.YELLOW));
		jet2.wakeUp();
	}
	
	@Override
	public void setEnable(boolean bool) {
		jet1.setEnable(bool);
		jet2.setEnable(bool);
		super.setEnable(bool);
	}
	
	@Override
	protected void onDestroy() {
		active_player=null;
	}
	
}
