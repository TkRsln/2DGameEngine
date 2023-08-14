package game.Component.Utku;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class Animation {
	
	public Animation() {
		// TODO Auto-generated constructor stub
	}
	public Animation(boolean auto) {
		this.auto=auto;
	}
	
	private static HashMap<String, Image[]> allAnimations = new HashMap<String, Image[]>();
	private static String path="img/Animations/";
	
	private Image[] anims= new Image[0];
	private int current_anim=0;
	private boolean auto=true;
	
	
	public Image getPNG() {
		if(anims.length<=current_anim)return null;
		return anims[current_anim];
	}
	public void next() {
	if(auto)
		current_anim=(current_anim+1)%anims.length;
	else 
		current_anim=(current_anim+1==anims.length?anims.length-1:current_anim+1);
	}
	public Image nextPNG() {
		if(auto)
			current_anim=(current_anim+1)%anims.length;
		else 
			current_anim=(current_anim+1==anims.length?anims.length-1:current_anim+1);
		return anims[current_anim];
	}
	public void reset() {
		current_anim=0;
	}
	public Image getPNG(int no) {
		no=no%anims.length;
		return anims[no];
	}
	
	public void loadAnim(String animation_name) {
		if(allAnimations.containsKey(animation_name)) {
			anims=allAnimations.get(animation_name);
			//System.out.println(animation_name+" "+anims.length);
		}else {

			try {
				int count=countPNGs(animation_name);
				if(count==0)
					return;
				anims=new Image[count];
				for(int i=0;i<count;i++) {
						anims[i]= ImageIO.read(new File(path+animation_name+(i+1)+".png"));	
				}
				allAnimations.put(animation_name, anims);
				
				//System.out.println(this);
				//System.out.println(animation_name+" "+anims.length);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private int countPNGs(String name) {
		int index=1;
		File f = new File(path+name+index+".png");
		while(f.exists()) {
			index++;
			f = new File(path+name+index+".png");
		}
		return index-1;
	}
	@Override
	public String toString() {
		return "animation["+anims.length+"]";
	}
	
	
}
