package game.Component.A;

import java.util.Random;

import game.Main;
import game.Component.DynamicCollider;
import game.Component.GameObject;
import game.Component.HitBox;
import game.Component.Test.DecorSprite;
import game.Component.Utku.SingleAnimation;
import game.Engine.TAGS;
import game.Requirments.Collider;

public class Carnivorous extends GameObject {
	
	double carnivorous_health = 10;
	
	public Carnivorous(int posX, int posY) {
		transform.size.x =200;
    	transform.size.y =200;
    	transform.setPosition(posX,posY);
    	transform.scale.x = -1;
    	tag = TAGS.alien;
    	
    	HitBox hbCrn = new HitBox(this);
    	addComponent(hbCrn);
    	//addComponent(new SingleAnimation(this,"Evil Grass2_" , 300,true));
    	addComponent(new SingleAnimation(this,"Carnivorous Plant Attacking", 400, true));
    	
    	wakeUp();
    	
    }
		@Override
		public void onConstantCollision(Collider col) {
			if (col.isContains(TAGS.player_bullet)) {
				carnivorous_health -= 0.7;
				if (carnivorous_health<0) {
					Random rand = new Random();
					
					if(rand.nextInt(100)<55) {
						Health_Scene3 h3 = new Health_Scene3(transform);					
					}
					Main.addScore(2);
					Destroy();
					
				}
			}
		}

	}
	
	

