package game.Component.Utku;

import java.awt.Color;

import game.Component.DynamicCollider;
import game.Component.GameObject;
import game.Component.HitBox;
import game.Component.OvalSprite;
import game.Component.RectSprite;
import game.Component.RigidBody;
import game.Component.Transform;
import game.Component.Test.GunObject;
import game.Component.Utku.Boss.Boss1;
import game.Component.Utku.Boss.Boss1AI;
import game.Engine.Camera;
import game.Engine.TAGS;
import game.Requirments.Collider;

public class Player1 extends GameObject {
	
	public double health=10;
	public GameObject healthBar;
	public RectSprite rs;
	
	public GunObject gun;
	
	public static Player1 active_player;
	
	public Player1(char[] keys) {

		super(90,100);
		active_player=this;
		this.health=10;
		

		gun = new GunObject();
		gun.transform.setParent(transform);
		gun.transform.setPosition(-20, 51);
		
		//GameObject tree = new GameObject(25,30);
		addComponent(new HitBox(this));
		//addComponent(new DecorSprite(this,"C:\\Users\\utkua\\Downloads\\Free-Game-Assets-Monsters\\Monsters\\MSHero_1\\PNG\\MSHero_1_Attack_Loop0003.png"));
		addComponent(new WalkAnimationControl(this, "pw", "pidle",50));
		
		name="Player";
		tag=TAGS.player;
		//addComponent(new OvalSprite(player,Color.ORANGE,true));
		//RigidTesst r= new RigidTesst(player);
		//transform.position.x=3000;
		transform.position.y=250;
		RigidBody r =new RigidBody(this);
		r.friction_ground=15;
		r.friction_air_x=10;
		r.friction_air_y=0.1;
		r.gravitiy=16;
		r.bounce=0.2f;
		addComponent(r);
		
		addComponent(new Player1Move(this,gun,keys));
		wakeUp();
		

		healthBar = new GameObject(50,3);
		rs = new RectSprite(healthBar);
		rs.color=Color.RED.darker();
		healthBar.addComponent(rs);
		healthBar.transform.setParent(transform);
		healthBar.transform.setPosition(-20, 8);
		
		
	}

	public void setHealth(int health) {
		this.health=health;
		healthBar.transform.size.x=health*5;
		
	}
	
	@Override
	public void onDynamicCollision(DynamicCollider col) {
		//System.out.println(col);
		if(col.isContains(TAGS.alien_bullet)) {
			hitDamage(0.2f);
		}
	}
	
	public void hitDamage(double damage) {
		health-=damage;
		healthBar.transform.size.x=health*5;
		if(health<=0) {
			health=10;
			Player1Death pd = new Player1Death(transform.position);
			pd.transform.scale.x=transform.scale.x;
			Camera.active.setFollow(pd.transform);
			transform.position.x=Boss1.active!=null&&((Boss1AI)Boss1.active.getComponent(Boss1AI.class)).startAttack?3700:0;
			transform.position.y=100;
			final Transform tr = transform;
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						Thread.sleep(1500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Camera.active.setFollow(tr);
				}
			}).start();
		}
			
	}
	@Override
	protected void onDestroy() {
		active_player=null;
	}
	
}
