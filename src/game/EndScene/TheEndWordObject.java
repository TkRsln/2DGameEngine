package game.EndScene;

import game.Main;
import game.Component.GameObject;
import game.Component.Test.DecorSprite;

public class TheEndWordObject extends GameObject{
	
	public TheEndWordObject() {
		
		double ratio=Main.app.getWidth()/700;
		
		transform.size.x=ratio*500;
		transform.size.y=ratio*110;
		
		addComponent(new DecorSprite(this, "img/TheEnd.png"));
		transform.position.x= Main.cam1.getMidX()-(transform.size.x/2);
		transform.position.y= Main.cam1.getMidY()-(transform.size.y/2);
	}
}
