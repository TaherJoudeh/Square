package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;
import gameInputs.KeyInput;

public class Power {
	
	private static Handler handler;
	private static Random R;
	
	public static boolean havePower;
	
	//Boost
	private static int Boost_Timer, Boost_Delay;
	public static boolean haveBoost;
	public static int Boost_spawnTime;
	
	//Invisible
	private static int Invisible_Timer, Invisible_Delay;
	public static boolean haveInvisible;
	public static int Invisible_spawnTime;
	
	//Auto_Gun
	private static int autoGun_Timer, autoGun_Delay;
	public static boolean haveautoGun;
	public static int autoGun_spawnTime;
	
	private static Color c1, c2;
	
	public Power(Handler handler) {
		
		Power.handler = handler;
		R = new Random();
		
		Boost_spawnTime = R.nextInt(500) + 1000;
		Invisible_spawnTime = R.nextInt(1700) + 1500;
		autoGun_spawnTime = R.nextInt(2000) + 1700;
		
	}
	
	private static void havePower() {
		
		if(Gameplay.isEND && !havePower) {
			Invisible_Delay = 0;
			Invisible_Timer = 0;
			haveInvisible = false;
			
			autoGun_Delay = 0;
			autoGun_Timer = 0;
			haveautoGun = false;
			
			Boost_Delay = 0;
			Boost_Timer = 0;
			haveBoost = false;
			
			havePower = false;
			return;
		}
	
		GameObject power = null;
		for(int i = 0; i < handler.size(); i++) {
			if(handler.getGameObject(i).getID() != ID.Enemy && handler.getGameObject(i).getID() != ID.movingEnemy &&
					handler.getGameObject(i).getID() != ID.Player) {
				power = handler.getGameObject(i);
				break;
			}
		}
		
		if(power == null) return;
		if(handler.getGameObject(0).getBounds().intersects(power.getBounds())) {
			
			handler.removeGameObject(power);
			havePower = true;
			
			if(power.getID() == ID.Power_Boost) {
			
				c1 = Gameplay.background;
				c2 = Gameplay.enemies;
				Gameplay.invisible = true;
				haveBoost = true;
				
			}else if(power.getID() == ID.Power_autoGun) haveautoGun = true;
			else if(power.getID() == ID.Power_Invisble) {
				haveInvisible = true;
				AudioPlayer.playSound("Invisible");
			}
		}
	}
	
	public static void Boost_tick() {
		
		havePower();
		if(haveBoost) {
			
		if(Boost_Delay%27 == 0) Boost_Timer++;
		if(Boost_Delay%9 == 0) AudioPlayer.playSound("Boost");
		if(Boost_Timer < 10) {
			Boost_Delay++;
			Gameplay.SCORE += 5;
			int x = Boost_Delay - Boost_Timer;
			for(int i = 1; i < handler.size(); i++) {
				
				if(handler.getGameObject(i).getID() == ID.Enemy) handler.getGameObject(i).setVelY(x+50);
				
			}
		}else {
			Boost_Delay = 0;
			Boost_Timer = 0;
			Gameplay.count = 0;
			if(!KeyInput.developerMODE) Gameplay.invisible = false;
			handler.OnlyPlayer(1);
			haveBoost = false;
			havePower = false;
			Gameplay.background = c1;
			Gameplay.enemies = c2;
			Gameplay.DarkBright = false;
			Gameplay.increaseSpeed++;
			Gameplay.SpawnTime--;
			return;
		}
	}
  }
	public static void Boost_render(Graphics g) {
		
		if(haveBoost) {
			
			if(Boost_Timer%3 == 0) {
				
				Gameplay.background = new Color(R.nextInt(255),R.nextInt(255),R.nextInt(255));
				Gameplay.enemies = new Color(R.nextInt(255),R.nextInt(255),R.nextInt(255));
				
			}
			
			g.setColor(new Color(255, 0, 0, 250));
			g.setFont(new Font("Arial", Font.BOLD, 25));
			g.drawString(10 - Boost_Timer + "", Window.WIDTH/2-15, Window.HEIGHT/2-10);
		}
		
	}

	public static void Invisible_tick() {
		
		havePower();
		if(haveInvisible) {
			
			Gameplay.invisible = true;
			if(Invisible_Delay%27 == 0) Invisible_Timer++;
			if(Invisible_Timer < 10) {
				Invisible_Delay++;
			}else {
				Gameplay.invisible = false;
				Invisible_Delay = 0;
				Invisible_Timer= 0;
				haveInvisible = false;
				havePower = false;
			}
		}
		
	}
	
	public static void Invisible_render(Graphics g) {
		
		if(haveInvisible && Invisible_Timer >= 5) {
			
			g.setColor(new Color(255, 0, 0, 250));
			g.setFont(new Font("Arial", Font.BOLD, 25));
			g.drawString(10 - Invisible_Timer + "", Window.WIDTH/2-15, Window.HEIGHT/2-10);
			
		}
		
	}
	
	public synchronized static void autoGun_tick() {
		
		havePower();
		if(haveautoGun) {
			
			if(autoGun_Delay%4 == 0) autoGun_Timer++;
			if(autoGun_Delay%3 == 0) AudioPlayer.playSound("autoGun");

			if(autoGun_Timer%2 == 0) {				
				handler.addGameObject(new GameObject(
					handler.getGameObject(0).getX()+8, handler.getGameObject(0).getY()-10, 16, 16, 0, -13, ID.Bullet, handler));
				handler.addGameObject(new GameObject(handler.getGameObject(0).getX()-8, handler.getGameObject(0).getY()-8, 16, 17 , -3, -13, ID.Bullet, handler));
				handler.addGameObject(new GameObject(handler.getGameObject(0).getX()+(handler.getGameObject(0).getWidth()/2)+8, handler.getGameObject(0).getY()-8, 16, 16, 3, -13, ID.Bullet, handler));
			}
			if(autoGun_Timer < 20) autoGun_Delay++;
			else {
			
				autoGun_Delay = 0;
				autoGun_Timer = 0;
				haveautoGun = false;
				havePower = false;
				
			}
		}
		
	}
	
	public static void autoGun_render(Graphics g) {
		
		if(haveautoGun) {
			
			g.setColor(new Color(255, 0, 0, 250));
			g.setFont(new Font("Arial", Font.BOLD, 25));
			g.drawString(10 - (autoGun_Timer/2) + "", Window.WIDTH/2-15, Window.HEIGHT/2-10);
			
		}
		
	}
	
}