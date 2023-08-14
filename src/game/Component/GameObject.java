package game.Component;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;

import game.Engine.TAGS;
import game.Requirments.Collider;
import game.Requirments.Vector;

public class GameObject {
	
	public Transform transform = new Transform(this);
	public String name;
	public TAGS tag=TAGS.none;
	public int dt=0;

	private boolean enable=true;
	private ArrayList<Component> components=new ArrayList<Component>();
	private Color err = Color.pink;
	private long time_now=0;
	private Timer looper = new Timer();
	private static int timer_delay=17;
	
	
	
	public GameObject() {
	}
	public GameObject(Vector v) {
		transform.size=v;
	}
	public GameObject(double x,double y) {
		transform.size.x=x;
		transform.size.y=y;
	}
	public void wakeUp() {

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					//Thread.sleep(50);
					time_now=System.currentTimeMillis();
					sendOnStartCall();
					if(isLoopRequired())
						startLoop();
					
				} catch (Exception e) {
					e.printStackTrace();
					System.err.println("N:"+name+" T:"+tag);
				}
			}
		}).start();
	}
	public boolean getEnable() {
		return enable;
	}
	public void setEnable(boolean bool) {
		if(bool==enable)return;
		else {
			if(bool) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							//Thread.sleep(50);
							time_now=System.currentTimeMillis();
							if(isLoopRequired())
								startLoop();
						} catch (Exception e) {
							e.printStackTrace();
							System.err.println("N:"+name+" T:"+tag);
						}
					}
				}).start();
			}else {
				stopLoop();
			}
			enable=bool;
		}
	}
	private boolean isLoopRequired() {
		
		if(components==null)return false;
			synchronized (components) {
				for(Component com : components) {
					try {
						if(com.isLoopRequired()) {
							return true;
						}
					}catch (Exception e) {
						e.printStackTrace();
						System.err.println("N:"+name+" T:"+tag+" Com:"+com.getClass().getSimpleName());
					}
				}
			}
			return false;
	}
	private void sendOnStartCall() {
		try {
			if(components==null)return;
			for(Component com : components)
				com.onStart();
		}catch (Exception e) {
			e.printStackTrace();
			System.err.println("N:"+name+" T:"+tag);
		}
	}
	private void startLoop() {
		if(looper==null)
			looper=new Timer();
		looper.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					if(!enable)return;
					dt=(int)(System.currentTimeMillis()-time_now);
					onLoop();
					if(components==null)return;
					synchronized (components) {
						for(Component com : components)
							if(com!=null)
								com.onLoop();
					}
					time_now=System.currentTimeMillis();
				}catch (Exception e) {
					System.err.println(this.getClass().getSimpleName()+" : "+e.getMessage());
					//e.printStackTrace();
				}
			}
		},0,timer_delay);
	}
	public void stopLoop() {

		if(looper!=null) {
			try {
				looper.cancel();
				//looper.purge();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		looper=null;
	}
	protected void onDestroy() {
		
	}
	protected void onLoop() {
		
	}
	public Image OnRender() {

		if(!enable)return null;
		//synchronized (components) {
			for(Component com : components) {
				Image img =com.onRender();
				if(img!=null)return img;
			}
		//}
		return null;
	}
	
	public void onDynamicCollision(DynamicCollider col) {
	}
	public void onConstantCollision(Collider col) {
		
	}
	public void onTriggerCollision(GameObject obj) {
		
	}
	
	public <T extends Component> void addComponent(T component) {
		components.add(component);
	}
	public <T extends Component> Component getComponent(Class<T> cls) {
		for(Component c : components)
			if(c.getClass()==cls)
				return c;
		return null;
	}
	
	
	public static void Destroy(GameObject obj) {
		obj.Destroy();
	}
	public static void Destroy(GameObject obj,int delay) {
		obj.Destroy(delay);
	}
	public void Destroy() {
		Destroy(0);
	}
	public  <T extends Component> void Destroy(Class<T> comp) {
		Destroy(comp,0);
	}
	public <T extends Component>void Destroy(final Class<T> componentType,final int delay) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(delay);
					synchronized (components) {
						Component comp = getComponent(componentType);
						if(comp==null)return;
						comp.onDestroyComponent();
						components.remove(comp);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}).start();;
	}
	public void Destroy(final int delay) {
		new Thread( new Runnable() {
			public void run() {
				try {

					Thread.sleep(delay);
					stopLoop();
					onDestroy();
					if(components==null)return;
					synchronized (components) {
						if(components!=null)
						for(Component com : components) {
							if(com!=null)
								com.onDestroyComponent();
						}
						if(transform!=null)
							transform.Destroy();

						if(components!=null) {
							components.clear();
							components=null;
						}
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	@Override
	public String toString() {

		return ("N:"+name+" T:"+tag);
	}
	
	
}
