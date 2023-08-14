package game.Component.A;

import javax.swing.tree.TreeCellRenderer;

import game.Component.GameObject;
import game.Component.Test.DecorSprite;
import game.Component.Utku.SingleAnimation;

public class grass extends GameObject {
    public grass(int posX, int posY) {
    	transform.size.x =120;
    	transform.size.y =120;
    	transform.setPosition(posX,posY);
    	//addComponent(new SingleAnimation(this,"Evil Grass2_" , 300,true));
    	addComponent(new DecorSprite(this,"img/Animations/Evil Grass2_1.png" ));
    	
    	wakeUp();
    	
    }
}
