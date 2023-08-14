package game.Component.Utku;

import game.Component.GameObject;
import game.Component.HitBox;

public class PlatformObject extends GameObject{

    public PlatformObject(double posx,double posy,String top, String bot) {

        super(800,200);
        transform.setPosition(posx, posy);
        addComponent(new TextureComponent(this, top, bot));
        addComponent(new HitBox(this));
        wakeUp();

    }public PlatformObject(double posx,double posy,double width,double height,String top, String bot) {

        super(width,height);
        transform.setPosition(posx, posy);
        addComponent(new TextureComponent(this, top, bot));
        addComponent(new HitBox(this));
        wakeUp();

    }

}
