package game.EndScene;

import java.awt.Color;

import game.Component.Component;
import game.Component.GameObject;
import game.Component.RectSprite;

public class TheEndAnim extends Component{

	public TheEndAnim(GameObject obj,RectSprite rs) {
		super(obj);
		this.rs=rs;
	}
	
	private RectSprite rs;
	private int duration=2000;
	private int total_time=0;
	
	@Override
	protected boolean isLoopRequired() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	protected void onLoop() {
		if(total_time<duration) {
			total_time+=object.dt;
			float ratio=total_time*1.0f/duration;// total time 4000 olunca, oran 1 gelir;
			ratio=ratio>1?1:ratio;
			rs.color=new Color(0, 0, 0, ratio);
		}
	}

}
