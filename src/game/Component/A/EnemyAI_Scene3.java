package game.Component.A;

import java.awt.Color;
import java.util.Random;

import game.Main;
import game.Component.Component;
import game.Component.GameObject;
import game.Component.RigidBody;
import game.Component.S.PlayerShip;
import game.Component.Test.BulletTest;
import game.Engine.TAGS;

public class EnemyAI_Scene3 extends Component {
	//private Player_Scene3 target;
	private RigidBody plRb;
	private Random rand;
	
	
	public EnemyAI_Scene3(GameObject goEnemy) {
		super(goEnemy);
		rand = new Random();
	}
	
	@Override
	protected boolean isLoopRequired() {
		// TODO Auto-generated method stub
		return true;
	}
	int tm;
	
	@Override
	protected void onStart() {
		//target = Player_Scene3.active_pl;
		plRb = (RigidBody) object.getComponent(RigidBody.class);
	}
	
	
	@Override
	protected void onLoop() {
		tm += object.dt;
		if (Player_Scene3.active_pl==null) {
			menuAI();
			return;
			
		}
		int distance = (int) Math.abs(Player_Scene3.active_pl.transform.position.x-transform.position.x);
		
	
	    	if(Math.abs(Player_Scene3.active_pl.transform.position.x-transform.position.x) < 830 && Player_Scene3.active_pl.getEnable()) {  
	    	boolean isLeft=(Player_Scene3.active_pl.transform.position.x<transform.position.x);
		    transform.scale.x=isLeft?1:-1;
		
			if (tm>1000&&Player_Scene3.active_pl.getEnable()) {
				tm = 0;
				Bullet b1 = new Bullet(object.transform.position.x, object.transform.position.y, 
						25, 25,0.02*(Player_Scene3.active_pl.transform.position.x-object.transform.position.x),
						0.02*(Player_Scene3.active_pl.transform.position.y-object.transform.position.y), "img/Boss1 Bullet Impact1.png");
				b1.tag = TAGS.alien_bullet;
			}
			
			if(Math.abs(Player_Scene3.active_pl.transform.position.x-transform.position.x) > 400+rand.nextInt(4)*50) {
				plRb.speed.x=4*(isLeft?-1:1);
			}
			
			
			if(Math.abs(Player_Scene3.active_pl.transform.position.x-transform.position.x)<500 ) {
				distance = (int) (Player_Scene3.active_pl.transform.position.y - distance/2);
				if (distance > Player_Scene3.active_pl.transform.position.y) {
					distance = (int) Player_Scene3.active_pl.transform.position.y;
				}
				transform.position.y = distance;
				
			}
			
			
			
	    }
		
	    
	}
	private int walkTime = 750;
	private int walkDirection = 0;
	private int walkDirectionY = 0;
	private void menuAI() {
		
		if (transform.position.x<-100) {
			
			walkDirection = 1;
			transform.scale.x = walkDirection*-1;
			
		}
		else if (transform.position.x>Main.app.getWidth()) {
			walkDirection = -1;
			transform.scale.x = walkDirection*-1;
		
		}
		if (transform.position.y<0) {
			walkDirectionY = +1;
		
		}
		
		if (tm>walkTime) {
			tm = 0;
			walkDirection = rand.nextInt(3)-1;
			walkDirectionY = rand.nextInt(3)-1;
			if (walkDirection!=0) {
				transform.scale.x = walkDirection*-1;
			
			}
			plRb.gravitiy = 2;
			
		}
		else if(walkDirection!=0) {
			plRb.speed.x = walkDirection*2;
			plRb.speed.y = walkDirectionY*1;
		}
	}
	
	
	
}
