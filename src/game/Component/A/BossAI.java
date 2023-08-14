package game.Component.A;

import game.Component.Component;
import game.Component.GameObject;
import game.Component.RigidBody;
import game.Component.Transform;
import game.Engine.TAGS;

public class BossAI extends Component {
	private RigidBody rigidBoss;
	int trigger = 600;
	int attackRange = 1500;
	boolean startAttack = false;
	
	public BossAI(GameObject goboss) {
		super(goboss);	
	}
	
	@Override
	protected boolean isLoopRequired() {
		// TODO Auto-generated method stub
		return true;
	}
	
	int deltime;
	@Override
	protected void onStart() {
		
		rigidBoss = (RigidBody) object.getComponent(RigidBody.class);	
	}
	
	
	@Override
	protected void onLoop() {
		double dis = Math.abs(transform.getMidX() - Player_Scene3.active_pl.transform.getMidX());
		if (!startAttack) {
			startAttack = dis < trigger;
		
			if (!startAttack) {
				return;
			}
		
		}
		deltime += object.dt;
		boolean isLeft=(Player_Scene3.active_pl.transform.position.x<transform.position.x);
	    transform.scale.x=isLeft?1:-1;
	    
		
		
	if (dis > 400) {
		rigidBoss.speed.x = 2*(isLeft?-1:1);
		
	}
	//System.out.println((deltime>1000)+" "+Player_Scene3.active_pl.getEnable()+" "+deltime);
	if (deltime>1000&&Player_Scene3.active_pl.getEnable()&&dis<attackRange) {
		deltime = 0;
		Transform point = ((Finall_Boss) object).shootPoint.transform;
		
		
		Bullet b2 = new Boss_Bullet(point.getWorldMiddlePosition().x, point.getWorldMiddlePosition().y
				,0.02*(Player_Scene3.active_pl.transform.position.x-object.transform.position.x),
				0.02*(Player_Scene3.active_pl.transform.position.y-object.transform.position.y));
		b2.tag = TAGS.alien_bullet;
	
		}
	
	}
	
	
	
	
	 
}
