package game.Component.A.movesandetc;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.Component.Component;
import game.Component.GameObject;

public class Scene3_PNG_Creator extends Component {
	
	private Image sceen3_image;
	
	public Scene3_PNG_Creator(GameObject pngCr, String pngPath) {
		super(pngCr);
		try {
			sceen3_image = ImageIO.read(new File(pngPath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	protected BufferedImage onRender() {
		return (BufferedImage) sceen3_image;
	}

}
