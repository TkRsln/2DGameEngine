package game.Component.Test;

import java.awt.Color;
import java.util.Random;

import game.Component.Component;
import game.Component.GameObject;
import game.Component.OvalSprite;
import game.Requirments.Vector;

public class ParticilarCreater extends Component {

	public ParticilarCreater(GameObject obj) {
		super(obj);
		// TODO Auto-generated constructor stub
	}
	
	public ParticilarCreater(GameObject obj,int delay,int smokeSize,int duration,Color begin,Color end) {
		super(obj);
		this.smoke_size=smokeSize;
		this.duration=duration;
		this.delay=delay;
		this.begin=begin;
		this.end=end;
		// TODO Auto-generated constructor stub
	}
	

	private int duration=500;
	private int smoke_size=18;
	private int delay=25;
	private int total=0;
	private Color begin=Color.CYAN;
	private Color end=Color.MAGENTA;
	Random rand=new Random();
	
	
	@Override
	protected void onLoop() { 
		total+=object.dt;
		if(total>=delay) {
			total=0;
			createPartical();
		}
	}
	private void createPartical() {
		if(transform == null)return;
		GameObject go = new GameObject(smoke_size,smoke_size);
		go.addComponent(new OvalSprite(go,true));
		go.addComponent(new ParticilarTest(go,smoke_size,begin,end));
		Vector v=transform.getWorldPositionStart();
		go.transform.setPosition(rand.nextInt(10)+v.x,rand.nextInt(10)+v.y);
		
		go.Destroy(duration);
		go.wakeUp();
	}
	
	@Override
	protected boolean isLoopRequired() {
		// TODO Auto-generated method stub
		return true;
	}

}
