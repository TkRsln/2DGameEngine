package game.Menu;

import java.awt.Font;
import java.awt.Image;
import java.awt.Menu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import game.Main;
import game.Component.A.Enemy_Scene3;
import game.Component.Utku.DecorObject;
import game.Component.Utku.PlatformObject;
import game.Component.Utku.Player1;
import game.Component.Utku.Player1Move;
import game.Component.Utku.Ship1Scene;
import game.Component.Utku.Alien.Alien1;
import game.Component.Utku.Boss.Boss1;
import game.Engine.Camera;

public class MainMenu  {
	
	public static int buttonSizeX = 300;
	public static int buttonSizeY = 80;
	public static int space = 100;
	
	private static JButton j1,j2,j3;
	
	public static void startMenu() {
		Image newimg = null;
		try {
			newimg = ImageIO.read(new File("img/btn_icon.png")).getScaledInstance( buttonSizeX-10, buttonSizeY,  java.awt.Image.SCALE_SMOOTH ) ;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Main.cam1.setWorldY(-Main.app.getHeight()+800);
		
		j1 = new JButton();
		j1.setBounds(getMidX()-buttonSizeX/2,getStartY()-buttonSizeY/2 , buttonSizeX, buttonSizeY);		
		j1.setOpaque(false);
		j1.setContentAreaFilled(false);
		j1.setBorderPainted(false);
		j1.setText("Single Player");
		j1.setHorizontalTextPosition(SwingConstants.CENTER);
		j1.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MenuShip.respawn=false;
				Main.nop=1;
				Main.Scene1();
				
			}
		} );
		Main.cam1.add(j1);
		
		j2 = new JButton();
		j2.setBounds(getMidX()-buttonSizeX/2,getStartY()-buttonSizeY/2+space , buttonSizeX, buttonSizeY);	
		j2.setOpaque(false);
		j2.setContentAreaFilled(false);
		j2.setBorderPainted(false);
		j2.setText("Multi Player");
		j2.setHorizontalTextPosition(SwingConstants.CENTER);
		Main.cam1.add(j2);
		j2.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MenuShip.respawn=false;
				Main.nop=2;
				Main.Scene1();
				
			}
		} );
		
		j3 = new JButton();
		j3.setBounds(getMidX()-buttonSizeX/2,getStartY()-buttonSizeY/2+space*2 , buttonSizeX, buttonSizeY);
		j3.setOpaque(false);
		j3.setContentAreaFilled(false);
		j3.setBorderPainted(false);
		j3.setText("Exit");
		j3.setHorizontalTextPosition(SwingConstants.CENTER);
		Main.cam1.add(j3);
		
		if (Main.font!=null) {
			//j1.setFont(Main.font.deriveFont(Main.font.getSize()*1.4F));
			//j2.setFont(Main.font.deriveFont(Main.font.getSize()*1.4F));
			//j3.setFont(Main.font.deriveFont(Main.font.getSize()*1.4F));
			j1.setFont(Main.font.deriveFont(Font.PLAIN,20));
			j2.setFont(Main.font.deriveFont(Font.PLAIN,20));
			j3.setFont(Main.font.deriveFont(Font.PLAIN,20));
			
		}
		
		if (newimg!=null) {
			j1.setIcon(new ImageIcon(newimg));
			j2.setIcon(new ImageIcon(newimg));
			j3.setIcon(new ImageIcon(newimg));
		}
		
		SceneMenu();
		
		
		
	}

	public static void hideAll() {
		//Main.app.removeAll();
		//Main.app.add(Main.cam);
		//Main.app.remove(j1);
		//Main.app.remove(j2);
		//Main.app.remove(j3);
		//Main.app.revalidate();
		//Main.app.repaint();
		if(j1==null)return;
		j1.setVisible(false);
		j2.setVisible(false);
		j3.setVisible(false);
		Main.app.requestFocus();
	}
	public static void showAll() {
		//Main.app.remove(j1);
		//Main.app.remove(j2);
		//Main.app.remove(j3);
		j1.setVisible(true);
		j2.setVisible(true);
		j3.setVisible(true);
	}
	private static int getMidX() {
		return Main.app.getWidth()/2;
	}
	private static int getStartY() {
		return Main.app.getHeight()/3;
	}
	
	public static void SceneMenu() {
		Random rand = new Random();
        try {
            Main.cam1.background=ImageIO.read(new File("img/Enviroment/back.jpg"));
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

            for(int x=-700;x<2000;x+=800)
                new PlatformObject(x, 600,"img/Platform/top.png" ,"img/Platform/bot.png");

        for(int x=300;x<2000;x+=400) {
            DecorObject d=new DecorObject(x+rand.nextInt(3)*100, 610);
            d.transform.scale.x=rand.nextBoolean()?-1:1;
        }


        //new Ship1Scene(Main.cam, 600, 460);
        MenuShip.respawn=true;
        new MenuShip();
        new Alien1(100,460);
        new Alien1(300, 460);
        new Enemy_Scene3(400, 80);
        Boss1 b=new Boss1(300, 100);
        b.platform_front=new PlatformObject(4800, 300, 300, 500,"img/Platform/top.png" ,"img/Platform/bot.png");

    }

	
	
}
