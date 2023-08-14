package game.Component.A;

import game.Main;
import game.Component.DynamicCollider;
import game.Component.GameObject;
import game.Component.HitBox;
import game.Component.RigidBody;
import game.Component.Utku.SingleAnimation;
import game.Engine.TAGS;
import game.Requirments.Collider;

public class doorScene3 extends GameObject {
	
	public static doorScene3 active;
	private SingleAnimation doorAnimation;
	private boolean isOpen = false;
	private boolean firstHit=true;
	
	public doorScene3(int posX,int posY) {
		transform.size.x = 150;
		transform.size.y = 150;
		transform.setPosition(posX,posY);
		
		HitBox hbDoor = new HitBox(this);
		addComponent(hbDoor);
		addComponent(new RigidBody(this));
		active = this;
		doorAnimation = new SingleAnimation(this, "Cyberdoor Open", 100, false, false);
		addComponent(doorAnimation);
		wakeUp();
		
		
	}
	
	public void openDoor() {
		isOpen=true;
		doorAnimation.playAnimation();
	}
	@Override
	public void onDynamicCollision(DynamicCollider col) {
		if(isOpen&&col.isContains(TAGS.player)&&firstHit) {
			firstHit=false;
			Main.endScene();
			System.out.println("THE ENDDD");
		}
	}
	
	
	
	
}
