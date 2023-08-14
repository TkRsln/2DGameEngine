package game.EndScene;

import game.Main;
import game.Component.GameObject;
import game.Component.RectSprite;

public class TheEndBackground extends GameObject{
	public TheEndBackground() {
		transform.size.x=Main.app.getWidth();
		transform.size.y=Main.app.getHeight();
		transform.position.x=Main.cam1.getWorldX();
		transform.position.y=Main.cam1.getWorldY();
		
		RectSprite rs = new RectSprite(this);
		addComponent(rs);
		addComponent(new TheEndAnim(this, rs));
		wakeUp();
	}
}
