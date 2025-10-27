package game;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Window {

	public static JFrame j;
	public final static int WIDTH = 1366, HEIGHT = 770;
	private Dimension W_C_Size = new Dimension(WIDTH, HEIGHT);
	
		public Window(String title, Gameplay game) {
			
			game.setPreferredSize(W_C_Size);
			game.setMinimumSize(W_C_Size);
			game.setMaximumSize(W_C_Size);

		    j = new JFrame(title);
		    try {
				j.setIconImage(ImageIO.read(new File("Data/Customize/ASQ.png")));
			} catch (IOException e) { e.printStackTrace(); }
		    
		    j.add(game);
		    j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    j.setUndecorated(false);
		    j.setExtendedState(JFrame.MAXIMIZED_BOTH);
		    j.setLocation(0, 0);
		    j.setResizable(false);
		    j.pack();
		    j.setVisible(true);
			
		    game.start();
		    
		}
}