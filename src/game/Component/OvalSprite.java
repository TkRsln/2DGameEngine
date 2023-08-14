package game.Component;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class OvalSprite extends Component {

	private BufferedImage img;
	
	//public int size=100;
	public Color color=Color.ORANGE;
	public boolean fill=false;
	/*
	public OvalSprite(GameObject obj) {
		super(obj);
	}*/
	public OvalSprite(GameObject obj) {
		super(obj);
	}
	public OvalSprite(GameObject obj,boolean fl) {
		super(obj);
		this.fill=fl;
	}
	public OvalSprite(GameObject obj,Color clr,boolean fl) {
		super(obj);
		//this.size=size;
		this.fill=fl;
		color=clr;
	}
	
	@Override
	protected void onStart() {
		object.transform.size.x=transform.size.x;
		object.transform.size.y=transform.size.x;
	}
	
	@Override
	protected BufferedImage onRender() {
		if(transform.size.x<=1||transform.size.y<=1)return null;
		img=new BufferedImage((int)transform.size.x, (int)transform.size.y, BufferedImage.TYPE_INT_ARGB);
		Graphics g = img.createGraphics();
		g.setColor(color);
		if(!fill)g.drawOval(0, 0, img.getWidth(), img.getHeight());
		else g.fillOval(0, 0, img.getWidth(), img.getHeight());
		return img;
	}

}
