package gameInputs;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import game.Accounts;
import game.AudioPlayer;
import game.Customize;
import game.GameObject;
import game.Gameplay;
import game.Handler;
import game.STATUS;
import game.Window;

public class MouseInput extends MouseAdapter {

	private Handler handler;
	private GameObject player;
	private Robot robot;
	private Gameplay game;
	
	public static final Rectangle Keyboard = new Rectangle(475-210, Window.HEIGHT/2-120, 300, 200), Mouse = new Rectangle(475+300, Window.HEIGHT/2-120, 300, 200);
	
	public MouseInput(Handler handler, Gameplay game) {
		
		this.handler = handler;
		this.game = game;
		player = handler.getGameObject(0);
		Buttons.select.add(Keyboard);
		Buttons.select.add(Mouse);
		Buttons.displayS.add(false);
		Buttons.displayS.add(false);
		try {
			robot = new Robot();
		} catch (AWTException e) { e.printStackTrace(); }
		
	}

	public void mouseReleased(MouseEvent e) {
		
		if(SwingUtilities.isLeftMouseButton(e)) {
		if(Gameplay.gameStatus == STATUS.SELECT) {
			for(int i = 0; i < Buttons.select.size(); i++) {
				Rectangle r = Buttons.select.get(i);
				
				if(isMousing(r, e)) {
				if(Accounts.inAccount) {
					if(r == Accounts.customize1 || r == Accounts.customize2) {
						AudioPlayer.playSound("Click");
						Gameplay.gameStatus = STATUS.CUSTOMIZE;
						KeyInput.Name = false;
						KeyInput.Id = false;
						Gameplay.stopDisplay();
					}
					if(r == MouseInput.Keyboard) {
						AudioPlayer.closeClip();
						handler.OnlyPlayer(1);
						Gameplay.KM = Key_Mouse.Key;
						Gameplay.gameStatus = STATUS.GAME;
						AudioPlayer.playTheme(AudioPlayer.Music);
					}
					if(r == MouseInput.Mouse) {
						AudioPlayer.closeClip();
						robot.mouseMove(Window.j.getX() + handler.getGameObject(0).getX()
								+handler.getGameObject(0).getWidth()/2, Window.j.getY() + handler.getGameObject(0).getY()
								+handler.getGameObject(0).getHeight()/2);
						handler.OnlyPlayer(1);
						Gameplay.KM = Key_Mouse.Mouse;
						Gameplay.gameStatus = STATUS.GAME;
						AudioPlayer.playTheme(AudioPlayer.Music);
					}
					
					if(r == Accounts.LogOut) {
						AudioPlayer.playSound("Click");
						Accounts.logout();
					}
				}else {
					if(r == MouseInput.Keyboard
							|| r == MouseInput.Mouse) {
						AudioPlayer.playSound("Click");
						JOptionPane.showMessageDialog(Window.j, "You must LOGIN before!", "Account ERROR!", 0);
					}
					
					if(r == Accounts.Register) {
						AudioPlayer.playSound("Click");
						Gameplay.gameStatus = STATUS.REGISTER;
						KeyInput.Id = false;
						KeyInput.Name = false;
						Gameplay.stopDisplay();
					}
					if(r == Accounts.Login) {
						AudioPlayer.playSound("Click");
						Gameplay.gameStatus = STATUS.LOGIN;
						KeyInput.Id = false;
						KeyInput.Name = false;
						Gameplay.stopDisplay();
					}
				}
				return;
			}
		}
			}else if(Gameplay.gameStatus == STATUS.LOGIN || Gameplay.gameStatus == STATUS.REGISTER || Gameplay.gameStatus == STATUS.CUSTOMIZE) {
				for(int i = 0; i < Buttons.R_L.size(); i++) {
					Rectangle r = Buttons.R_L.get(i);
					
					if(isMousing(r, e)) {
						if(Gameplay.gameStatus == STATUS.LOGIN || Gameplay.gameStatus == STATUS.REGISTER) {
						if(r == KeyInput.IdInput) {
							AudioPlayer.playSound("Click");
							KeyInput.Id = true;
							KeyInput.Name = false;
						} else if(r == KeyInput.NameInput) {
							AudioPlayer.playSound("Click");
							KeyInput.Name = true;
							KeyInput.Id = false;
						}else {
							KeyInput.Name = false;
							KeyInput.Id = false;
						}
						}
						if(r == Buttons.Back) {
							AudioPlayer.playSound("Click");
							Accounts.name = "";
							Accounts.id = "";
							Accounts.idTEXT = "";
							Accounts.LimId = 0;
							Accounts.LimName = 0;
							KeyInput.Id = false;
							KeyInput.Name = false;
							if(Gameplay.gameStatus == STATUS.CUSTOMIZE) Gameplay.ImageIndex = Customize.getIndex(Accounts.getImage());
							Gameplay.gameStatus = STATUS.SELECT;
						}
						
						if(r == Accounts.RL_Apply) {
							AudioPlayer.playSound("Click");
							switch(Gameplay.gameStatus) {
							case LOGIN:
								Accounts.login(Accounts.name, Accounts.id);
								break;
							case REGISTER:
								Accounts.register(Accounts.name, Accounts.id);
								break;
							default:
								break;
							}
						  }
						}
					}
			}	
				}
		}
		
	public void mouseMoved(MouseEvent e) {
		
	  if(!Gameplay.isEND && Gameplay.gameStatus == STATUS.GAME && Gameplay.KM == Key_Mouse.Mouse) {  
		player.setX(e.getX()-16);
		player.setY(e.getY()-16);
	  }else {

		  if(Gameplay.gameStatus == STATUS.SELECT) {
			  
			  for(int i = 0; i < Buttons.select.size(); i++) {
				  Rectangle r = Buttons.select.get(i);
				  
				  if(isMousing(r, e)) {

					  if(r == MouseInput.Keyboard) Buttons.displayS.set(i, true);
					  if(r == MouseInput.Mouse) Buttons.displayS.set(i, true);
					  
					  if(Accounts.inAccount) {
						  if(r == Accounts.LogOut) Buttons.displayS.set(i, true);
						  
						  else if(r == Accounts.customize1) {
							  Buttons.displayS.set(i, true);
							  Buttons.displayS.set(i+1, true);
							  break;
						  }else if(r == Accounts.customize2) {
							  Buttons.displayS.set(i, true);
							  Buttons.displayS.set(i-1, true);
						  }
					  }else {
						 if(r == Accounts.Login) Buttons.displayS.set(i, true);
						 if(r == Accounts.Register) Buttons.displayS.set(i, true);
					  }
					  
				  }else {
					  Buttons.displayS.set(i , false);
				  }
				  
			  }
			  
		  }else if(Gameplay.gameStatus == STATUS.LOGIN || Gameplay.gameStatus == STATUS.REGISTER || Gameplay.gameStatus == STATUS.CUSTOMIZE) {
			  for(int i = 0; i < Buttons.R_L.size(); i++) {
				  Rectangle r = Buttons.R_L.get(i);

				  if(isMousing(r, e)) {
					  if(Gameplay.gameStatus == STATUS.CUSTOMIZE) {
					  if(r == Buttons.Back) Buttons.displayR_L.set(i, true);
					  else Buttons.displayR_L.set(i, false);
				  }else Buttons.displayR_L.set(i, true);
			  }else Buttons.displayR_L.set(i, false);
		}
	}
  }
}	
	public boolean isMousing(Rectangle r, MouseEvent e) {
		
		if((e.getX() >= r.x && e.getX() <= r.x+r.width) && (e.getY() >= r.y && e.getY() <= r.y+r.height)) return true;
		return false;
		
	}
}