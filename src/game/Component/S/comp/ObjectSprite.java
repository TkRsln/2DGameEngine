package game.Component.S.comp;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.Component.Component;
import game.Component.GameObject;

public class ObjectSprite extends Component{

	Image img;
	
	public ObjectSprite(GameObject GO, String url) {
		super(GO);
		//Ship mesela
		try {
			img = ImageIO.read(new File(url));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Obje nas�l g�r�necek
	@Override 
	protected BufferedImage onRender() {
		return (BufferedImage) img;
	}
	

}
