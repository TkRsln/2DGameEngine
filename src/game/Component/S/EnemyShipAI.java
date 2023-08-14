package game.Component.S;

import java.awt.Color;
import java.util.Random;

import game.Component.Component;
import game.Component.GameObject;
import game.Component.RigidBody;
import game.Component.Transform;
import game.Component.Test.BulletTest;
import game.Component.Utku.Alien.Alien1Bullet;
import game.Engine.TAGS;
import game.Requirments.Vector;

public class EnemyShipAI extends Component {

	private PlayerShip target;
	
	private RigidBody ship_rigidbody;

	
	
	private int bullet_rate=500;
	private int total_time=0;
	
	private Random rand;
	
	private GameObject smoke;
	
	public EnemyShipAI(GameObject obj, GameObject smoke) {
		super(obj);
		this.smoke=smoke;
		rand=new Random();
	}
	@Override
	protected void onStart() {
		target=PlayerShip.active_player;
		ship_rigidbody=(RigidBody) object.getComponent(RigidBody.class);
		
		transform.scale.x=-1;
	}
	
	@Override
	protected boolean isLoopRequired() {
		return true;
	}
	
	@Override
	protected void onLoop() {
		    total_time+=object.dt;	//geçen zaman
		    
		    //aralarýndaki mesafe 830 olunca yaklaþmaya baþlýyor
		    if(Math.abs(target.transform.position.x-transform.position.x) < 830 && PlayerShip.active_player.getEnable()) {
			    //player ile enemy x ekseni arasýndaki fark
		    	boolean isLeft=(target.transform.position.x<transform.position.x);
		    	boolean isUp=(target.transform.position.y<transform.position.y);
		    	//target soldaysa (scale -1 sol)
			    transform.scale.x=isLeft?-1:1;
			    
			    //bullet_rate atýþ sýklýðý
			    if(total_time>bullet_rate) {
			    	//mermi gönderme
			    	new Alien1Bullet(transform.getMidPoint(),30*(isLeft?-1:1), -5+rand.nextInt(10));
			    	total_time=0;
			    }
			    
			    smoke.setEnable(ship_rigidbody.speed.magnitude()>1);
			    
			    if(Math.abs(target.transform.position.y-transform.position.y) > 10) {
			    	ship_rigidbody.acceleration.y=-5*(isUp?-1:1);
			    }
			    
			    //mesafe 400 olana kadar koþuyor 
				if(Math.abs(target.transform.position.x-transform.position.x) > 400+rand.nextInt(4)*50) {
					ship_rigidbody.speed.x=5*(isLeft?-1:1);
				}
				
			
		    }
	}
}
