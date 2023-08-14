package game.Component.Utku.Alien;

import java.awt.Color;
import java.util.Random;

import game.Main;
import game.Component.Component;
import game.Component.GameObject;
import game.Component.RigidBody;
import game.Component.Transform;
import game.Component.Test.BulletTest;
import game.Component.Test.RigidTesst;
import game.Component.Utku.Player1;
import game.Engine.TAGS;

public class Alien1AI extends Component {

	private Player1 target;
	
	private RigidBody object_rigidbody;
	
	
	private int bullet_rate=500;
	private int total_time=0;
	
	private Random rand;
	
	private Transform gun_transform;
	
	public Alien1AI(GameObject obj, Transform gun_transform) {
		super(obj);
		rand=new Random();
		this.gun_transform=gun_transform;
	}
	@Override
	protected void onStart() {
		target=Player1.active_player;
		object_rigidbody=(RigidBody) object.getComponent(RigidBody.class);
		
		transform.scale.x=-1;
	}
	
	@Override
	protected boolean isLoopRequired() {
		return true;
	}
	
	@Override
	protected void onLoop() {
		total_time+=object.dt;	
			if (target==null) {
				menuAI();
			}
			else {
				gameAI();
			}
		    
	}
	
	private int walkTime = 750;
	private int walkDirection = 0;
	private void menuAI() {
		
		if (transform.position.x<-100) {
			
			walkDirection = 1;
			transform.scale.x = walkDirection;
			gun_transform.scale.x = walkDirection;
		}
		else if (transform.position.x>Main.app.getWidth()) {
			walkDirection = -1;
			transform.scale.x = walkDirection;
			gun_transform.scale.x = walkDirection;
			
		}
		
		if (total_time>walkTime) {
			total_time = 0;
			walkDirection = rand.nextInt(3)-1;
			if (walkDirection!=0) {
				transform.scale.x = walkDirection;
				gun_transform.scale.x = walkDirection;
				
			}
			
			
		}
		else {
			object_rigidbody.speed.x = walkDirection*3;
		}
	}
	
	
	
	
	
	
	private void gameAI() {
		
	    
	    //aralarýndaki mesafe 830 olunca yaklaþmaya baþlýyor
	    if(Math.abs(target.transform.position.x-transform.position.x) < 830 && Player1.active_player.getEnable()) {
		    //player ile enemy x ekseni arasýndaki fark
	    	boolean isLeft=(target.transform.position.x<transform.position.x);
	    	//target soldaysa (scale -1 sol)
		    transform.scale.x=isLeft?-1:1;
		    //silah döndürme
		    gun_transform.scale.x=isLeft?-1:1;
		    
		    //bullet_rate atýþ sýklýðý
		    if(total_time>bullet_rate) {
		    	//mermi gönderme
		    	new Alien1Bullet(((Alien1)object).gun.transform.getWorldPositionEnd(),30*(isLeft?-1:1), -5+rand.nextInt(10));
		    	total_time=0;
		    }
		    //mesafe 400 olana kadar koþuyor 
			if(Math.abs(target.transform.position.x-transform.position.x) > 400+rand.nextInt(4)*50) {
				object_rigidbody.speed.x=5*(isLeft?-1:1);
			}
			
		//boþluktan düþerse öldürüyoruz	
	    }if(transform.position.y>1200) {
			((Alien1)object).hitDamage(10);
		}
	}
	
}









//boolean isLeft=(target.transform.position.x-transform.position.x)
