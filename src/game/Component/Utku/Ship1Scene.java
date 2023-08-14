package game.Component.Utku;

import java.awt.Color;

import game.Main;
import game.Component.DynamicCollider;
import game.Component.GameObject;
import game.Component.HitBox;
import game.Component.OvalSprite;
import game.Component.RigidBody;
import game.Component.Test.DecorSprite;
import game.Component.Test.ParticilarCreater;
import game.Engine.Camera;
import game.Engine.TAGS;
import game.Requirments.Collider;
import game.Requirments.Vector;

public class Ship1Scene extends GameObject {
	
	private Camera cam;
	private RigidBody rb;
	private boolean first=true;
	private Player1 p1;
	private Player1 p2;
	
	public Ship1Scene(Camera cam,double posx,double posy,Player1 p1,Player1 p2) {
		this.p1=p1;
		this.p2=p2;
		transform.size.x=170*2;
		transform.size.y=250*2;
		transform.setPositionEnd(new Vector(posx, posy));
		
		addComponent(new HitBox(this));
		rb=new RigidBody(this);
		rb.bounce=0;
		addComponent(rb);
		addComponent(new DecorSprite(this, "img/ship.png"));
		
		wakeUp();
		
		//enableJets();
		
		this.cam=cam;
		
	}
	
	@Override
	public void onDynamicCollision(DynamicCollider col) {
		// TODO Auto-generated method stub
		if(first&&col.isContains(TAGS.player)) {
			first=false;
			p1.setEnable(false);
			if(Main.nop==2)
				p2.setEnable(false);
			cam.setFollow(null);
			cam.follow_distance_y=-100;
			rb.acceleration.y=13;
			enableJets();
			Main.nextScene();
		}
	}
	
	private void enableJets() {
		GameObject jet1=new GameObject(50,50);
		jet1.transform.setParent(this.transform);
		//jet1.addComponent(new OvalSprite(jet1));
		jet1.transform.setPosition(28, 460);
		jet1.addComponent(new ParticilarCreater(jet1,10,30,600,Color.RED,Color.YELLOW));
		jet1.wakeUp();
		

		jet1=new GameObject(50,50);
		jet1.transform.setParent(this.transform);
		//jet1.addComponent(new OvalSprite(jet1));
		jet1.transform.setPosition(-57, 460);
		jet1.addComponent(new ParticilarCreater(jet1,10,30,600,Color.RED,Color.YELLOW));
		jet1.wakeUp();
	}
}
