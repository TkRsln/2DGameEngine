package game.Component.Utku.Boss;

import java.awt.Color;

import game.Main;
import game.Component.DynamicCollider;
import game.Component.GameObject;
import game.Component.HitBox;
import game.Component.OvalSprite;
import game.Component.RectSprite;
import game.Component.RigidBody;
import game.Component.Test.DecorSprite;
import game.Component.Utku.PlatformObject;
import game.Component.Utku.Player1;
import game.Engine.TAGS;
import game.Requirments.Vector;

public class Boss1 extends GameObject{
	
	RigidBody rb =new RigidBody(this);
	public GameObject bullet_point;
	
	public static Boss1 active;
	
	public GameObject platform_back;
	public GameObject platform_front;

	private double health=10;
	public GameObject healthBar;
	public RectSprite rs;
	
	public Boss1(double positionX,double positionY) {
		
		active=this;
		
		transform.setPositionEnd(new Vector(positionX, positionY));
		transform.size.x=355*0.7f;
		transform.size.y=315*0.7f;
		
		rb.bounce=0;
		rb.friction_ground=1.5;
		
		tag = TAGS.alien;
		
		addComponent(new HitBox(this));
		addComponent(rb);
		addComponent(new Boss1AI(this));
		addComponent(new Boss1Animations(this));
		//addComponent(new  DecorSprite(this,"img/Animations/BossRun1.png"));
		
		bullet_point=new GameObject(15,15);
		bullet_point.transform.setParent(transform);
		
		bullet_point.transform.setPosition(-20, 70);
		bullet_point.addComponent(new OvalSprite(bullet_point));
		
		wakeUp();
		

		healthBar = new GameObject(80,5);
		rs = new RectSprite(healthBar);
		rs.color=Color.magenta.darker();
		healthBar.addComponent(rs);
		healthBar.transform.setParent(transform);
		healthBar.transform.setPosition(-15, -10);
		
	}
	
	public void createBlockPlatform() {
		platform_back=new PlatformObject(3000, 300, 300, 500,"img/Platform/top.png" ,"img/Platform/bot.png");
	}
	public void removeBlockPlatform() {
		platform_back.Destroy();
		platform_front.Destroy();
	}
	
	@Override
	public void onDynamicCollision(DynamicCollider col) {
		
		if(col.isContains(TAGS.player_bullet)) {
			health-=0.35;
			healthBar.transform.size.x=health*8;
			if(health<=0)
				Destroy();
		}
		
	}
	
	
	
	@Override
	protected void onDestroy() {
		
		if(Player1.active_player==null)return;
		Boss1.active=null;
		Boss1Death b=new Boss1Death(transform.position);
		b.transform.scale.x=transform.scale.x;
		removeBlockPlatform();
		Main.addScore(7);
	}
	
	
}
