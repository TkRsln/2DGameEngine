package game.Component.Test;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.Component.Component;
import game.Component.GameObject;

public class DecorSprite extends Component {

	BufferedImage img;
	
	public DecorSprite(GameObject obj,String path) {
		super(obj);
		try {
			img=ImageIO.read(new File(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Override
	protected BufferedImage onRender() {
		return img;
	}
	
	
}
