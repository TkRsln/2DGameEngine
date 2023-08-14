package game.Component.Utku;

import java.awt.Image;
import java.awt.image.BufferedImage;

import game.Component.Component;
import game.Component.GameObject;
import game.Component.RigidBody;

public class WalkAnimationControl extends Component{
	
	private Animation walk=new Animation();
	private Animation idle=new Animation();
	private Animation jump;
	private RigidBody rb;
	
	
	public WalkAnimationControl(GameObject obj,String walk_name, String idle_name) {
		super(obj);
		walk.loadAnim(walk_name);
		idle.loadAnim(idle_name);
	}
	public WalkAnimationControl(GameObject obj,String walk_name, String idle_name,int walk_speed) {
		super(obj);
		walk.loadAnim(walk_name);
		idle.loadAnim(idle_name);
		this.walk_speed=walk_speed;
	}
	public WalkAnimationControl(GameObject obj,String walk_name, String idle_name,int walk_speed,double walk_tolerance) {
		super(obj);
		walk.loadAnim(walk_name);
		idle.loadAnim(idle_name);
		this.walk_speed=walk_speed;
		this.walk_tolerance=walk_tolerance;
	}public WalkAnimationControl(GameObject obj,String walk_name, String idle_name,String jump_name,int walk_speed,double walk_tolerance) {
		super(obj);
		jump=new Animation(false);
		walk.loadAnim(walk_name);
		idle.loadAnim(idle_name);
		jump.loadAnim(jump_name);
		this.walk_speed=walk_speed;
		this.walk_tolerance=walk_tolerance;
	}
	
	@Override
	protected void onDestroyComponent() {
		walk=null;
		idle=null;
	}
	
	@Override
	protected void onStart() {
		rb=(RigidBody) object.getComponent(RigidBody.class);
	}

	private int time_total=0;
	private int walk_speed=200;
	private double walk_tolerance=1;
	private boolean isWalking=false;
	
	@Override
	protected boolean isLoopRequired() {
		return true;
	}
	
	@Override
	protected void onLoop() {
		time_total+=object.dt;
		if(rb.speed.magnitude()>walk_tolerance &&(jump==null || rb.onGround )) {
			if(time_total>walk_speed) {
				time_total=0;
				walk.next();
				if(jump!=null)jump.reset();
				isWalking=true;
			}
		}else if(jump!=null ) {
			if(time_total>walk_speed) {
				time_total=0;
				jump.next();
				isWalking=false;
			}
			
		}else {
			if(time_total>walk_speed) {
				time_total=0;
				idle.next();
				isWalking=false;
			}
		}
	}
	
	@Override
	protected BufferedImage onRender() {
		if(rb==null)return null;
		if(jump!=null&&!rb.onGround) {
			return (BufferedImage) jump.getPNG();
		}else {
			return (BufferedImage) (isWalking?walk.getPNG():idle.getPNG());
		}
	}
	
	
	
	
	
	
}
