package game.Component.Test;

import java.awt.Color;
import java.util.Random;

import game.Component.DynamicCollider;
import game.Component.GameObject;
import game.Component.HitBox;
import game.Component.OvalSprite;
import game.Component.RigidBody;
import game.Component.Transform;
import game.Requirments.Collider;

public class GranadeTest extends GameObject{
	
	public GranadeTest(GameObject player,float velocityX,float velocityY) {
		super(25,25);
		//GameObject go = new GameObject();
		OvalSprite vl = new OvalSprite(this,true);
		name="Granade";
		//Color[] clr = new Color[] {Color.RED,Color.ORANGE,Color.GREEN,Color.PINK,Color.CYAN,Color.WHITE};
		//vl.size=25;
		vl.color=Color.WHITE;//clr[new Random().nextInt(clr.length)];
		addComponent(new ParticilarCreater(this));
		addComponent(new HitBox(this));
		RigidBody r=new RigidBody(this);
		r.gravitiy=9;
		r.bounce=0.3f;//new Random().nextFloat();//0.1f;
		addComponent(r);
		addComponent(vl);
		transform.setPosition(player.transform.getEndX(),player.transform.getStartY());
		wakeUp();
		r.addVelocity(velocityX,velocityY);
		//r.bounce=0.5f;
		Destroy(new Random().nextInt(5)*500+5000);
		GranadeTrigger gt=new GranadeTrigger(transform);
		gt.transform.setParent(transform);
	}
	
	
}
class GranadeTrigger extends GameObject {
	public GranadeTrigger(Transform parent) {
		super(100,100);
		//GameObject go = new GameObject(70,70);
		name="GP";
		transform.position=transform.size.clone().divideVector(-2);
		HitBox h =new HitBox(this,true);
		h.isTrigger=true;
		addComponent(h);
		wakeUp();
		//transform.setParent(parent);
	}
	
	
	
	@Override
	public void onDynamicCollision(DynamicCollider col) {
		// TODO Auto-generated method stub
		//System.out.println(col);
		super.onDynamicCollision(col);
	}
	
	
	
	
	@Override
	public void onTriggerCollision(GameObject obj) {
		// TODO Auto-generated method stub
		if(obj.name!="Player")
			System.out.println("T: "+obj.name);
	}
}