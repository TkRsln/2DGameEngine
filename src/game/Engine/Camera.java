package game.Engine;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

import game.Component.GameObject;
import game.Component.Transform;
import game.Requirments.Vector;

public class Camera extends JPanel {
	
	
	
	public Camera() {
        //setBackground(Color.black);
        //startFinderLoop();
        active=this;
        paintLoop();
	}
	
	public static Camera active;
	
	public Image 	background;
	
	public float 	render_distance=1.5f;
	

	public double 	follow_distance_x=200f;
	public double 	follow_distance_y=100f;
	
	private int 	screen_x=0;
	private int 	screen_y=0;
	private Vector 	word_position=new Vector();
	
	private double 	width=0;
	private double 	height=0;
	//private Timer 	loop_finder=new Timer();
	private Timer 	loop_follower=null;
	private Timer 	paint_loop;
	
	public double 	follow_speed_x=0.05f;
	public double 	follow_speed_y=0.01f;
	
	private int 	shake_dt=0;
	private long 	shake_last_time=0;
	private double 	shake_target_y=300;
	private double 	shake_y=0;
	private int 	shake_until=1500;
	private boolean shake_start=false;
	private int 	shake_count_time=0;
	private int 	shake_count_total_time=0;
	private Random 	random=new Random();
	
	
	private Transform follow;
	
	public void setFollow(Transform toFollow) {
		if(toFollow==follow)return;
		if(toFollow==null) {
			stopFollowLoop();
			follow=toFollow;
		}
		else {

			//System.out.println("startFollow "+(loop_follower==null));
			//synchronized (follow) {
				follow=toFollow;
			//}

			if(loop_follower==null) {
				//System.out.println("startFollowLoop");
				startFollowLoop();
			}
		}
		
	}
	private void startFollowLoop() {
		try {
			if(loop_follower!=null){
				loop_follower.cancel();
				loop_follower.purge();
				loop_follower=null;
			}
			loop_follower=new Timer();
			//System.out.println("loop_follower");
			loop_follower.schedule(new TimerTask() {
				@Override
				public void run() {
					loopFollowObject();
				}
			}, 0,20);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void stopFollowLoop() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				if(loop_follower==null)return;
				loop_follower.cancel();
				loop_follower.purge();
				loop_follower=null;
			}
		}).start();
	}
	private void loopFollowObject() {
			if(follow!=null&&follow.position!=null&&follow.gameobject!=null&&follow.gameobject.getEnable()) {
				synchronized (follow) {
					addPosition((follow.position.x+follow_distance_x-getMidX())*follow_speed_x,(follow.position.y-follow_distance_y-getMidY())*follow_speed_y);
				}
			//else { 
				//follow=null;
				//stopFollowLoop();
			//}
		}
	}
	
	//////////////////////////////////////////[SHAKE AREA]
	public void startShake(int time) {
		shake_until=time;
		shake_start=true;
	}
	private void shake() {
		if(!shake_start)return;
		shake_count_time+=shake_dt;
		shake_count_total_time+=shake_dt;
		if(shake_count_time>100) {
			shake_count_time=0;
			shake_y=(random.nextInt(10)-5)*1;
		}else {
			shake_y+=(shake_target_y-shake_y)*0.01;
		}
		if(shake_count_total_time>shake_until) {
			shake_count_total_time=0;
			shake_until=0;
			shake_y=0;
			shake_count_time=0;
			shake_count_total_time=0;
			shake_start=false;
		}
			
		
	}
	//////////////////////////////////////////
	
	private ArrayList<Transform> toRender = new ArrayList<Transform>();
	//private final Object list_lock = new Object();
	
	public void setWorldX(double x) {
		word_position.x=x;
	}public void setWorldY(double y) {
		word_position.y=y;
	}
	public double getWorldX() {
		return word_position.x;
	}public double getWorldY() {
		return word_position.y;
	}
	public void addPosition(Vector add) {
		word_position.addVector(add);
	}
	public void addPosition(double x,double y) {
		word_position.addVector(x,y);
	}
	public void setPosition(double x,double y) {
		word_position.x=x;
		word_position.y=y;
	}
	public void setSize(int width,int height) {
		setBounds(screen_x,screen_y,width,height);
	}
	public double getMidX() {
		return word_position.x+width/2;
	}
	public double getMidY() {
		return word_position.y+height/2;
	}
	
	@Override
	public void setBounds(int x, int y, int width, int height) {
		screen_x=x;
		screen_y=y;
		this.width=width;
		this.height=height;
		super.setBounds(x, y, width, height);
	}
	
	
	//============================================Render
	public void paintLoop() {
		paint_loop = new Timer();
		paint_loop.schedule(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				shake_dt=(int) (System.currentTimeMillis()-shake_last_time);
				loopFollowObject();
				shake();
				repaint();
				shake_last_time=System.currentTimeMillis();
			}
		}, 0,10);
	}
	 @Override 
	 public void paintComponent(Graphics g) {
	        super.paintComponent(g);

	       //synchronized (list_lock) {
	    	//	System.out.println("Draw");
	        //try {
	        	if(background!=null)
	        		g.drawImage(background, 0, 0, getWidth(), getHeight(),null);
	        	synchronized (GameEngine.root) {
		        	for(Transform tr : GameEngine.root) {
		        		synchronized (tr) {
		        			if(tr.getEndX()>(word_position.x) && tr.getStartX()<word_position.x+width)
			        			if(tr.gameobject.getEnable())
			        				startDrawChilds(g,tr);
						}
		        		
		        	}
				}
	        //}catch (Exception e) {
	        //	e.printStackTrace();
				// TODO: handle exception
			//}
	        	//System.out.println(GameEngine.root.size());
			//}
	        /*
	        //write either "RED" or "BLUE" using graphics
	        g.setColor(Color.WHITE);
	        g.setFont(new Font("serif", Font.BOLD, 60));
	        g.drawString("HH", getWidth() / 2 - g.getFontMetrics().stringWidth("HH") / 2,
	                getHeight() / 2 + g.getFontMetrics().getHeight() / 2);
	         */
	 }
	 private void startDrawChilds(Graphics g,Transform tr) {
	 		drawChilds(g, tr, 0, 0);
	 }
	 private void drawChilds(Graphics g,Transform tr,int totX,int totY) {
		 synchronized (tr) {

			 	if(tr==null|| g==null || tr.gameobject==null)return;
			 		Image img = tr.gameobject.OnRender();
		 		if(img!=null) {
		 			//g.drawImage(img,(int)( tr.getStartX()+totX-word_position.x),(int) (tr.getStartY()+totY-word_position.y),null);
		 			g.drawImage(img, (int)( tr.getStartX()+totX-word_position.x+(tr.getScaleX()<0?tr.size.x:0)), (int) (tr.getStartY()+totY-word_position.y-shake_y), (int)(tr.getScaleX()<0?-tr.size.x:tr.size.x), (int)tr.size.y, null);
		 			
		 			//g.drawIma
		 			//g.drawImage(img, x, y, width, height, observer)
		 		}
		 		totX+=tr.getStartX();
	 			totY+=tr.getStartY();

		 }
		 		for(int i=0;i<tr.getChildCount();i++) {
		 			//drawChilds(g, tr.getChild(i),totX,totY);
		 			drawChilds2(g, tr.getChild(i),totX,totY);
		 		}
	 }
	 private void drawChilds2(Graphics g,Transform tr,int totX,int totY) {
		 synchronized (tr) {

			 	if(tr==null|| g==null || tr.gameobject==null)return;
			 		Image img = tr.gameobject.OnRender();
		 		if(img!=null) {
		 			//g.drawImage(img,(int)( tr.getStartX()+totX-word_position.x),(int) (tr.getStartY()+totY-word_position.y),null);
		 			g.drawImage(img, (int)( GameEngine.objectStartPosition(tr).x-word_position.x), (int) (GameEngine.objectStartPosition(tr).y-word_position.y-shake_y), (int)(tr.getScaleX()<0?-tr.size.x:tr.size.x), (int)tr.size.y, null);
		 			//if(tr.gameobject.name=="Gun")System.out.println("CAM: "+GameEngine.objectStartPosition(tr));
		 			//g.drawIma
		 			//g.drawImage(img, x, y, width, height, observer)
		 		}
		 		totX+=tr.getStartX();
	 			totY+=tr.getStartY();

		 }
		 		for(int i=0;i<tr.getChildCount();i++) {
		 			drawChilds(g, tr.getChild(i),totX,totY);
		 		}
	 }
	 
	 public void stopCamera() {
		 if(loop_follower!=null) {
			 stopFollowLoop();
		 }
		 if(paint_loop!=null) {
			 try {
				 paint_loop.cancel();
				 paint_loop.purge();
				 paint_loop=null;
			 }catch (Exception e) {
				// TODO: handle exception
			}
		 }
	 }

		
	 
	 
}
/*
//===================================================
	private void startFinderLoop() {
		loop_finder.schedule(new TimerTask() {
			@Override
			public void run() {
				findObjectsToRender();
			}
		},0,1000);
	}
	private void findObjectsToRender() {
		//synchronized (list_lock) {

  	synchronized (GameEngine.root) {
		//System.out.println(GameEngine.root.size());
			toRender.clear();
			for(Transform tr : GameEngine.root) {
				if(tr.getDistanceX(getMidX())<width*render_distance) {
					if(!toRender.contains(tr))toRender.add(tr);
				}
				System.out.println(toRender.size());
			}
		}
	}
*/