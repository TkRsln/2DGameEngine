package game.Component.Utku.Boss;

import java.awt.image.BufferedImage;

import game.Component.Component;
import game.Component.GameObject;
import game.Component.RigidBody;
import game.Component.Utku.Animation;

public class Boss1Animations extends Component{

	
	private Boss1AI bossAi;
	private RigidBody bossRB;
	private int ai_status=0;
	
	private Animation walk=new Animation();
	private Animation idle=new Animation();
	private Animation attack=new Animation();
	private Animation jump=new Animation(false);
	
	private int speed_walk=150;
	private int speed_idle=150;
	private int speed_attack=150;
	private int speed_jump=150;
	
	private int total_time=0;
	
	// 0:Idle - 1:Walk - 2:Jump - 3:Attack
	public Boss1Animations(GameObject obj) {
		super(obj);
		walk.loadAnim("BossRun");
		idle.loadAnim("BossIdle");
		attack.loadAnim("BossAttack");
		jump.loadAnim("BossJump");
	}
	
	
	@Override
	protected void onStart() {
		bossAi=(Boss1AI) object.getComponent(Boss1AI.class);
		bossRB=(RigidBody) object.getComponent(RigidBody.class);
	}
	
	@Override
	protected boolean isLoopRequired() {
		return true;
	}
	@Override
	protected void onLoop() {
		total_time+=object.dt;
		ai_status=bossAi.status;
		
		if(ai_status==0) {
			attack.reset();
			if(bossRB.onGround&&speed_walk<total_time&&bossRB.speed.magnitude()>0.10 ) {
				walk.next();
				jump.reset();
				total_time=0;
			}else if(bossRB.onGround&&speed_idle<total_time&&bossRB.speed.magnitude()<=0.10) {
				idle.next();
				jump.reset();
				total_time=0;
			}else if(!bossRB.onGround&&speed_jump<total_time) {
				jump.next();
				total_time=0;
			}
		}else if(speed_attack<total_time) {
			attack.next();
			total_time=0;
		}
		
	}
	@Override
	protected BufferedImage onRender() {
		if(bossRB==null)return null;
		if(ai_status==0) {
			if(bossRB.onGround&&bossRB.speed.magnitude()>1 ) {
				return (BufferedImage) walk.getPNG();
			}else if(bossRB.onGround&&bossRB.speed.magnitude()<=1 ) {
				return (BufferedImage) idle.getPNG();
			}else if(!bossRB.onGround) {
				return (BufferedImage) jump.getPNG();
			}else {
				return (BufferedImage) idle.getPNG();
			}
		}else 
			return (BufferedImage) attack.getPNG();
		
	}
	
	private void resetAnim(int anim) {
		if(ai_status==0)
			idle.reset();
		else if(ai_status==1)
			walk.reset();
		else if(ai_status==2)
			jump.reset();
		else if(ai_status==3)
			attack.reset();
	}
	
	
	
	
}
