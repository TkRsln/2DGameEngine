package game.Component.Utku;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.Component.Component;
import game.Component.GameObject;

public class TextureComponent extends Component {

	private BufferedImage img;
	
	public TextureComponent(GameObject obj,String pathTop,String pathBot) {
		super(obj);
		
		BufferedImage topSample;
		BufferedImage botSample;
		try {
			topSample = ImageIO.read(new File(pathTop));
			botSample=ImageIO.read(new File(pathBot));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
		img=new BufferedImage((int)transform.size.x, (int)transform.size.y, BufferedImage.TYPE_INT_ARGB);
		Graphics g = img.getGraphics();
		for(int x=0;x<transform.size.x;x+=topSample.getWidth()) {
			g.drawImage(topSample, x, 0,null);
		}
		for(int y=topSample.getHeight();y<transform.size.y;y+=botSample.getHeight()) {
			for(int x=0;x<transform.size.x;x+=botSample.getWidth()) {
				g.drawImage(botSample, x, y,null);
			}
		}
		
		
	}
	
	@Override
	protected BufferedImage onRender() {
		return img;
	}
	
}
