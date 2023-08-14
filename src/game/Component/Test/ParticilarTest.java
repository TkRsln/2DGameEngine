package game.Component.Test;

import java.awt.Color;

import game.Component.Component;
import game.Component.GameObject;
import game.Component.OvalSprite;

public class ParticilarTest extends Component{

	public ParticilarTest(GameObject obj,Color clr,int size) {
		super(obj);
		this.clr_start=clr;
		this.size=size;
	}
	public ParticilarTest(GameObject obj,Color clr) {
		super(obj);
		this.clr_start=clr;
	}
	public ParticilarTest(GameObject obj,int size,Color start,Color end) {
		super(obj);
		this.clr_start=start;
		this.clr_end=end;
		changeColor=true;
		this.size=size;
	}
	public ParticilarTest(GameObject obj) {
		super(obj);
	}
	
	private OvalSprite os;
	private Color clr_end=Color.RED;
	private Color clr_start=Color.RED;
	private float size=15;
	private int total_time=10;
	private int time_max=500;
	private boolean changeColor=false;
	
	@Override
	protected void onStart() {
		os=(OvalSprite) object.getComponent(OvalSprite.class);
		os.color=clr_start;
	}
	
	@Override
	protected void onLoop() {
		total_time+=object.dt;
		if(os!=null) {
			float ratio=(1-(total_time*1.0f)/time_max);
			float x=(size*ratio);
			object.transform.size.x=(int)x;
			object.transform.size.y=(int)x;
			//System.out.println("On Start "+x);
			if(changeColor) {
				os.color=gradient(ratio);
			}
		}
	}
	// 1-0 arasýnda deðer alýr 0End/1Start
	private Color gradient(float ratio) {
		float r=(clr_end.getRed()+(clr_start.getRed()-clr_end.getRed())*ratio)/255.0f;
		float g=(clr_end.getGreen()+(clr_start.getGreen()-clr_end.getGreen())*ratio)/255.0f;
		float b=(clr_end.getBlue()+(clr_start.getBlue()-clr_end.getBlue())*ratio)/255.0f;
		
		try {
		return new Color(
				limit(r),
				limit(g),
				limit(b)
				);
		}catch (Exception e) {
			System.err.println(r+" "+g+" "+b);
			return Color.PINK;
		}
	}
	private float limit(float val) {
		return val=val>=1?1:(val<=0?0:val);
	}
	
	@Override
	protected boolean isLoopRequired() {
		// TODO Auto-generated method stub
		return true;
	}

}
