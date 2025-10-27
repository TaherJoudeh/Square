package game;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.imageio.ImageIO;

public class Customize {

	public static LinkedHashMap <Integer, BufferedImage> skins = new LinkedHashMap <Integer, BufferedImage>();
	public static LinkedHashMap <Integer, Integer> price = new LinkedHashMap <Integer, Integer> ();
	
	public Customize() {
		
		try {
			skins.put(0, ImageIO.read(new File("Data\\Customize\\Blue.png")));
			skins.put(1, ImageIO.read(new File("Data\\Customize\\Red.png")));
			skins.put(3, ImageIO.read(new File("Data\\Customize\\Camo.png")));
			skins.put(12, ImageIO.read(new File("Data\\Customize\\Illution.png")));
			skins.put(20, ImageIO.read(new File("Data\\Customize\\Illution2.PNG")));
			skins.put(2, ImageIO.read(new File("Data\\Customize\\HappyBlue.png")));
			skins.put(21, ImageIO.read(new File("Data\\Customize\\Rainbow.PNG")));
			skins.put(6, ImageIO.read(new File("Data\\Customize\\Geometry.png")));
			skins.put(8, ImageIO.read(new File("Data\\Customize\\3D.png")));
			skins.put(9, ImageIO.read(new File("Data\\Customize\\TruckBoy.png")));
			skins.put(5, ImageIO.read(new File("Data\\Customize\\Microsoft.png")));
			skins.put(13, ImageIO.read(new File("Data\\Customize\\TheifMask.png")));
			skins.put(14, ImageIO.read(new File("Data\\Customize\\AngryTheifMask.png")));
			skins.put(29, ImageIO.read(new File("Data\\Customize\\Ghost.png")));
			skins.put(7, ImageIO.read(new File("Data\\Customize\\Ninja.png")));
			skins.put(4, ImageIO.read(new File("Data\\Customize\\eimoBoy.png")));
			skins.put(19, ImageIO.read(new File("Data\\Customize\\Diamond.png")));
			skins.put(24, ImageIO.read(new File("Data\\Customize\\Payday_America.PNG")));
			skins.put(10, ImageIO.read(new File("Data\\Customize\\SuperMan.png")));
			skins.put(17, ImageIO.read(new File("Data\\Customize\\Mustache.jpg")));
			skins.put(22, ImageIO.read(new File("Data\\Customize\\Capturesd.PNG")));
			skins.put(11, ImageIO.read(new File("Data\\Customize\\Batman.jpg")));
			skins.put(27, ImageIO.read(new File("Data\\Customize\\StarWars.PNG")));
			skins.put(26, ImageIO.read(new File("Data\\Customize\\Dark.PNG")));
			skins.put(16, ImageIO.read(new File("Data\\Customize\\Creeper.png")));
			skins.put(23, ImageIO.read(new File("Data\\Customize\\DamagedRobot.PNG")));
			skins.put(15, ImageIO.read(new File("Data\\Customize\\Developer.png")));
			skins.put(28, ImageIO.read(new File("Data\\Customize\\Pump.PNG")));
			skins.put(25, ImageIO.read(new File("Data\\Customize\\Monster.PNG")));
			skins.put(18, ImageIO.read(new File("Data\\Customize\\ASQ.png")));
			
			price.put(0,0);
			price.put(1,0);
			price.put(2,400);
			price.put(3,300);
			price.put(4,2500);
			price.put(5,1000);
			price.put(6,700);
			price.put(7,2000);
			price.put(8,700);
			price.put(9,750);
			price.put(10,3500);
			price.put(11,5000);
			price.put(12,350);
			price.put(13,1000);
			price.put(14,1500);
			price.put(15,7000);
			price.put(16,6000);
			price.put(17, 4000);
			price.put(18, 10000);
			price.put(19, 3000);
			price.put(20, 370);
			price.put(21, 500);
			price.put(22, 4500);
			price.put(23, 6000);
			price.put(24, 3500);
			price.put(25, 8000);
			price.put(26, 5500);
			price.put(27, 5000);
			price.put(28, 8000);
			price.put(29, 1750);
			
		} catch (IOException e) { e.printStackTrace(); }
		
	}	
	
	public static int getIndex(BufferedImage bi) {
		
		int count = 0;
		for(Map.Entry<Integer, BufferedImage> entry : skins.entrySet()) {
			
			if(bi == entry.getValue()) return count;
			count++;
			
		}
		return count;
	}
	
	public static BufferedImage getSkin(int index) {
		
		int count = 0;
		for(Map.Entry<Integer, BufferedImage> entry : skins.entrySet()) {
			if(count == index) return entry.getValue();
			count++;
		}
		return null;
	}
	
	public static int getKey(BufferedImage bi) {
		
		for(Map.Entry<Integer, BufferedImage> entry : skins.entrySet()) {
			if(bi == entry.getValue()) return entry.getKey();
		}
		
		return 0;
		
	}
	
}