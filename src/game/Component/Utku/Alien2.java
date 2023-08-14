package game.Component.Utku;

import game.Component.GameObject;
import game.Component.HitBox;
import game.Component.RigidBody;
import game.Component.Utku.Alien.Alien1AI;

public class Alien2  extends GameObject{
	public Alien2(double posx,double posy) {
		super(75*2,75*2);
		
		addComponent(new HitBox(this));
		RigidBody r=new RigidBody(this);
		r.friction_ground=15;
		r.friction_air_x=0.3f;
		r.gravitiy=16;
		r.bounce=0.2f;
		addComponent(r);
		addComponent(new WalkAnimationControl(this, "BossRun", "BossIdle","BossJump",100,0.5f));
		addComponent(new Alien1AI(this,transform));
		wakeUp();
	}
}
