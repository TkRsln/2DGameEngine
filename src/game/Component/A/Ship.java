package game.Component.A;

import java.awt.Color;

import game.Main;
import game.Component.DynamicCollider;
import game.Component.GameObject;
import game.Component.HitBox;
import game.Component.RigidBody;
import game.Component.Test.DecorSprite;
import game.Component.Test.ParticilarCreater;
import game.Component.Utku.Player1;
import game.Engine.Camera;
import game.Engine.TAGS;
import game.Requirments.Collider;
import game.Requirments.Vector;

public class Ship extends GameObject {
	
	private Camera cam;
	private RigidBody rb;
	boolean firstTime = true;
	private Player_Scene3 p1;
	private Player_Scene3 p2;
	
	public Ship(double posx,double posy, Player_Scene3 p1, Player_Scene3 p2) {
		
		transform.size.x=170*2;
		transform.size.y=250*2;
		transform.setPositionEnd(new Vector(posx, posy));
		this.p1 = p1;
		this.p2 = p2;
		
		addComponent(new HitBox(this));
		rb=new RigidBody(this);
		rb.bounce=0;
		rb.acceleration.y = 5;
		addComponent(rb);
		addComponent(new DecorSprite(this, "img/ship.png"));
		
		wakeUp();
		
		enableJets();
		
		Main.cam1.follow_speed_y = 1;
		Main.cam1.follow_distance_y = -170;
		
	}
	
	@Override
	public void onConstantCollision(Collider col) {
		
		if(firstTime && col.isContains(TAGS.none)) {
			p1.setEnable(true);
			Main.cam1.setFollow(p1.transform);
			p1.transform.setPosition(this.transform.position.x+100,this.transform.getMidY());
			firstTime = false;
			Main.cam1.follow_distance_y=100;
			Main.cam1.follow_speed_y = 0.01;
			Destroy(RigidBody.class);
			if (Main.nop==2) {
			  p2.setEnable(true);
			  Main.cam2.setFollow(p2.transform);
				p2.transform.setPosition(this.transform.position.x+100,this.transform.getMidY());
				Main.cam2.follow_distance_y=100;
				Main.cam2.follow_speed_y = 0.01;
				
			}
			

		}
	}
	
	private void enableJets() {
		GameObject jet1=new GameObject(50,50);
		jet1.transform.setParent(this.transform);
		//jet1.addComponent(new OvalSprite(jet1));
		jet1.transform.setPosition(28, 460);
		jet1.addComponent(new ParticilarCreater(jet1,10,30,600,Color.RED,Color.YELLOW));
		jet1.wakeUp();
		jet1.Destroy(ParticilarCreater.class,4000);

		jet1=new GameObject(50,50);
		jet1.transform.setParent(this.transform);
		//jet1.addComponent(new OvalSprite(jet1));
		jet1.transform.setPosition(-57, 460);
		jet1.addComponent(new ParticilarCreater(jet1,10,30,600,Color.RED,Color.YELLOW));
		jet1.wakeUp();
		jet1.Destroy(ParticilarCreater.class,4000);
	}
}
