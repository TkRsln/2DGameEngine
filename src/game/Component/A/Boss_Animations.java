package game.Component.A;

import java.awt.image.BufferedImage;

import game.Component.Component;
import game.Component.GameObject;
import game.Component.RigidBody;
import game.Component.Utku.Animation;

public class Boss_Animations extends Component {

	Animation walk = new Animation();
	Animation idle = new Animation();
	Animation attack = new Animation();
	RigidBody animRb ;
	boolean isWalking = false;
	int walkanimspeed = 200;
	int idleanimspeed = 300;
	int animtotime = 0;
	
	public Boss_Animations(GameObject obj) {
		super(obj);
		walk.loadAnim("FinalBossWalk");
		idle.loadAnim("FinalBossIdle");
		attack.loadAnim("FinalBossAttack");
	}
	@Override
	protected boolean isLoopRequired() {
		return true;
	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
	animRb = (RigidBody) object.getComponent(RigidBody.class);
	}
	
	@Override
	protected void onLoop() {
		animtotime += object.dt;
		
		if (animRb.speed.magnitude() > 1) {
			isWalking = true;	
			if (animtotime > walkanimspeed ) {
				walk.next();
				animtotime = 0;
			}
		}
		else {
			isWalking = false;
			if (animtotime > idleanimspeed ) {
				idle.next();
				animtotime = 0;
			}
		}
		
	}
	
	@Override
	protected BufferedImage onRender() {
		if (isWalking) {
			return (BufferedImage) walk.getPNG();	
			
		}
		else {
			return (BufferedImage) idle.getPNG();
		}
	}
	
	
	
	
	
	
	
	
	
}
