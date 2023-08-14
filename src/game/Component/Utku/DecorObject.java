package game.Component.Utku;

import java.util.Random;

import game.Component.GameObject;
import game.Component.HitBox;
import game.Component.OvalSprite;
import game.Component.RigidBody;
import game.Component.Test.DecorSprite;
import game.Engine.TAGS;
import game.Requirments.Vector;

public class DecorObject extends GameObject{
	
	
	
	
	public DecorObject(double posx,double posy) {
		//super(100,100);
		
		String[] path = new String[] {
			"img/Enviroment/Bush.png",	
			"img/Enviroment/Bush_2.png",	
			"img/Enviroment/Tree_1.png",
			"img/Enviroment/Tree_2.png"	
		};
		int[][] size = new int[][] {
			{100,60},{120,120},{250,250},{220,275}
		};
		
		int selected=new Random().nextInt(path.length);
		
		//GameObject tree = new GameObject(25,30);
		name="Decor";
		//tag=TAGS.alien;
		//addComponent(new HitBox(this));
		//RigidBody rb =new RigidBody(this);
		//addComponent(rb);
		//rb.friction_ground=15;
		transform.size.x=size[selected][0];
		transform.size.y=size[selected][1];
		
		transform.setPositionEnd(new Vector(posx,posy));
		//addComponent(new OvalSprite(this));
		addComponent(new DecorSprite(this,path[selected]));
		//addComponent(new EnemyAITest(this));
		
		wakeUp();
		//transform.setPosition(x,y);
		
		
		
		
	}
	
	
}
