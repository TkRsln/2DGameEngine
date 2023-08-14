package game.Menu;

import java.awt.Color;
import java.util.Random;

import game.Main;
import game.Component.GameObject;
import game.Component.HitBox;
import game.Component.OvalSprite;
import game.Component.RigidBody;
import game.Component.Test.DecorSprite;
import game.Component.Test.ParticilarCreater;
import game.Engine.Camera;

public class MenuShip extends GameObject {
	
	public static boolean respawn=true;

	private RigidBody rb = new RigidBody(this);
	public MenuShip() {
		super(450,450);
		
		addComponent(new DecorSprite(this, "img/Ship1.png"));
		
		rb.gravitiy=0;
		rb.friction_air_x=0;
		rb.friction_air_y=0;
		
		addComponent(rb);
		addComponent(new HitBox(this));
		wakeUp();
		decideSpawn();
		Destroy(4500+new Random().nextInt(4)*1000);
		
		GameObject duman = new GameObject(25,25);
		duman.transform.setParent(transform);
		duman.transform.setPosition(-125, 220);
		//duman.addComponent(new OvalSprite(duman));
		duman.addComponent(new ParticilarCreater(duman,10,35,500,Color.GREEN,Color.YELLOW));
		duman.wakeUp();
		
		
	}
	private void decideSpawn() {	
		boolean isLeft=new Random().nextBoolean();
		if(isLeft) {
			transform.setPosition(Main.app.getWidth()+450, Main.cam1.getWorldY()-(Main.cam1.HEIGHT/2.0)-125);
			rb.speed.x=-20;
			transform.scale.x=-1;
		}else {
			transform.setPosition(-450,Main.cam1.getWorldY()-(Main.cam1.HEIGHT/2.0)-125);
			rb.speed.x=20;
		}
		
	}
	private void goLeft() {
		rb.acceleration.x=-10;
		transform.scale.x=-1;
	}
	private void goRight() {
		rb.acceleration.x=-10;
		transform.scale.x=-1;
	}
	
	@Override
	protected void onDestroy() {
		if(!respawn)return;
		new MenuShip();
	}
	
}
