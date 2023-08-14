package game.Component.S;

import game.Component.Component;
import game.Component.GameObject;
import game.Component.Utku.SingleAnimation;

public class BlackHoleComp extends Component{
	
	float x,y;
	double totalTime = -4000;
	
	public BlackHoleComp(GameObject Go) {
		super(Go);
		
	}
	
	@Override
	protected boolean isLoopRequired() {
		return true;
	}
	
	@Override
	protected void onLoop() {
		
		totalTime += object.dt;
		if(totalTime == 4000) {
			totalTime = -4000;
		}
		if(PlayerShip.active_player==null)return;
		double distance = PlayerShip.active_player.transform.getDistance(this.transform);
		if(distance<1200) {
			PlayerShip.active_player.rb.addVelocity((transform.getMidX()-PlayerShip.active_player.transform.getMidX())*0.0003, -(transform.getMidY()-PlayerShip.active_player.transform.getMidY())*0.0003);
		}

	}
}
