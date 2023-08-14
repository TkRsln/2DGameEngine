package game.Component;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class RectSprite extends Component {

	private BufferedImage img;
	
	//public int size=5;
	public Color color=Color.YELLOW;
	
	public RectSprite(GameObject obj) {
		super(obj);
	}
	/*
	public RectSprite(GameObject obj,int size) {
		super(obj);
		//this.size=size;
	}*/
	
	@Override
	protected void onStart() {
		//object.transform.size.x=object.transform.size.x;
		//object.transform.size.y=object.transform.size.y;
	}
	
	@Override
	protected BufferedImage onRender() {
		if(object.transform.size.x<=1||object.transform.size.y<=1)return null;
		img=new BufferedImage((int)object.transform.size.x, (int)object.transform.size.y, BufferedImage.TYPE_INT_ARGB);
		Graphics g = img.createGraphics();
		g.setColor(color);
		g.fillRect(0, 0, (int)object.transform.size.x,(int)object.transform.size.y);//(0, 0, img.getWidth(), img.getHeight());
		return img;
	}
}
