package game;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioPlayer {
	
	public static Map <String, File> audioFiles = new HashMap <String, File> ();
	
	private static AudioInputStream ais;
	public static Clip clip;
	
	private static AudioInputStream themeAis;
	public static Clip themeClip; 
	
	public static int level, length;
	public static String Music;
	private Random R;
	
	public void load() {
		
		audioFiles.put("Click", new File("Data\\Audio\\click.wav"));
		audioFiles.put("Theme0", new File("Data\\Audio\\Square_Music\\music1.wav"));
		audioFiles.put("Theme1", new File("Data\\Audio\\Square_Music\\music2.wav"));
		audioFiles.put("Theme3", new File("Data\\Audio\\Square_Music\\music4.wav"));
		audioFiles.put("Theme4", new File("Data\\Audio\\Square_Music\\music5.wav"));
		audioFiles.put("Theme5", new File("Data\\Audio\\Square_Music\\music6.wav"));
		audioFiles.put("autoGun", new File("Data\\Audio\\autoGun.wav"));
		audioFiles.put("Collapse", new File("Data\\Audio\\Collapse.wav"));
		audioFiles.put("pauseIn", new File("Data\\Audio\\pauseIN.wav"));
		audioFiles.put("pauseOut", new File("Data\\Audio\\pauseOUT.wav"));
		audioFiles.put("Theme2", new File("Data\\Audio\\Square_Music\\music3.wav"));
		audioFiles.put("Boost", new File("Data\\Audio\\Boost.wav"));
		audioFiles.put("Coin", new File("Data\\Audio\\Coin.wav"));
		audioFiles.put("Invisible", new File("Data\\Audio\\Invisible.wav"));
		audioFiles.put("Background", new File("Data\\Audio\\Background.wav"));
		audioFiles.put("Theme6", new File("Data\\Audio\\Square_Music\\music7.wav"));
		
		R = new Random();
		Music = "Theme" + R.nextInt(getMusicsNumber());
		
	}
	
	public synchronized static void playSound(String key) {
			
		try {
			
		ais = AudioSystem.getAudioInputStream(audioFiles.get(key));
		AudioFormat af = ais.getFormat();
		
		DataLine.Info info = new DataLine.Info(Clip.class, af);

		clip = (Clip) AudioSystem.getLine(info);
		
		clip.open(ais);
		clip.start();
		FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		control.setValue(-1f);
		
		}catch(IOException | 
				UnsupportedAudioFileException | 
				LineUnavailableException e) { e.printStackTrace(); }
		
  }
	
	public synchronized static void playTheme(String key) {
		
		try {
			
		themeAis = AudioSystem.getAudioInputStream(audioFiles.get(key));
		AudioFormat af = themeAis.getFormat();
		
		DataLine.Info info = new DataLine.Info(Clip.class, af);

		themeClip = (Clip) AudioSystem.getLine(info);
		
		themeClip.open(themeAis);
		themeClip.loop(Clip.LOOP_CONTINUOUSLY);

		}catch(IOException | 
				UnsupportedAudioFileException | 
				LineUnavailableException e) { e.printStackTrace(); }
		
	}
	
	public static int getMusicsNumber() {
		File f = new File("Data\\Audio\\Square_Music");
		File [] musics = f.listFiles();
		return musics.length;
	}
	
	public synchronized static void closeClip() {

		themeClip.flush();
		themeClip.close();
		
	}
	
}