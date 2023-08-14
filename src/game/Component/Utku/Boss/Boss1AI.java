package game.Component.Utku.Boss;

import java.util.Random;

import game.Main;
import game.Component.Component;
import game.Component.GameObject;
import game.Component.RigidBody;
import game.Component.Utku.HealthObject;
import game.Component.Utku.Player1;
import game.Engine.Camera;

public class Boss1AI extends Component{

	private RigidBody rb;
	
	public int status=0; // 0:Normal / - 1:Attack
	
	
	private int trigger_distance=800;
	private int close_distance=650;
	public boolean startAttack=false;
	private float run_speed=7;
	private int jump_time=3000;
	private int attack_cooldown=750;
	private int attack_anim_time=1700;
	private int[] attack_seconds= {500,1300};
	private boolean[] attacked;
	private float bullet_speed=15;
	private int health_drop_percentage=30;
	
	
	private int total_time_jump=0;
	private int total_time_attack=0;
	private int total_time_attackcool=0;
	private boolean attack_anim=false;
	
	private Random random=new Random();
	
	private boolean onground=true;
	
	private int shake_count=0;
	private int shake_wait_time=250;
	
	
	public Boss1AI(GameObject obj) {
		super(obj);
		
	}
	
	@Override
	protected void onStart() {
		rb = (RigidBody) object.getComponent(RigidBody.class);
		attacked=new boolean[attack_seconds.length];
	}
	
	@Override
	protected void onLoop() {
		if (Player1.active_player==null) {
			menuAI();
			return;
		}
		
		
		double distance =transform.getMidX()- Player1.active_player.transform.getMidX();
		if(!startAttack) {
			startAttack=trigger_distance>Math.abs(distance);
			if(startAttack)((Boss1)object).createBlockPlatform();
		}
		else {
			
			if(rb.onGround&&!onground&&shake_count>shake_wait_time) {
				onground=true;
				Main.cam1.active.startShake(250);
				if(Main.nop==2)Main.cam2.startShake(250);
			}
			
			shake_count+=object.dt;
			total_time_attackcool+=object.dt;
			total_time_jump+=object.dt;
			if(distance<0)
				transform.scale.x=-1;
			else 
				transform.scale.x=1;
			
			if(!attack_anim&&Math.abs(distance)>close_distance) {
				status=0;
				rb.addVelocity((run_speed-Math.abs(rb.speed.x))*object.dt/1000.0*-transform.scale.x, 0);
			}else if(rb.speed.magnitude()<0.50&&total_time_attackcool>attack_cooldown) {
				status=1;
				total_time_attack+=object.dt;
				attack_anim=true;
				if(checkCanAttack(total_time_attack)) {
					new Boss1Bullet(transform, -transform.scale.x*bullet_speed, 0);
				}
				if(attack_anim_time<total_time_attack) {
					attack_anim=false;
					total_time_attackcool=0;
					total_time_attack=0;
					status=0;
					attacked=new boolean[attack_seconds.length];
					//System.out.println("END ATTACK");
				}
				//System.out.println("A: "+total_time_attack);
			}
			
			
			if((!attack_anim&&jump_time<total_time_jump&&total_time_attackcool<attack_cooldown)) {
				
				onground=false;
				shake_count=0;
				
				status=0;
				rb.addVelocity(6*-transform.scale.x, 8);
				total_time_jump=0;
				if(random.nextInt(100)+1<health_drop_percentage )
					dropHealth();
			}
		}
	}
	
	private int total_time = 0;
	private int walkTime = 1250;
	private int walkDirection = 0;
	private void menuAI() {
		total_time+=object.dt;	
		if (transform.position.x<-100) {
			
			walkDirection = 1;
			transform.scale.x = walkDirection*-1;
			
		}
		else if (transform.position.x>Main.app.getWidth()) {
			walkDirection = -1;
			transform.scale.x = walkDirection*-1;
			
			
		}
		
		if (total_time>walkTime) {
			total_time = 0;
			walkDirection = random.nextInt(3)-1;
			if (walkDirection!=0) {
				transform.scale.x = walkDirection*-1;
			
				
			}
			
			
		}
		else {
			rb.speed.x = walkDirection*1.5;
		}
	}
	
	
	
	
	
	
	private boolean checkCanAttack(int dt) {
		for(int i=0;i<attack_seconds.length;i++) {
			if(attack_seconds[i]<dt && !attacked[i]) {
				attacked[i]=true;
				return true;
			}
		}
		return false;
	}
	
	private void dropHealth() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(650);
					new HealthObject(transform);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

}
