package game.Component.S;

import java.awt.Color;

import game.Main;
import game.Component.DynamicCollider;
import game.Component.GameObject;
import game.Component.HitBox;
import game.Component.OvalSprite;
import game.Component.RigidBody;
import game.Component.Test.DecorSprite;
import game.Component.Test.ParticilarCreater;
import game.Component.Utku.SingleAnimation;
import game.Engine.Camera;
import game.Engine.TAGS;
import game.Requirments.Collider;
import game.Requirments.Vector;

public class LastSeen extends GameObject{

	public GameObject guide;
	private Camera cam;
	private RigidBody rb;
	private boolean firstHit=true;
	
	public LastSeen(Camera cam, int posx, int posy) {
		
		
		tag=TAGS.black_hole;
		
		rb = new RigidBody(this);
		addComponent(new HitBox(this));
		
		rb.gravitiy = 0;
		
		
		transform.size.x=350*2;
		transform.size.y=350*2;
		transform.setPositionEnd(new Vector(posx, posy));

		
		addComponent(rb);
		addComponent(new SingleAnimation(this, "BlackHole", 50, true));
		addComponent(new BlackHoleComp(this));
			
		wakeUp();
		
		
		
		this.cam=cam;
	}
	
	@Override
	public void onDynamicCollision(DynamicCollider col) {
		if(col.isContains(TAGS.player)&&firstHit) {
			firstHit=false;
			Main.nextScene2();
			Main.cam1.setFollow(null);
			if(Main.nop==2)
				Main.cam2.setFollow(null);
			col.getGameObjectByTag(TAGS.player).setEnable(false);
		}
	}
	
}


