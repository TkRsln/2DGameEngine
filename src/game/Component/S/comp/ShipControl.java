package game.Component.S.comp;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import game.Main;
import game.Component.Component;
import game.Component.GameObject;
import game.Component.RigidBody;
import game.Component.Transform;
import game.Component.Test.BulletTest;
import game.Component.Utku.Player1Bullet;
import game.Engine.TAGS;
import game.Menu.MainMenu;

public class ShipControl extends Component implements KeyListener{

	RigidBody rb;
	Random rand = new Random();
	char[] keys;
	public Transform gunPoint;
	
	public ShipControl(GameObject gO,char[] keys, Transform gunPoint) {
		super(gO);
		this.keys=keys;
		this.gunPoint=gunPoint;
	}

	@Override
	protected boolean isLoopRequired() {
		return true;
	}
	
	@Override
	protected void onLoop() {
		//object.dt bir önceeki döngü zamaný
		
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
		if(e.getKeyChar()==keys[3]) {
			if(rb.speed.x>-10) {
				rb.acceleration.x = -10;
			}
			transform.scale.x = -1;
//			rb.acceleration.x = 5;
//			transform.addPosition(-5, 0);
		}
		if(e.getKeyChar()==keys[0]) {
			System.out.println("hello");
			if(rb.speed.y<10) {
				rb.acceleration.y = 10;
			}
		}
		if(e.getKeyChar()==keys[2]) {
			if(rb.speed.x<10) {
				rb.acceleration.x = 10;
			}
			transform.scale.x = 1;
//			transform.addPosition(5, 0);
		}	
		if(e.getKeyChar()==keys[1]) {
			
			if(rb.speed.y>-10) {
				rb.acceleration.y = -10;
			}
		}if(e.getKeyChar()==keys[4] && object.getEnable()) {
            //gun.transform.scale.x=1;
            new Player1Bullet(gunPoint.getWorldMiddlePosition(),30*object.transform.getScaleX(), -5+rand.nextInt(10));
		}
	}
		
	

	@Override
	public void keyPressed(KeyEvent e) {
		
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		if(e.getKeyChar()==keys[3]) {
//			rb.addVelocity(0, 0);
//			rb.speed.x = 0;
			rb.acceleration.x = 0;
//			transform.addPosition(-5, 0);
		}
		if(e.getKeyChar()==keys[2]) {
//			rb.addVelocity(0, 0);
//			rb.speed.x = 0;
			rb.acceleration.x = 0;
			
//			transform.addPosition(-5, 0);
		}
		if(e.getKeyChar()==keys[1]) {
//			rb.addVelocity(0, 0);
//			rb.speed.x = 0;
			rb.acceleration.y = 0;
//			transform.addPosition(-5, 0);
		}
		if(e.getKeyChar()==keys[0]) {
//			rb.addVelocity(0, 0);
//			rb.speed.x = 0;
			rb.acceleration.y = 0;
//			transform.addPosition(-5, 0);
		}
		
	}
	
	
	@Override
	protected void onStart() {
		rb = (RigidBody) object.getComponent(RigidBody.class);
	}
	
	@Override
	protected void onDestroyComponent() {
		Main.app.removeKeyListener(this);
	}
}
