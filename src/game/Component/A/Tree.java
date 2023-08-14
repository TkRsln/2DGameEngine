package game.Component.A;

import game.Component.GameObject;
import game.Component.Test.DecorSprite;
import game.Component.Utku.SingleAnimation;

public class Tree extends GameObject{
	  public Tree(int posX, int posY) {
	    	transform.size.x =200;
	    	transform.size.y =200;
	    	transform.setPosition(posX,posY);
	    	//addComponent(new SingleAnimation(this,"Evil Grass2_" , 300,true));
	    	addComponent(new SingleAnimation(this,"Winter Grass1_", 450, true));
	    	
	    	wakeUp();
	    	
	    }
}
