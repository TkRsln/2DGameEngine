package game.Component;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import game.Engine.PhysicEngine;

public class HitBox  extends Component{

	private BufferedImage debug_img=null;
	private boolean debug=false;
	public RigidBody rb;
	
	public HitBox(GameObject obj) {
		super(obj);
		// TODO Auto-generated constructor stub
	}public HitBox(GameObject obj,boolean triger) {
		super(obj);
		this.isTrigger=triger;
	}
	public HitBox(GameObject obj,boolean triger,boolean debug) {
		super(obj);
		this.debug=debug;
		this.isTrigger=triger;
		
	}
	public boolean isTrigger=false;
	
	@Override
	protected void onStart() {
		try {
			rb=(RigidBody) object.getComponent(RigidBody.class);
			PhysicEngine.add(this);
			if(debug) {
				debug_img=new BufferedImage((int)object.transform.size.x,(int)object.transform.size.y, BufferedImage.TYPE_INT_ARGB);
				Graphics g = debug_img.getGraphics();
				g.setColor(Color.GREEN);
				g.drawRect(0, 0, debug_img.getWidth(), debug_img.getHeight());
				g.drawRect(1, 1, debug_img.getWidth()-1, debug_img.getHeight()-1);
			}
		}catch (Exception e) {
			System.err.println(object.name);
		}
	}
	
	@Override
	protected void onDestroyComponent() {
		rb=null;
		PhysicEngine.remove(this);
	}
	
	@Override
	protected BufferedImage onRender() {
		// TODO Auto-generated method stub
		return debug_img;
	}
}
