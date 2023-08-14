package game.Component.A;

public class Boss_Bullet extends Bullet{

	public Boss_Bullet(double posX, double posY, double vecX, double vecY) {
		//point.getWorldMiddlePosition().x, point.getWorldMiddlePosition().y,60, 60,0.02*(Player_Scene3.active_pl.transform.position.x-object.transform.position.x0.02*(Player_Scene3.active_pl.transform.position.y-object.transform.position.y), "img/Rock.png"
		super(posX, posY, 60, 60, vecX, vecY, "img/Rock.png");
		// TODO Auto-generated constructor stub
		
	}

	@Override
	protected void onDestroy() {
		if (this.transform.position!=null) {
			new BossBulletImpact((int)transform.position.x,(int)transform.position.y);
		}
		
	}
	
	
	
	
}
