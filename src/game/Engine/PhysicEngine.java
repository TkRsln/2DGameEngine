package game.Engine;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import game.Component.DynamicCollider;
import game.Component.GameObject;
import game.Component.HitBox;
import game.Component.RigidBody;
import game.Component.Transform;
import game.Requirments.Collider;
import game.Requirments.ConstCollider;
import game.Requirments.Vector;

public class PhysicEngine {
	
	private static ArrayList<HitBox> constant_boxes = new ArrayList<HitBox>();
	private static ArrayList<HitBox> dynamic_boxes = new ArrayList<HitBox>();
	private static ArrayList<HitBox> trigger_boxes = new ArrayList<HitBox>();
	

	private static ArrayList<DynamicCollider> colliders = new ArrayList<DynamicCollider>();
	private static ArrayList<Collider> colliders_constant = new ArrayList<Collider>();
	//private static ArrayList<Collider> colliders_trigger = new ArrayList<Collider>();
	
	
	private static float energy_loss=0.8f;
	private static float energy_tolerance=0.5f;
	private static Timer loop = null;
	private static boolean engine=false;
	
	private static long old_time=0;
	
	private static Object physic_loop_lock=new Object();
	
	public static void stopEngine() {
		engine=false;
		if(loop==null)return;
		loop.cancel();
		loop.purge();
		loop=null;
	}
	public static void startEngine() {
		old_time=System.currentTimeMillis();
		engine=true;
		stopEngine();
		loop=new Timer();
		loop.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					calculateCollision();
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, 0,12);
	}
	public static void add(HitBox ht) {
		if(ht.object ==null)return;
		synchronized (trigger_boxes) {

			if(((HitBox)ht.object.getComponent(HitBox.class)).isTrigger) {
				trigger_boxes.add(ht);
				return;
			}
		}

		if(ht.object.getComponent(RigidBody.class)==null) {
			if(!constant_boxes.contains(ht))
				addConstant(ht);
		}
		else {
			if(!dynamic_boxes.contains(ht))
				addDynamic(ht);
		}
	}
	public static void remove(HitBox obj) {
		synchronized (trigger_boxes) {
			if(((HitBox)obj.object.getComponent(HitBox.class)).isTrigger) {
				trigger_boxes.remove(obj);
				return;
			}
		}
		if(obj.object.getComponent(RigidBody.class)==null)
			//if(constant_boxes.contains(obj))
				removeConstant(obj);
		else
			//if(dynamic_boxes.contains(obj))
				removeDynamic(obj);
	}
	public static void removeConstant(HitBox tr) {
		synchronized (constant_boxes) {
			constant_boxes.remove(tr);
		}
		//System.out.println("removeConstant");
	}
	public static void removeDynamic(HitBox tr) {
		synchronized (dynamic_boxes) {
			dynamic_boxes.remove(tr);
		}
		//System.out.println("removeDynamic");
	}
	public static void addConstant(HitBox ht) {
		synchronized (constant_boxes) {
			constant_boxes.add(ht);
		}
		//System.out.println("addConstant");
	}
	public static void addDynamic(HitBox tr) {
		synchronized (dynamic_boxes) {
			dynamic_boxes.add(tr);
		}
		//System.out.println("addDynamic");
	}
	
	private static void calculateCollision() {

		Collider col = null;
		DynamicCollider dy=null;
		
		// DYNAMÝIC
		synchronized (colliders) {
			colliders.clear();
		synchronized (dynamic_boxes) {
		for(HitBox h_referans : dynamic_boxes) {
			if(!h_referans.object.getEnable())continue;
			Transform referans =h_referans.transform;
			synchronized (referans) {
					for(HitBox h_dyn : dynamic_boxes) {
						if(!h_dyn.object.getEnable())continue;
						Transform dyn =h_dyn.transform;
						synchronized (dyn) {
	
							if(referans==dyn)continue;
							if(referans.isPointXInside(dyn.getStartX())||referans.isPointXInside(dyn.getEndX())) {
								if(referans.isPointYInside(dyn.getStartY())||referans.isPointYInside(dyn.getEndY())) {
									
									if(!dynamicContains(referans,dyn)) {
										
											//dy=new DynamicCollider(referans.gameobject,dyn.gameobject);
											//colliders.add(dy);
											colliders.add(new DynamicCollider(referans,dyn));
										
										
									}
									//col.addGameObject(obj);
									/*
									if(!isContains(referans, dyn)) {
										if(h_referans.isTrigger) {
											//referans.gameobject.onTriggerCollision(const_t.gameobject);
											sendTriggerInfo(referans,dyn);
											continue;
										}
										else if(h_dyn.isTrigger) {
											//const_t.gameobject.onTriggerCollision(referans.gameobject);
											sendTriggerInfo(dyn,referans);
											continue;
										}
										if(col==null)
											col=new Collider(referans.gameobject);
										col.addGameObject(dyn.gameobject);

									}
									//*/
								}
							}
							
						}
					}
				}
			}
			//if(col!=null) {
			//	colliders.add(dy);
			//	col=null;
			//}
		}
		}
		/// TRIGGER
		//synchronized (colliders_trigger) {
		//	colliders_trigger.clear();
		//}
		/*
		synchronized (trigger_boxes) {
		for(HitBox h_referans : trigger_boxes) {
			if(!h_referans.object.getEnable())continue;
			Transform referans =h_referans.transform;
			synchronized (referans) {
					for(HitBox h_dyn : constant_boxes) {
						if(!h_dyn.object.getEnable())continue;
						Transform dyn =h_dyn.transform;
						synchronized (dyn) {
	
							if(referans==dyn)continue;
							if(referans.isPointXInside(dyn.getStartX())||referans.isPointXInside(dyn.getEndX())) {
								if(referans.isPointYInside(dyn.getStartY())||referans.isPointYInside(dyn.getEndY())) {
									if(!isContains(referans, dyn)) {
										if(h_referans.isTrigger) {
											//referans.gameobject.onTriggerCollision(const_t.gameobject);
											sendTriggerInfo(referans,dyn);
											continue;
										}
										else if(h_dyn.isTrigger) {
											//const_t.gameobject.onTriggerCollision(referans.gameobject);
											sendTriggerInfo(dyn,referans);
											continue;
										}
									}
								}
							}
							
						}
					}
				}
			}
		}//*/
		// CONSTANT
		synchronized (colliders_constant) {
			colliders_constant.clear();
		//synchronized (GameEngine.root) {
	
		synchronized (dynamic_boxes) {
			synchronized (constant_boxes) {
				for(HitBox h_ref : dynamic_boxes) {
					if(h_ref.rb==null)continue; // son sahne hatasý 
					h_ref.rb.onGround=false;	
					Transform referans=h_ref.transform;
					if(referans.gameobject==null)continue; // son sahne hatasý 
					synchronized (referans) {
						if(!referans.gameobject.getEnable())continue;
						for(HitBox h_const : constant_boxes) {
							if(!h_const.object.getEnable())continue;
							Transform const_t=h_const.transform;
							synchronized (const_t) {
								if(referans==const_t)continue;
								if(referans.isInHierarchy(const_t))continue;
								
								
								boolean x_start=const_t.isPointXInside(referans.getStartX());
								boolean x_end=const_t.isPointXInside(referans.getEndX());
								boolean y_start=const_t.isPointYInside(referans.getStartY());
								boolean y_end=const_t.isPointYInside(referans.getEndY());
								if(x_start||x_end) {
									//System.out.println("CC");
									if(y_start||y_end) {
										//System.out.println("DD");
										//if(!isContains(referans, const_t)) {
											if(h_ref.isTrigger) {
												//referans.gameobject.onTriggerCollision(const_t.gameobject);
												sendTriggerInfo(referans,const_t);
												continue;
											}
											else if(h_const.isTrigger) {
												//const_t.gameobject.onTriggerCollision(referans.gameobject);
												sendTriggerInfo(const_t,referans);
												continue;
											}

											if(col==null)
												col=new Collider(referans.gameobject);
											col.addGameObject(const_t.gameobject);
											
											collisionCxD(const_t, referans,h_ref);
											/*
											h_ref.rb.onGround=false;
											if(y_end&&!y_start&&const_t.getStartX()<h_ref.transform.getEndX()) {
												h_ref.rb.opositeY();
												h_ref.transform.setPositionY(const_t.getStartY()-h_ref.transform.size.y);
												h_ref.rb.onGround=true;
												//System.out.println("Alt");
											}else if(!y_end&&y_start&&const_t.getStartX()<h_ref.transform.getEndX()) {
												h_ref.rb.opositeY();
												h_ref.transform.setPositionY(const_t.getEndY());
												//System.out.println("Ust"+col.objects[1].name);
											}
											if(x_start&&!x_end&&const_t.getStartY()<h_ref.transform.getEndY()) {
												h_ref.rb.opositeX();
												h_ref.transform.setPositionX(const_t.getEndX());
												//System.out.println("Sað");
											}else if(!x_start&&x_end&&const_t.getStartY()<h_ref.transform.getEndY()) {
												h_ref.rb.opositeX();
												h_ref.transform.setPositionX(const_t.getStartX()-h_ref.transform.size.x);
												//System.out.println("Sol");
											}
											//*/
											
											
									}
								}
								
							}
						}
					}
					if(col!=null) {
						synchronized (colliders_constant) {
							colliders_constant.add(col);
						}
						col=null;
					}
				}
			}
		}
		//}
		}
		new Thread(new Runnable() {
			@Override
			public void run() {
				synchronized (colliders) {
					for(DynamicCollider col : colliders)
						sendDynamicInfoAll(col);
				}
			}
		}).start();
		//*/
		new Thread(new Runnable() {
			@Override
			public void run() {
				synchronized (colliders_constant) {
					for(Collider col : colliders_constant)
						sendConstantInfoAll(col);
				}
			}
		}).start();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				synchronized (physic_loop_lock) {
					sendPhysicLoop();
				}
			}
		}).start();
	}
	//////////////////////////////////////////////////////////////////////////////////////////
	public static boolean dynamicContains(Transform referans,Transform hitter) {
		for(DynamicCollider col : colliders) {
			if(col.isContains(referans)&&col.isContains(hitter)) {
				return true;
			}
		}
		return false;
	}
	private static void collisionDxD() {
		
	}
	private static void collisionCxD(Transform constant,Transform dynamic,HitBox dynamic_hit) {
		
		boolean dynamicOnRight=constant.getMidX() <dynamic.getMidX();
		boolean dynamicOnTop=constant.getMidY() <dynamic.getMidY();
		
		
		float surface_y=(float)(Math.min(constant.getEndY(), dynamic.getEndY())-Math.max(constant.getStartY(), dynamic.getStartY()));
		
		//float surface_x=(float)(Math.min(constant.getEndX(), dynamic.getEndX())-Math.max(constant.getStartX(), dynamic.getStartX()));
		float surface_x=(float)(Math.min(constant.getEndX(), dynamic.getEndX())-Math.max(constant.getStartX(), dynamic.getStartX()));
		
		
		
		if((surface_y)<(surface_x)) {
			if(constant.getMidY() > dynamic.getMidY()){
				dynamic.position.y=constant.getStartY()-dynamic.size.y;
				dynamic_hit.rb.opositeY(true);
				dynamic_hit.rb.onGround=true;
			}else {
				dynamic.position.y=constant.getEndY();
				dynamic_hit.rb.opositeY(false);
			}
		}else {
			if(constant.getMidX() <dynamic.getMidX()) {
				dynamic.position.x=constant.getEndX();
				dynamic_hit.rb.opositeX(true);
			}else {
				dynamic.position.x=constant.getStartX()-dynamic.size.x;
				dynamic_hit.rb.opositeX(false);
			}
		}
		
		
		 
		
		
		
		/*
		float dis_x=0;
		float dis_y=0;
		
		if(constant.getMidX() <dynamic.getMidX()) {	//Eðer sabit cisim, çarpan cismin solunda ise	===>		Const|  |Dynmic
			dis_x=(float)(dynamic.getStartX()-constant.getEndX());
		}else {																					//	===>		Dynamic|  |Const
			dis_x=(float)(constant.getStartX()-dynamic.getEndX());
		}
		
														//		Dynamic	
		if(constant.getMidY() > dynamic.getMidY() ) {	//  	Const
			dis_y=(float)(dynamic.getStartY()-constant.getEndY());
		}else {																					//	===>		Dynamic|  |Const
			dis_x=(float)(constant.getStartY()-dynamic.getEndY());
		}
		
		
		/*
		if(constant.getMidX() <dynamic.getMidX() && constant.getStartY()<dynamic.getStartY()) {  // Eðer sabit cisim, çarpan cismin solunda ise===>		Const|  |Dynmic
			if( constant.getEndX()>=dynamic.getStartX() ) {
				dynamic.position.x=constant.getEndX();
				dynamic_hit.rb.opositeX();
			}
		}
		else if(constant.getMidX() >dynamic.getMidX() && constant.getStartY()<dynamic.getStartY()) {//      Dynamic| |Const
			if( constant.getEndX()<=dynamic.getStartX() ) {
				dynamic.position.x=constant.getStartX()-dynamic.size.x;
				dynamic_hit.rb.opositeX();
			}
		}
																//	Dynamic		Eðer Sabit cisim, hareketlinin üstünde ise
		else if(constant.getMidY() > dynamic.getMidY()  ) {		//  Const    
			if( constant.getStartY()<=dynamic.getEndY() ) {
				dynamic.position.y=constant.getStartY()-dynamic.size.y;
				dynamic_hit.rb.opositeY();
				dynamic_hit.rb.onGround=true;	
			}
		}													//	Const		Eðer Sabit cisim, hareketlinin üstünde ise
		else if(constant.getMidY() < dynamic.getMidY()) {	//  Dynamic    
			if( constant.getStartY()>=dynamic.getEndY() ) {
				dynamic.position.y=constant.getEndY();
				dynamic_hit.rb.opositeY();
			}
		}*/
		
		
	}
	/*
	 * 
	private static ArrayList<HitBox> constant_boxes = new ArrayList<HitBox>();
	private static ArrayList<HitBox> dynamic_boxes = new ArrayList<HitBox>();
	private static ArrayList<HitBox> trigger_boxes = new ArrayList<HitBox>();
	

	private static ArrayList<DynamicCollider> colliders = new ArrayList<DynamicCollider>();
	private static ArrayList<Collider> colliders_constant = new ArrayList<Collider>();
	 * 
	 */
	public static void resetEngine() {
		synchronized (constant_boxes) {
			constant_boxes.clear();
		}synchronized (dynamic_boxes) {
			dynamic_boxes.clear();
		}synchronized (trigger_boxes) {
			trigger_boxes.clear();
		}synchronized (colliders) {
			colliders.clear();
		}synchronized (colliders_constant) {
			colliders_constant.clear();
		}
	}

	//////////////////////////////////////////////////////////////////////////////////////////
	private static void sendTriggerInfo(final Transform ref,final Transform info) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				ref.gameobject.onTriggerCollision(info.gameobject);
				
			}
		}).start();
	}
	
	private static void sendDynamicInfoAll(DynamicCollider col) {
		for(Transform tr : col.collision) {
			GameObject go = tr.gameobject;
			if(go!=null)
				try {
					go.onDynamicCollision(col);
				}catch (Exception e) {
					// TODO: handle exception
				}
		}
	}
	//*/
	private static void sendConstantInfoAll(Collider col) {
		/*
		int hit =0;
		for(int i=0;i<col.collided_objects;i++)
			if(!((HitBox)col.objects[i].getComponent(HitBox.class)).isTrigger);
				hit++;
		if(col.collided_objects-hit==1)
		*/
		for(int i=0;i<col.collided_objects;i++) {
			GameObject go = col.objects[i];
			if(go!=null)
				go.onConstantCollision(col);
		}
	}
	private static void sendPhysicLoop() {
		synchronized (dynamic_boxes) {
			for(HitBox ht : dynamic_boxes) {
				if(ht.rb==null)continue;
					ht.rb.onPhysicLoop((int)(System.currentTimeMillis()-old_time));
			}
		}
		old_time=System.currentTimeMillis();
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/*
	private static void calculateMomentum() {	
		for(Collider col : colliders) {
			float t_mom=getColliderMomentum(col);
			float t_mas=getColliderMass(col);
			for(GameObject o : col.objects) {
				if(o==null)continue;
				//((RigidBody)o.getComponent(RigidBody.class)).addVelocity(new Vector(col.objects[0].transform.position.x-col.objects[1].transform.position.x, col.objects[0].transform.position.y-col.objects[1].transform.position.y));
			}	
		}
	}//*/
	
	private static float getColliderMomentum(Collider col) {
		float m =0;
		for(int i=0;i<col.collided_objects;i++)
			m+=getObjectMomentum(col.objects[i]);
		return m;
	}
	private static float getObjectMomentum(GameObject obj) {
		if(obj==null)return 0;
		RigidBody rb = (RigidBody) obj.getComponent(RigidBody.class);
		if(rb==null)return 0;
		return rb.getMass()*rb.getVelocity();
		
	}
	private static float getColliderMass(Collider col) {
		float m =0;
		for(int i=0;i<col.collided_objects;i++)
			m+=getObjectMass(col.objects[i]);
		return m;
	}
	private static float getObjectMass(GameObject obj) {
		if(obj==null)return 0;
		RigidBody rb = (RigidBody) obj.getComponent(RigidBody.class);
		if(rb==null)return 0;
		return rb.getMass();
		
	}
	//////////////////////////////////////////////////////////////////////
	/*
	private static boolean isContains(Transform refer,Transform dyn) {
		for(Collider col: colliders) {
			if(col.objects[0]==dyn.gameobject)
				for(int i=1;i<col.collided_objects;i++)
					if(col.objects[i]==refer.gameobject)return true;
		}
		return false;
	}//*/
	
}



/*
 * 
											if(col==null)
												col=new Collider(referans.gameobject);
											col.addGameObject(const_t.gameobject);
											h_ref.rb.onGround=false;
											if(y_end&&!y_start&&const_t.getStartX()<h_ref.transform.getEndX()) {
												h_ref.rb.opositeY();
												h_ref.transform.setPositionY(const_t.getStartY()-h_ref.transform.size.y);
												h_ref.rb.onGround=true;
												//System.out.println("Alt");
											}else if(!y_end&&y_start&&const_t.getStartX()<h_ref.transform.getEndX()) {
												h_ref.rb.opositeY();
												h_ref.transform.setPositionY(const_t.getEndY());
												//System.out.println("Ust"+col.objects[1].name);
											}
											if(x_start&&!x_end&&const_t.getStartY()<h_ref.transform.getEndY()) {
												h_ref.rb.opositeX();
												h_ref.transform.setPositionX(const_t.getEndX());
												//System.out.println("Sað");
											}else if(!x_start&&x_end&&const_t.getStartY()<h_ref.transform.getEndY()) {
												h_ref.rb.opositeX();
												h_ref.transform.setPositionX(const_t.getStartX()-h_ref.transform.size.x);
												//System.out.println("Sol");
											}
											/*
											if(x_start&&!x_start) {
												h_ref.rb.opositeX();
												h_ref.transform.setPositionX(const_t.getEndX());
												System.out.println("x_start");
											}else if(x_end&&!x_start) {
												h_ref.rb.opositeX();
												h_ref.transform.setPositionX(const_t.getStartX());
												System.out.println("x_end");
											}else if(y_start&&!y_end) {
												h_ref.rb.opositeY();
												h_ref.transform.setPositionY(const_t.getEndY());
												System.out.println("y_start");
											}else if(y_end&&!y_start) {
												h_ref.rb.opositeY();
												h_ref.transform.setPositionY(const_t.getStartY());
												System.out.println("y_end");
											}*/
										//}
 
/*
 * synchronized (dynamic_boxes) {
			synchronized (constant_boxes) {
				for(HitBox h_ref : dynamic_boxes) {
					Transform referans=h_ref.transform;
					synchronized (referans) {
						for(HitBox h_const : constant_boxes) {
							Transform const_t=h_const.transform;
							synchronized (const_t) {
								if(referans==const_t)continue;
								if(referans.isInHierarchy(const_t))continue;
								
								
								boolean x_start=const_t.isPointXInside(referans.getStartX());
								boolean x_end=const_t.isPointXInside(referans.getEndX());
								boolean y_start=const_t.isPointYInside(referans.getStartY());
								boolean y_end=const_t.isPointYInside(referans.getEndY());
								if(x_start||x_end) {
									//System.out.println("CC");
									if(y_start||y_end) {
										//System.out.println("DD");
										//if(!isContains(referans, const_t)) {
										if(h_ref.isTrigger) {
											//referans.gameobject.onTriggerCollision(const_t.gameobject);
											sendTriggerInfo(referans,const_t);
											continue;
										}
										else if(h_const.isTrigger) {
											//const_t.gameobject.onTriggerCollision(referans.gameobject);
											sendTriggerInfo(const_t,referans);
											continue;
										}
											if(col==null)
												col=new Collider(referans.gameobject);
											col.addGameObject(const_t.gameobject);
											h_ref.rb.onGround=false;
											if(y_end&&!y_start&&const_t.getStartX()<h_ref.transform.getEndX()) {
												h_ref.rb.opositeY();
												h_ref.transform.setPositionY(const_t.getStartY()-h_ref.transform.size.y);
												h_ref.rb.onGround=true;
												//System.out.println("Alt");
											}else if(!y_end&&y_start&&const_t.getStartX()<h_ref.transform.getEndX()) {
												h_ref.rb.opositeY();
												h_ref.transform.setPositionY(const_t.getEndY());
												//System.out.println("Ust"+col.objects[1].name);
											}
											if(x_start&&!x_end&&const_t.getStartY()<h_ref.transform.getEndY()) {
												h_ref.rb.opositeX();
												h_ref.transform.setPositionX(const_t.getEndX());
												//System.out.println("Sað");
											}else if(!x_start&&x_end&&const_t.getStartY()<h_ref.transform.getEndY()) {
												h_ref.rb.opositeX();
												h_ref.transform.setPositionX(const_t.getStartX()-h_ref.transform.size.x);
												//System.out.println("Sol");
											}
											/*
											if(x_start&&!x_start) {
												h_ref.rb.opositeX();
												h_ref.transform.setPositionX(const_t.getEndX());
												System.out.println("x_start");
											}else if(x_end&&!x_start) {
												h_ref.rb.opositeX();
												h_ref.transform.setPositionX(const_t.getStartX());
												System.out.println("x_end");
											}else if(y_start&&!y_end) {
												h_ref.rb.opositeY();
												h_ref.transform.setPositionY(const_t.getEndY());
												System.out.println("y_start");
											}else if(y_end&&!y_start) {
												h_ref.rb.opositeY();
												h_ref.transform.setPositionY(const_t.getStartY());
												System.out.println("y_end");
											}
										//}
									}
								}
								
							}
						}
					}
					if(col!=null) {
						synchronized (colliders_constant) {
							colliders_constant.add(col);
						}
						col=null;
					}
				}
			}
		}
 * 
 */ 
