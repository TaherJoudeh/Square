package gameInputs;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.JOptionPane;

import game.Accounts;
import game.AudioPlayer;
import game.Customize;
import game.GameObject;
import game.Gameplay;
import game.Handler;
import game.ID;
import game.STATUS;
import game.Window;

public class KeyInput extends KeyAdapter {

	private int key;
	private Handler handler;
	private GameObject player;
	private Gameplay game;
	public static boolean Id = false, Name = false, showCoinsAtGame = false, developerMODE, isCode;
	public static int showCoinsCount = 0, codeLimitInput = 0;
	public static final Rectangle NameInput = new Rectangle(500, 190, 500, 100),
			IdInput = new Rectangle(500, 380, 500, 100);
	private Random R;
	
	public static boolean isRight, isLeft, isUp, isDown;

	public KeyInput(Handler handler, Gameplay game) {
		this.handler = handler;
		this.game = game;
		R = new Random();
		Buttons.R_L.add(NameInput);
		Buttons.R_L.add(IdInput);
		
		Buttons.displayR_L.add(false);
		Buttons.displayR_L.add(false);
	}
	
	public void keyPressed(KeyEvent e) {
		key = e.getKeyCode();

		if((key == KeyEvent.VK_TAB) && (Gameplay.gameStatus == STATUS.LOGIN || Gameplay.gameStatus == STATUS.REGISTER)) {
			if(!Id && !Name) Name = true;
			else if(Id) {
				Id = false;
				Name = true;
			}else if(Name) {
				Name = false;
				Id = true;
			}
		}
		
		if(Accounts.inAccount && Keys(e.getKeyCode()) && e.getKeyCode() != KeyEvent.VK_SPACE && 
				developerMODE && Gameplay.gameStatus == STATUS.DEVELOP &&
				codeLimitInput <= 10) {
			Gameplay.code += KeyEvent.getKeyText(e.getKeyCode());
			codeLimitInput++;
		}
		
		if(key == KeyEvent.VK_ENTER && !Gameplay.pause && !Gameplay.isEND) {
			if(developerMODE) {
				if(Gameplay.gameStatus == STATUS.GAME) Gameplay.gameStatus = STATUS.DEVELOP;
				else if(Gameplay.gameStatus == STATUS.DEVELOP) {
					Gameplay.gameStatus = STATUS.GAME;
					isCode = true;
				}
			}
			if(Gameplay.gameStatus == STATUS.LOGIN) Accounts.login(Accounts.name, Accounts.id);
			else if(Gameplay.gameStatus == STATUS.REGISTER) Accounts.register(Accounts.name, Accounts.id);
		}
		
		type(e);
		
		if(key == KeyEvent.VK_ESCAPE) {
			/*if(Accounts.inAccount) Accounts.f.setWritable(true);
			if(Gameplay.gameStatus == STATUS.GAME && Gameplay.SCORE > Gameplay.highSCORE) Accounts.setNewHightScore();
			if(Gameplay.gameStatus == STATUS.GAME && !Gameplay.isEND) Accounts.setCoins((Accounts.AccountsCoins += Gameplay.SCORE/40));
			if(Accounts.inAccount) Accounts.f.setWritable(false);
			handler.getHandler().clear();
			Accounts.inAccount = false;
			Accounts.f = null;
			game.stop();*/
						
			
			if (Gameplay.gameStatus == STATUS.GAME) {
				AudioPlayer.level = AudioPlayer.themeClip.getFramePosition();
				AudioPlayer.themeClip.stop();
				Gameplay.pause = true;
			}
			int n = JOptionPane.showConfirmDialog(null, "Do you really want to exit the game?", "Confirmaion Window", JOptionPane.YES_NO_OPTION);
			
			if (n == 0)
				game.stop();
			else {
				if (Gameplay.gameStatus == STATUS.GAME) {
					AudioPlayer.playTheme(AudioPlayer.Music);
					AudioPlayer.themeClip.setFramePosition(AudioPlayer.level);
					Gameplay.pauseCount = 255;
					Gameplay.pauseTimer = 0;
				}
				Gameplay.pause = false;
			}
			
		}
		
		if(Gameplay.gameStatus == STATUS.CUSTOMIZE) {
			if(key == KeyEvent.VK_RIGHT && ((Customize.skins.size()-1)-Gameplay.ImageIndex) > 0) Gameplay.ImageIndex++;
			if(key == KeyEvent.VK_LEFT && ((Customize.skins.size()-1)-Gameplay.ImageIndex < Customize.skins.size()-1)) Gameplay.ImageIndex--;
			
			Accounts.f.setWritable(true);
			if(!developerMODE) {
				if(!Accounts.OWN_SKINS.contains(Customize.getKey(Customize.getSkin(Gameplay.ImageIndex))+"") && key == KeyEvent.VK_ENTER) {
					if(Accounts.getCoins() >= Customize.price.get(Customize.getKey(Customize.getSkin(Gameplay.ImageIndex)))) {
						int Buy = JOptionPane.showConfirmDialog(Window.j, "Are you sure to buy this skin for " + 
								Customize.price.get(Customize.getKey(Customize.getSkin(Gameplay.ImageIndex))) + "?", "Buy confirm", 0, 1);
				
				if(Buy == 0) Accounts.BuySkin(Customize.getKey(Customize.getSkin(Gameplay.ImageIndex)));					
			}else JOptionPane.showMessageDialog(Window.j, "You do not have enough coins!", "Buy Skin", 0);
			}else if(Accounts.OWN_SKINS.contains(Customize.getKey(Customize.getSkin(Gameplay.ImageIndex))+"") && key == KeyEvent.VK_ENTER) {
				Accounts.chooseSkin(Customize.getKey(Customize.getSkin(Gameplay.ImageIndex)));
				Accounts.SKIN = Accounts.getImage();
			}
		}else if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			Accounts.chooseSkin(Customize.getKey(Customize.getSkin(Gameplay.ImageIndex)));
			Accounts.SKIN = Accounts.getImage();
		}
			Accounts.f.setWritable(false);
		}
		
		if(key == KeyEvent.VK_ENTER && Gameplay.isEND) {
			reset();
			Gameplay.isEND = false;
			Gameplay.gameStatus = STATUS.SELECT;
		}
		if(key == KeyEvent.VK_P && Gameplay.KM == Key_Mouse.Key && Gameplay.gameStatus == STATUS.GAME) {
			if(!Gameplay.pause) {
				AudioPlayer.level = AudioPlayer.themeClip.getFramePosition();
				AudioPlayer.themeClip.stop();
				AudioPlayer.playSound("pauseIn");
				Gameplay.pause = true;
			}
			else if(Gameplay.pause) {
				AudioPlayer.playSound("pauseOut");
				AudioPlayer.playTheme(AudioPlayer.Music);
				AudioPlayer.themeClip.setFramePosition(AudioPlayer.level);
				Gameplay.pauseCount = 255;
				Gameplay.pauseTimer = 0;
				Gameplay.pause = false;
			}
		}
		
		if(Gameplay.KM == Key_Mouse.Key && Gameplay.gameStatus == STATUS.GAME && 
				!Gameplay.pause && !Gameplay.isEND) {
			
			/*
			if((key == KeyEvent.VK_UP && key == KeyEvent.VK_RIGHT)
					|| (key == KeyEvent.VK_W && key == KeyEvent.VK_D)) {
				handler.getGameObject(0).setVelX(5);
				handler.getGameObject(0).setVelY(-5);
			}
			
			if((key == KeyEvent.VK_UP && key == KeyEvent.VK_LEFT)
					|| (key == KeyEvent.VK_W && key == KeyEvent.VK_A)) {
				handler.getGameObject(0).setVelX(-5);
				handler.getGameObject(0).setVelY(-5);
			}
			
			if((key == KeyEvent.VK_DOWN && key == KeyEvent.VK_RIGHT)
					|| (key == KeyEvent.VK_S && key == KeyEvent.VK_D)) {
				handler.getGameObject(0).setVelX(5);
				handler.getGameObject(0).setVelY(5);
			}
			
			if((key == KeyEvent.VK_DOWN && key == KeyEvent.VK_LEFT)
					|| (key == KeyEvent.VK_S && key == KeyEvent.VK_A)) {
				handler.getGameObject(0).setVelX(-5);
				handler.getGameObject(0).setVelY(5);
			}
			*/
			
			if(key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) isRight = true; //handler.getGameObject(0).setVelX(10);
			if(key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) isLeft = true; //handler.getGameObject(0).setVelX(-10);
			if(key == KeyEvent.VK_UP || key == KeyEvent.VK_W) isUp = true; //handler.getGameObject(0).setVelY(-7);
			if(key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) isDown = true; //handler.getGameObject(0).setVelY(7);
			
		}
	}
	
	public void keyReleased(KeyEvent e) {
		key = e.getKeyCode();
				
		if(Gameplay.gameStatus == STATUS.GAME) {
			if(key == KeyEvent.VK_C) {
				showCoinsCount = 0;
				showCoinsAtGame = true;
			}
		
			if(key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) isLeft = false;
			if(key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) isRight = false; 
			if(key == KeyEvent.VK_UP || key == KeyEvent.VK_W) isUp = false; 
			if(key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) isDown = false;
			
		}
	}

	public void keyTyped(KeyEvent e) { /*Nothing*/ }


	public void reset() {
		
		handler.getHandler().clear();
		player = new GameObject(Window.WIDTH/2-16, Window.HEIGHT-40, 32, 32, 0, 0, ID.Player, handler);
		handler.addGameObject(player);
	
		Gameplay.MI = new MouseInput(handler, game);
		game.addMouseMotionListener(Gameplay.MI);

		Gameplay.KM = null;
		Gameplay.HS = Accounts.getHightScore(); Gameplay.count = 0; Gameplay.SCORE = 0; Gameplay.increaseSpeed = 5; Gameplay.SpawnTime = 12;
		Gameplay.skinCount = 0;
		Gameplay.coinCount = 0;
		Gameplay.coinPlus = 0;
		Gameplay.background = Color.DARK_GRAY;Gameplay.enemies = Color.BLACK;
		Gameplay.endOperation = true;
		AudioPlayer.Music = "Theme" + R.nextInt(AudioPlayer.getMusicsNumber());
		Gameplay.highSCORE = Accounts.getHightScore();
		AudioPlayer.playTheme("Background");
		
	}
	
	private void backSpace() {
		if(Gameplay.gameStatus == STATUS.DEVELOP && !Gameplay.code.equals("")) {
			Gameplay.code = Gameplay.code.substring(0, Gameplay.code.length()-1);
			codeLimitInput--;
		}else if(KeyInput.Name) {
		if(Accounts.LimName == 0) return;
		Accounts.name = Accounts.name.substring(0, Accounts.name.length()-1);
		Accounts.LimName--;
		}else if(KeyInput.Id) {
			if(Accounts.LimId == 0) return;
			Accounts.id = Accounts.id.substring(0, Accounts.id.length()-1);
			Accounts.idTEXT = Accounts.idTEXT.substring(0,Accounts.idTEXT.length()-1);
			Accounts.LimId--;
		}
	}
	
	private void type(KeyEvent e) {
		
		key = e.getKeyCode();
		
		if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE) backSpace();
			
		if(KeyInput.Name) {
			
			if(Keys(key) && Accounts.LimName <= 16) {
				 if(key == KeyEvent.VK_SPACE) {
					Accounts.name += " ";
					Accounts.LimName++;
				} else if(Gameplay.gameStatus != STATUS.SELECT && 
						Gameplay.gameStatus != STATUS.GAME) {
					Accounts.name += KeyEvent.getKeyText(e.getKeyCode());
					Accounts.LimName++;
				}
					}
			
		}else if(KeyInput.Id) {
		
			if(Keys(key) && Accounts.LimId <= 16) {
				 if(key == KeyEvent.VK_SPACE) {
					Accounts.id += " ";
					Accounts.idTEXT += "*";
					Accounts.LimId++;
				} else if(Gameplay.gameStatus != STATUS.SELECT && 
						Gameplay.gameStatus != STATUS.GAME) {
					Accounts.id += KeyEvent.getKeyText(e.getKeyCode());
					Accounts.idTEXT += "*";
					Accounts.LimId++;
				}
					}
			
		}
		
	}
	
	private boolean Keys(int key) {
		
		if(key >= 48 && key <= 57) return true;
		if(key >= 65 && key <= 90) return true;
		if(key == 32) return true;
		return false;
		
	}
}
