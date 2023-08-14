package game;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import game.Component.GameObject;
import game.Component.HitBox;
import game.Component.OvalSprite;
import game.Component.RectSprite;
import game.Component.RigidBody;
import game.Component.Transform;
import game.Component.A.Carnivorous;
import game.Component.A.Enemy_Scene3;
import game.Component.A.Finall_Boss;
import game.Component.A.Player_Scene3;
import game.Component.A.Ship;
import game.Component.A.Tree;
import game.Component.A.doorScene3;
import game.Component.A.grass;
import game.Component.A.movesandetc.PlayerMove;
import game.Component.S.EdgeControl;
import game.Component.S.EnemyShip;
import game.Component.S.LastSeen;
import game.Component.S.PlayerShip;
import game.Component.S.meteor;
import game.Component.S.satellite;
import game.Component.S.comp.ShipControl;
import game.Component.Test.BulletTest;
import game.Component.Test.Bush2;
import game.Component.Test.DecorSprite;
import game.Component.Test.GranadeTest;
import game.Component.Test.GunObject;
import game.Component.Test.ParticilarCreater;
import game.Component.Test.RigidTesst;
import game.Component.Utku.Alien2;
import game.Component.Utku.DecorObject;
import game.Component.Utku.PlatformObject;
import game.Component.Utku.Player1Move;
import game.Component.Utku.Player1;
import game.Component.Utku.Ship1Scene;
import game.Component.Utku.Alien.Alien1;
import game.Component.Utku.Boss.Boss1;
import game.EndScene.TheEndBackground;
import game.EndScene.TheEndWordObject;
import game.Engine.Camera;
import game.Engine.GameEngine;
import game.Engine.PhysicEngine;
import game.Engine.TAGS;
import game.Menu.MainMenu;
import game.Requirments.Vector;

public class Main {
	
	public static Font font;
	
	public static Random rand = new Random();
	public static JFrame app = new JFrame();
	
	public static Camera cam1;
	public static Camera cam2;
	
	public static JLabel bulletInfo;
	public static JLabel Score;
	
	public static int high_score=0;
	public static int current_score=0;
	
	public static int nop=1;
	public static char[][] keys= { {'w','s','d','a',' '},{'u','j','k','h','\n'} };
	
	public static void main(String[] args) {
		
		try {
            //font= new Font("Kristen ITC", Font.BOLD, 12);
			font=Font.createFont(Font.TRUETYPE_FONT, new File("img/PermanentMarker.ttf"));
            
            
        } catch (Exception e) {
        	e.printStackTrace();
        }
		readScoreFromFile();
		
		PhysicEngine.startEngine();
		

		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		
		app.setSize(size);
		app.setLayout(null);
		app.setVisible(true);
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		cam1 = new Camera();
		app.add(cam1);
		cam1.setBounds(0,0,size.width,size.height);
		cam1.setLayout(null);
		app.setResizable(false);
		
		Score = new JLabel("Score: "+current_score+" / High Score: "+high_score);
		Score.setBackground(Color.DARK_GRAY);
		Score.setFont(font.deriveFont(Font.PLAIN,20));
		Score.setHorizontalAlignment(SwingConstants.CENTER);
		
		cam1.add(Score);
		Score.setBounds(app.getWidth()-300,0,300,100);
		
		
		
		
		bulletInfo = new JLabel("Velocity: "+100+" /	Angle:"+180);
		bulletInfo.setBackground(Color.DARK_GRAY);
		bulletInfo.setFont(font.deriveFont(Font.PLAIN,20));
		bulletInfo.setHorizontalAlignment(SwingConstants.CENTER);
		
		cam1.add(bulletInfo);
		bulletInfo.setBounds(app.getWidth()-300,app.getHeight()-100,300,100);
		

		
		//nextScene();
		//Scene2();
		//Scene1();

		
		MainMenu.startMenu();
		//seneTest();
		//Scene3();
	}
	
	
	public static void nextScene() {

		new Thread(new Runnable() {
			
			@Override
			public void run() {

				try {
					Thread.sleep(5000);
					


					GameEngine.clearAll();
					cam1.setFollow(null);
					if(nop==2) {
						cam2.setFollow(null);
						cam2.background=null;
					}
					cam1.background=null;
					for(KeyListener kl : app.getKeyListeners())
						app.removeKeyListener(kl);
					System.out.println("Cleared all "+GameEngine.root.size());
					
					Scene2();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}).start();
		
	}
	
		
	public static void Scene2() {
		cam1.follow_distance_y = -50;
		if(nop==2) 
			cam1.follow_distance_y=-50;
			
		try {
			Image img =ImageIO.read(new File("img/space2.jpg"));
			cam1.background = img;
			if(nop==2)cam2.background=img;
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		for(int x=0; x<6000; x+=800) {
			new satellite(x+rand.nextInt(800), 400-rand.nextInt(500));
		}
		
		for(int x=0; x<6000; x+=800) {
			new meteor(x+rand.nextInt(800), 400-rand.nextInt(500));
		}
		
		new LastSeen(cam1, 5800, 425);
		
		for(int i=0;i<nop;i++) {
			GameObject s2 = new PlayerShip(keys[i]);
			s2.transform.position = new Vector(0,50);
			 if(i==0)
	            	cam1.setFollow(s2.transform);
	         else if(i==1)
	            	cam2.setFollow(s2.transform);
			app.addKeyListener((KeyListener) s2.getComponent(ShipControl.class));
		}
		
		GameObject ustS = new EdgeControl(-400,-200);
		GameObject altS = new EdgeControl(-400,400);
		
		new EnemyShip(400,50);
		new EnemyShip(1400,50);
		new EnemyShip(2400,50);
		new EnemyShip(3400,50);
		
		
	}
	public static void nextScene2() {

		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(5000);
					cam1.setFollow(null);
					cam1.background=null;
					GameEngine.clearAll();
					if(nop==2) {
						cam2.setFollow(null);
						cam2.background=null;
					}
					
					System.out.println("Cleared all "+GameEngine.root.size());
					
					Scene3();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}).start();
	}
	
	public static void Scene1() {

		MainMenu.hideAll();
		GameEngine.clearAll();
		
		if(nop==2) {
			if(cam2==null) {
				cam2=new Camera();
				app.add(cam2);
				cam2.setBounds(0, app.getHeight()/2, app.getWidth(), app.getHeight()/2);
				cam1.setBounds(0,0, app.getWidth(), app.getHeight()/2);
			}
		}
		

		bulletInfo.setBounds(cam1.getWidth()-300,cam1.getHeight()-100,300,100);
		
        try {
        	Image back=ImageIO.read(new File("img/Enviroment/back.jpg"));
            cam1.background=back;
            if(nop==2)cam2.background=back;
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }


        new PlatformObject(-1000, 215,801,585,"img/Platform/top.png" ,"img/Platform/bot.png");
            for(int x=-200;x<6000;x+=800)
                new PlatformObject(x, 600,"img/Platform/top.png" ,"img/Platform/bot.png");

        for(int x=300;x<5200;x+=400) {
            DecorObject d=new DecorObject(x+rand.nextInt(3)*100, 610);
            d.transform.scale.x=rand.nextBoolean()?-1:1;
        }
        
        Player1 player1 = new Player1(keys[0]);
        Player1 player2=null;
        app.addKeyListener((Player1Move) player1.getComponent(Player1Move.class));
        cam1.setFollow(player1.transform);
        cam1.follow_distance_y=player1.transform .size.y*0.2;
        if(nop==2) {
        	player2 = new Player1(keys[1]);
            cam2.setFollow(player2.transform);
            cam2.follow_distance_y=player2.transform .size.y*0.2;
            app.addKeyListener((Player1Move) player2.getComponent(Player1Move.class));
        }

        new Alien1(1900,460);
        new Alien1(2800,460);
        new Alien1(3800,460);
        new Alien1(4800,460);
        //new Alien2(100,460);
        Boss1 b=new Boss1(4800, 100);
        b.platform_front=new PlatformObject(4800, 300, 300, 500,"img/Platform/top.png" ,"img/Platform/bot.png");

        new Ship1Scene(cam1, 5800, 460,player1,player2);
        

    }
	
	
	public static void Scene3() {
        try {
        	Image img=ImageIO.read(new File("img/Enviroment/BG_3.jpg"));
            cam1.background=img;
            if(nop==2) 
            	cam2.background=img;
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        for(int i =0; i<8;i++) {
            new PlatformObject(-1100+i*1000,600,1000,500,"img/scene3_ground.png" ,"img/scene3_ground_bot.png");
        }
        
        new PlatformObject(6900,400,250,500,"img/scene3_ground.png" ,"img/scene3_ground_bot.png");
        new PlatformObject(-1350,400,250,500,"img/scene3_ground.png" ,"img/scene3_ground_bot.png");
        
        for(int  x = 0; x<5500; x+=400) {
            new grass(x+rand.nextInt(300),487);
        }
        for(int  x = 400; x<6000; x+=800) {
            new Tree(x+rand.nextInt(500), 405);
        }
        for(int  x = 740; x<6000; x+=2300) {
            new Carnivorous(x+rand.nextInt(400), 400);
        }
        Player_Scene3 ps1 = new Player_Scene3(keys[0]);
        //cam1.setFollow(ps1.transform);
        app.addKeyListener((KeyListener) ps1.getComponent(PlayerMove.class));
        ps1.setEnable(false);
        
        
        Player_Scene3 ps2 = null;
        if (nop == 2) {
        	 ps2 = new Player_Scene3(keys[1]);
        	 //cam2.setFollow(ps2.transform);
             app.addKeyListener((KeyListener) ps2.getComponent(PlayerMove.class));
             ps2.setEnable(false);
		}
        
        
       /*   
        for(int i=0;i<nop;i++) {
	        Player_Scene3 ps3 = new Player_Scene3();
	        
	      //  cam1.setFollow(ps3.transform);
	        app.addKeyListener((KeyListener) ps3.getComponent(PlayerMove.class));
	        ps3.setEnable(false);
	        
        }*/
        //new Enemy_Scene3(100, 100);
        new Enemy_Scene3(500, 100);
        new Finall_Boss(5500, 400);
        new doorScene3(4800 , 475);
        Ship s3 = new Ship(-100,-300,ps1,ps2);
        cam1.setFollow(s3.transform);
        if(nop==2)cam2.setFollow(s3.transform);
    }
	
	public static void addScore(int score) {
		synchronized (Score) {
			current_score += score;
			Score.setText("Score: "+current_score+" / High Score: "+high_score);
			
		}
		
		
		
	}
	private static void writeScoreToFile() {
		try {
			File score = new File("score");
			if(!score.exists())
				score.createNewFile();
			BufferedWriter bw = new BufferedWriter(new FileWriter(score));
			bw.write(high_score+" ");
			bw.newLine();
			bw.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	private static void readScoreFromFile() {
		try {
			File score = new File("score");
			if(!score.exists())return;
			BufferedReader br = new BufferedReader(new FileReader(score));
			String str = br.readLine();
			high_score = Integer.parseInt(str.replace(" ", ""));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	private static void calculateScore() {
		if(current_score>high_score) {
			high_score=current_score;
		}
		current_score=0;
		Score.setText("Score: "+current_score+" / High Score: "+high_score);
		writeScoreToFile();
		
		
		
	}
	public static void endScene() {
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				
				if(cam2!=null) {
					cam2.setFollow(null);
					cam2.setWorldX(cam1.getWorldX());
					cam2.setWorldY(cam1.getWorldY());
				}
				//*/
				new TheEndBackground();
				new TheEndWordObject();
				
				cam1.setFollow(null);
				Player_Scene3.active_pl.setEnable(false);
				
				try {
					Thread.sleep(4500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				calculateScore();
				
				
				if(cam2!=null) {
					app.remove(cam2);
					cam2.stopCamera();
					cam2=null;
					
					cam1.setBounds(0,0,app.getWidth(),app.getHeight());
					bulletInfo.setBounds(app.getWidth()-300,app.getHeight()-100,300,100);
				}
				
				cam1.setWorldX(0);
				cam1.setWorldY(-Main.app.getHeight()+800);
				GameEngine.clearAll();
				MainMenu.SceneMenu();
				MainMenu.showAll();
				
				
				

				
			}
		}).start();
		
	}
	
}


 
 
 	