package game.Component.Test;

import game.Component.GameObject;
import game.Component.HitBox;
import game.Component.RigidBody;

public class GunObject extends GameObject{
	public GunObject() {

		super(50,20);
		//GameObject tree = new GameObject(25,30);
		addComponent(new DecorSprite(this,"img/Weapon_11.png"));
		
		name="Gun";
		
		/*
		GameObject go = new GameObject(20,20);
		go.transform.setParent(transform);
		go.addComponent(new HitBox(go, true));
		go.wakeUp();
		*/
		//addComponent(new OvalSprite(player,Color.ORANGE,true));
		//RigidTesst r= new RigidTesst(player);
		//transform.setPosition(x, y);
		//wakeUp();
	}
	
}
