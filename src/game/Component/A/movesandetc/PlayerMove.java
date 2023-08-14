package game.Component.A.movesandetc;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import game.Component.Component;
import game.Component.GameObject;
import game.Component.RigidBody;
import game.Component.Test.BulletTest;
import game.Component.Test.GranadeTest;
import game.Component.Test.GunObject;
import game.Component.Utku.Player1Bullet;
import game.Engine.TAGS;

public class PlayerMove extends Component implements KeyListener {
	
	RigidBody rbPm;
	GunObject g3;
	public static Random rand = new Random();
	long lastShoot = 0;
	int shootDelay = 100;
	private char[] keys;
	
	public PlayerMove(GameObject pm, GameObject go, RigidBody rbp,char[] keys) {
		super(pm);
		g3 = (GunObject) go;
		rbPm = rbp;	
		this.keys = keys;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyChar()==keys[2]) {
			object.transform.scale.x=1;
			g3.transform.scale.x=1;
			rbPm.speed.x=10;
		} if(e.getKeyChar()==keys[3]) {

			object.transform.scale.x=-1;
			g3.transform.scale.x=-1;
			rbPm.speed.x=-10;
		}
		if(e.getKeyChar()==keys[4] && System.currentTimeMillis()-lastShoot>=100) {
			//gun.transform.scale.x=1;
			lastShoot = System.currentTimeMillis();
			new Player1Bullet(g3.transform.getWorldPositionEnd(),30*object.transform.getScaleX(), -5+rand.nextInt(10));
		}if(e.getKeyChar()==keys[0] && rbPm.onGround && rbPm.speed.y < 5) {
			rbPm.speed.y=5;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

}
