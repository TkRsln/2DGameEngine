package game.Component;

public class ConstantSpeed extends Component{

	public ConstantSpeed(GameObject obj,float speedX,float speedY) {
		super(obj);
		sx=speedX;
		sy=speedY;
		// TODO Auto-generated constructor stub
	}
	float sx=0;
	float sy=0;
	@Override
	protected boolean isLoopRequired() {
		return true;
	}
	@Override
	protected void onLoop() {
		object.transform.addPosition(object.dt*sx, object.dt*sy);
	}
}
