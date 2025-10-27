package game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Images {
	
	private Random R;
	public static BufferedImage 
	keyboard, 
	mouse, 
	skin1, 
	skin2, 
	rightArrow, 
	leftArrow, 
	pauseImg,
	cursorImg,
	coinImg,
	coinImg_16,
	power_Boost,
	power_Invisible,
	power_autoGun,
	Bullet;
	
	public void load() {
		
		R = new Random();
		
		try {
			
		skin1 = Customize.skins.get(R.nextInt(Customize.skins.size()));
		skin2 = Customize.skins.get(R.nextInt(Customize.skins.size()));
		cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		keyboard = (ImageIO.read(new File("Data\\Keyboard.png")));
		mouse = (ImageIO.read(new File("Data\\Mouse.png")));
		coinImg = (ImageIO.read(new File("Data\\Coin.png")));
		rightArrow = (ImageIO.read(new File("Data\\rightArrow.png")));
		leftArrow = (ImageIO.read(new File("Data\\leftArrow.png")));
		coinImg_16 = (ImageIO.read(new File("Data\\Coin_16px.png")));
		pauseImg = (ImageIO.read(new File("Data\\Pause.png")));
		power_Boost = (ImageIO.read(new File("Data\\Powers\\Boost.png")));
		power_Invisible = (ImageIO.read(new File("Data\\Powers\\Invisible.png")));
		power_autoGun = (ImageIO.read(new File("Data\\Powers\\autoGun.png")));
		Bullet = (ImageIO.read(new File("Data\\Powers\\Bullet.png")));
		
		}catch(IOException e) { e.printStackTrace(); }
		
	}
	
	public static BufferedImage rotateImage(Graphics2D g) {
		
		return null;
		
	}
	
}
