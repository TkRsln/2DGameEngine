package game.Component.Utku.Alien;

import java.awt.Color;
import java.util.Random;

import game.Main;
import game.Component.DynamicCollider;
import game.Component.GameObject;
import game.Component.HitBox;
import game.Component.RectSprite;
import game.Component.RigidBody;
import game.Component.Test.DecorSprite;
import game.Component.Test.GunObject;
import game.Component.Utku.ChestObject;
import game.Component.Utku.HealthObject;
import game.Component.Utku.Player1;
import game.Component.Utku.WalkAnimationControl;
import game.Component.Utku.Boss.Boss1;
import game.Component.Utku.Boss.Boss1AI;
import game.Engine.TAGS;
import game.Requirments.Collider;
import game.Requirments.Vector;

public class Alien1 extends GameObject{
	
	
	public GameObject gun;
	

	private double health=10;
	public GameObject healthBar;
	public RectSprite rs;
	
	public Alien1(double posX,double posY) {

		 	super(90,100);
			transform.setPosition(posX, posY);
			
			//GameObject tree = new GameObject(25,30);
			addComponent(new HitBox(this));
			//addComponent(new DecorSprite(this,"C:\\Users\\utkua\\Downloads\\Alien-VS-Zombies\\Aliens VS Zombies\\Alien_3\\PNG\\Alien_3_Attacking0001.png"));
			addComponent(new WalkAnimationControl(this, "aw", "aidle",50));
			
			name="Alien";
			tag=TAGS.alien;
			//addComponent(new OvalSprite(player,Color.ORANGE,true));
			//RigidTesst r= new RigidTesst(player);
			
			RigidBody r =new RigidBody(this);
			r.friction_ground=15;
			r.friction_air_x=10;
			r.bounce=0.2f;
			addComponent(r);
			
			gun = new GunObject();
			gun.transform.setParent(this.transform);
			gun.transform.setPosition(-10, 51);
			gun.transform.scale.x=-1;
			
			addComponent(new Alien1AI(this,gun.transform));
			
			wakeUp();
		 
			

			healthBar = new GameObject(50,3);
			rs = new RectSprite(healthBar);
			rs.color=Color.green.darker();
			healthBar.addComponent(rs);
			healthBar.transform.setParent(transform);
			healthBar.transform.setPosition(5, -10);
			
	}
	 

		@Override
		public void onDynamicCollision(DynamicCollider col) {
			if(col.isContains(TAGS.player_bullet)) {
				//System.out.println("Alien:: "+col);
				hitDamage(1);
				//col.getGameObjectByTag(TAGS.player_bullet).Destroy();
			}
		}
		
		public void hitDamage(double damage) {
			health-=damage;
			healthBar.transform.size.x=health*5;
			if(health<3) {
				rs.color=Color.red.darker();
			}
			else if(health<6) {
				rs.color=Color.yellow.darker();
			}
			if(health<=0) {
				//health=10;
				setEnable(false);
				Alien1Dead d=new Alien1Dead(transform.position);
				if(new Random().nextInt(2)==1)
					new HealthObject(transform.position.x, transform.position.y);
				if(transform.scale!=null)
					d.transform.scale.x=transform.scale.x;
				
				
				
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						try {
							if(Boss1.active==null||((Boss1AI)Boss1.active.getComponent(Boss1AI.class)).startAttack)return;
							//setEnable(true);
							double posx=Player1.active_player.transform.position.x+new Random().nextInt(5)*100+1050;
							double posy=350;
							Thread.sleep((posx>4000?5000:1000)+new Random().nextInt(4)*500);
							if(posx>4000) {
								posy=-100;
								posx=5000;
								Thread.sleep(1000);
							}
								new Alien1(posx, posy);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				}).start();
				Destroy();
				
				//transform.position.x=PlayerObject.active_player.transform.position.x+1000;
				//transform.position.y=400;
				//((RigidBody)getComponent(RigidBody.class)).speed=new Vector();
			}
				
		}
		
		@Override
		protected void onDestroy() {
			if(Player1.active_player==null)return;
			Main.addScore(4);
		}
}
