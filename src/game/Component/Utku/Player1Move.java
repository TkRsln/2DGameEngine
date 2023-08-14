package game.Component.Utku;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import game.Main;
import game.Component.Component;
import game.Component.GameObject;
import game.Component.RigidBody;
import game.Component.Test.BulletTest;
import game.Component.Test.GranadeTest;
import game.Engine.Camera;
import game.Engine.GameEngine;
import game.Engine.TAGS;
import game.Requirments.Vector;

public class Player1Move  extends Component implements KeyListener{

	
	private RigidBody rb;
	private double speedY=0;
	private GameObject gun;
	private Random rand=new Random();
	
	private int fire_cooldown=200;
	private int fire_total_time=0;
	
	private int speed=13;
	
	private boolean fire=false;
	private boolean runX=false;
	private boolean runY=false;
	
	private char[] keys;
	
	private Vector gunHalfSize;
	
	public Player1Move(GameObject obj,GameObject gun,char[] keys) {
		super(obj);
		this.gun=gun;
		this.keys=keys;
		//gunHalfSize=new Vector(Main.target.transform.size.x/2,Main.target.transform.size.y/2);
	}
	
	private void startFire() {
		if(fire_cooldown<fire_total_time) {
			new Player1Bullet(((Player1)object).gun.transform.getWorldPositionEnd(), 30*object.transform.getScaleX(), -5+rand.nextInt(10));
			fire_total_time=0;
			//rb.addVelocity(-object.transform.scale.x*4, 0);
		}
	}
	
	@Override
	protected void onStart() {
		rb=(RigidBody) object.getComponent(RigidBody.class);
		//gun.transform.position.x=-15;
	}
	
	@Override
	protected boolean isLoopRequired() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	protected void onLoop() {
		fire_total_time+=object.dt;
		if(runX && Math.abs(rb.speed.x)<4.5)
			//rb.addVelocity(object.transform.scale.x*5,0);
			rb.addVelocity(object.transform.scale.x*(speed-Math.abs(rb.speed.x))*object.dt/100.0,0);
		if(runY && rb.onGround&& Math.abs(rb.speed.y)<5)
			rb.addVelocity(0, 5);
		if(fire) {
			startFire();
		}
		//Main.target.transform.setPosition((gun.transform.getWorldMiddlePosition().subVector(gunHalfSize)));
		//System.out.println(GameEngine.objectScaleX(gun.transform) + " / "+GameEngine.objectStartPosition(gun.transform)+"-"+GameEngine.objectStartPosition(transform));
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

		//System.out.println("HELLO");
		if(e.getKeyChar()==keys[0] && rb.onGround && rb.speed.y < 1) {
			runY=true;
		}
		if(e.getKeyChar()==keys[2]) {
			object.transform.scale.x=1;
			gun.transform.scale.x=1;
			runX=true;
		} if(e.getKeyChar()==keys[3]) {
			//player.transform.addPosition(-15, 0);
			object.transform.scale.x=-1;
			gun.transform.scale.x=-1;
			runX=true;
		}if(e.getKeyChar()==keys[4]) {
			fire=true;
			//gun.transform.scale.x=1;
			//new BulletTest(gun.transform.getWorldPositionEnd(),30*object.transform.getScaleX(), -5+rand.nextInt(10),TAGS.player_bullet,Color.RED);
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {

		System.out.println("HELLO");
		if(e.getKeyChar()==keys[2]||e.getKeyChar()==keys[3]) 
			runX=false;
		if(e.getKeyChar()==keys[0]) 
			runY=false;
		if(e.getKeyChar()==keys[4]) 
			fire=false;
		if(e.getKeyChar()=='r') 
			Camera.active.startShake(1500);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		
		
	}
	
}
