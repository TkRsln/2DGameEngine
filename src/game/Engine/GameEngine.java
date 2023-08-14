package game.Engine;

import java.awt.event.KeyListener;
import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JPanel;

import game.Main;
import game.Component.GameObject;
import game.Component.Transform;
import game.Requirments.Vector;

public class GameEngine {
	
	public static ArrayList<Transform> root=new ArrayList<Transform>();
	
	
	
	//playerýn pozisyonu ve gun'ýn posizyonunu topluyor
	public static Vector objectWorldPosition(Transform tr) {
		if(tr==null||tr.position==null)return null;
		Vector pos=new Vector();
		pos.x=tr.position.x;
		pos.y=tr.position.y;
		Transform prnt = tr.getParent();
		while(prnt!=null&&prnt.position!=null) {
			pos.x+=prnt.position.x*(prnt.getParent()==null?1:prnt.getParent().scale.x);
			pos.y+=prnt.position.y;
			prnt=prnt.getParent();
		}
		return pos;
	}
	public static Vector objectStartPosition(Transform tr) {
		if(tr==null)return null;
		Vector pos=new Vector();
		synchronized (tr) {
			pos.x=(tr.position.x)*(tr.getParent()==null||tr.getParent().scale==null?1:tr.getParent().scale.x);
			pos.y=tr.position.y;
			Transform prnt = tr.getParent();
			while(prnt!=null&&prnt.position!=null) {
				pos.x+=prnt.getMidX()*(prnt.getParent()==null?1:prnt.getParent().scale.x);
				pos.y+=prnt.position.y;
				prnt=prnt.getParent();
			}
		}
		return pos;
		
	}public static Vector objectEndPosition(Transform tr) {
		if(tr==null)return null;
		Vector pos=new Vector();
		pos.x=(tr.position.x+tr.size.x)*(tr.getParent()==null?1:tr.getParent().scale.x);
		pos.y=tr.position.y;
		Transform prnt = tr.getParent();
		while(prnt!=null&&prnt.position!=null) {
			pos.x+=prnt.getMidX()*(prnt.getParent()==null?1:prnt.getParent().scale.x);
			pos.y+=prnt.position.y;
			prnt=prnt.getParent();
		}
		return pos;
	}
	public static double objectScaleX(Transform tr) {
		if(tr==null)return 0;
		double x=0;
		x=(tr.getParent()==null?1:tr.getParent().scale.x);
		Transform prnt = tr.getParent();
		while(prnt!=null&&prnt.position!=null) {
			x*=(prnt.getParent()==null?1:prnt.getParent().scale.x);
			prnt=prnt.getParent();
		}
		return x;
	}
	
	public static void clearAll() {
		for(KeyListener kl : Main.app.getKeyListeners())
			Main.app.removeKeyListener(kl);
		PhysicEngine.resetEngine();
		synchronized (GameEngine.root) {
			for(Transform tr : GameEngine.root)
				tr.gameobject.stopLoop();
			for(Transform tr : GameEngine.root)
				tr.gameobject.Destroy();
			GameEngine.root.clear();
		}
	}
}
