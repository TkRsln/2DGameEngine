package game.Component.S;

import java.util.Random;

import game.Component.GameObject;
import game.Component.HitBox;
import game.Component.RigidBody;
import game.Component.Test.DecorSprite;

public class satellite extends GameObject{
	
	public Random rand = new Random();
	RigidBody rb;
	
	public satellite(int posx, int posy) {
		addComponent(new HitBox(this));
		rb = new RigidBody(this);
		addComponent(new HitBox(this));
		addComponent(rb);
		rb.gravitiy = 0;
		
		transform.size.x=100;
		transform.size.y=100;
		transform.setPosition(posx, posy);
		addComponent(new DecorSprite(this, "img/uydu2.png"));
		
		wakeUp();
	}
}
