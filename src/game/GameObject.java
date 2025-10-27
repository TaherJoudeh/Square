package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import gameInputs.KeyInput;

public class GameObject {
	
	private int x, y, width, height, VelX, VelY;
	private ID id;
	private Handler handler;
	
	public static GameObject crash;
	
	public GameObject(int x, int y, int width, int height, int VelX, int VelY, ID id, Handler handler) {
		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.VelX = VelX;
		this.VelY = VelY;
		this.id = id;
		this.handler = handler;
		
	}

	public int getX() { return x; }
	public void setX(int x) { this.x = x; }

	public int getY() { return y; }
	public void setY(int y) { this.y = y; }

	public int getWidth() { return width; }
	public void setWidth(int width) { this.width = width; }

	public int getHeight() { return height; }
	public void setHeight(int height) { this.height = height; }

	public int getVelX() { return VelX; }
	public void setVelX(int velX) { VelX = velX; }

	public int getVelY() { return VelY; }
	public void setVelY(int velY) { VelY = velY; }
	
	public ID getID() { return id; }
	public void setID(ID id) { this.id = id; }
	
	public Rectangle getBounds() { return (new Rectangle(x, y, width, height)); }
	
	public void tick() {
		
		y += VelY;
		x += VelX;
		
		if(id == ID.Bullet) {
			
			for(int i = 0; i < handler.size(); i++) {
				if(handler.getGameObject(i).getID() == ID.Enemy ||
						handler.getGameObject(i).getID() == ID.movingEnemy) {
					if(getBounds().intersects(handler.getGameObject(i).getBounds())) {
					handler.getHandler().remove(i);
					handler.removeGameObject(this);
					}
				}
			}
			
		}
		
		if(id == ID.Player) {
			
			if (KeyInput.isRight) VelX = 10;
			else if (!KeyInput.isLeft) VelX = 0;
			
			if (KeyInput.isLeft) VelX = -10;
			else if (!KeyInput.isRight) VelX = 0;
			
			if (KeyInput.isUp) VelY = -7;
			else if (!KeyInput.isDown) VelY = 0;
			
			if (KeyInput.isDown) VelY = 7;
			else if (!KeyInput.isUp) VelY = 0;
			
			if(x <= 0) x = 0;
			if(x+width >= Window.WIDTH) x = Window.WIDTH-width;
			if(y <= 0) y = 0;
			if(y+height >= Window.HEIGHT) y = Window.HEIGHT-height;
		}else if(id == ID.movingEnemy) {
			
			if(x+width > Window.WIDTH) VelX = -VelX;
			if(x < 0) VelX = -VelX;
			
		}
	}
	
	public void render(Graphics g) {
		
		if(id == ID.Enemy || id == ID.movingEnemy){
			if(Gameplay.invisible) {
				Color invColor = Gameplay.enemies;
				g.setColor(new Color(invColor.getRed(), invColor.getBlue(),
						invColor.getGreen(), 127));
			}
			else g.setColor(Gameplay.enemies);
			g.fillRect(x, y, width, height);
		}
		
		if(id == ID.Player) {
				g.drawImage(Accounts.SKIN, x, y, null);
			if(Gameplay.invisible) {
				g.setColor(new Color(106, 106, 106, 127));
				g.fillRect(x, y, width, height);
			}
		}
		if(id == ID.Power_autoGun) {
			g.drawImage(Images.power_autoGun, x, y, null);
		}
		if(id == ID.Bullet) {
				g.drawImage(Images.Bullet, x, y, null);
			}if(id == ID.Power_Boost) {
			g.drawImage(Images.power_Boost, x, y, null);
		}
			if(id == ID.Power_Invisble) {
			g.drawImage(Images.power_Invisible, x, y, null);
		} 
  }
}