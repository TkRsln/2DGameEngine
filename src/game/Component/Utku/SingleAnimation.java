package game.Component.Utku;

import java.awt.image.BufferedImage;

import game.Component.Component;
import game.Component.GameObject;

public class SingleAnimation extends Component{

	private Animation anim;
	private int anim_speed=200;
	private int time_total=0;
	private boolean autoStart = true;
	
	public SingleAnimation(GameObject obj,String name,int anim_speed) {
		super(obj);
		anim=new Animation(false);
		anim.loadAnim(name);
		this.anim_speed=anim_speed;
	}
	
	public SingleAnimation(GameObject obj,String name,int anim_speed,boolean loop) {
		super(obj);
		anim=new Animation(loop);
		anim.loadAnim(name);
		this.anim_speed=anim_speed;
	}
	public SingleAnimation(GameObject obj,String name,int anim_speed,boolean loop,boolean autoStart) {
		super(obj);
		anim=new Animation(loop);
		anim.loadAnim(name);
		this.anim_speed=anim_speed;
		this.autoStart = autoStart;
	}
	
	public void playAnimation() {
		autoStart = true;
		
	}
	
	
	
	
	
	
	
	
	@Override
	protected boolean isLoopRequired() {
		return true;
	}
	@Override
	protected void onLoop() {
		if (!autoStart) {
			return;
		}
		time_total+=object.dt;
		if(time_total>anim_speed) {
			time_total=0;
			anim.next();
		}
		
		super.onLoop();
	}

	@Override
	protected BufferedImage onRender() {
		// TODO Auto-generated method stub
		return (BufferedImage) (anim.getPNG());
	}

}
