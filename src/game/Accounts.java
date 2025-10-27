package game;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;
import gameInputs.Buttons;
import gameInputs.KeyInput;
import gameInputs.MouseInput;

public class Accounts {

	public static final Rectangle 
			Login = new Rectangle(Window.WIDTH/2-200, 600, 400, 40),
			Register = new Rectangle(Window.WIDTH/2-200, 643, 400, 40),
			RL_Apply = new Rectangle(Window.WIDTH/2-125, Window.HEIGHT-90, 250, 70),
			LogOut = new Rectangle(0, Window.HEIGHT-33, Window.WIDTH-1, 30),
			customize1 = new Rectangle(Window.WIDTH / 2 - 240, 100, 32, 32),
			customize2 = new Rectangle(Window.WIDTH / 2 + 213, 100, 32, 32);
	
	public static int LimId, LimName;
	public static String name = "", id = "";
	public static int SkinNum;
	public static String idTEXT = "";
	private File accounts;
	private static String path;
	public static File f;
	private static Random R;
	private static ArrayList <BufferedImage> ownSkins;
	public static String AccountName = "", OWN_SKINS = "";
	public static int AccountsCoins;
	public static BufferedImage SKIN;
	
	public static boolean inAccount = false;
	
	public Accounts() {
		
		Buttons.select.add(Login);
		Buttons.select.add(Register);
		Buttons.R_L.add(RL_Apply);
		Buttons.R_L.add(Buttons.Back);
		Buttons.select.add(LogOut);
		Buttons.select.add(customize1);
		Buttons.select.add(customize2);
	
		Buttons.displayS.add(false);
		Buttons.displayS.add(false);
		Buttons.displayR_L.add(false);
		Buttons.displayR_L.add(false);
		Buttons.displayS.add(false);
		Buttons.displayS.add(false);
		Buttons.displayS.add(false);
		Buttons.displayS.add(false);
		
		accounts = new File("Data/Accounts");
		path = accounts.getPath();
		R = new Random();

	}
	
	public static boolean register(String username, String ID) {
		
		File file = new File(path + "/" + username + ".txt");

		if(username.equals("") || LimId < 5)
			JOptionPane.showMessageDialog(Window.j, "NAME Should not be empty, ID should be more than 5 characters!",
				"Account ERROR!", 2);
			
		else if(file.exists() && !username.equals("")) {
				JOptionPane.showMessageDialog(Window.j, "!", "Account ERROR!", 0);
				name = "";
				id = "";
				idTEXT = "";
			    LimId = 0;
				LimName = 0;
				KeyInput.Name = false;
				KeyInput.Id = false;
				Gameplay.stopDisplay();
				return false;
			}else if(!username.equals("") && LimId >= 5) {
		
		try {
			
			PrintWriter pw = new PrintWriter(file);
			pw.print("ID:" + ID + "-");
			pw.print("hightscore:0-");
			pw.print("coins:0-");
			pw.print("[0,1]-");
			pw.print(0);
			pw.close();
			
			file.setWritable(false);
			file.setReadOnly();
			
			name = "";
			id = "";
			idTEXT = "";
			LimId = 0;
			LimName = 0;
			KeyInput.Name = false;
			KeyInput.Id = false;
			JOptionPane.showMessageDialog(Window.j, "Registration Successfully", "Register", 1);
			Gameplay.gameStatus = STATUS.SELECT;
			Gameplay.stopDisplay();
			
		} catch (FileNotFoundException e) { e.printStackTrace(); }
			}
		return true;
	}
	
	public static boolean login(String username, String ID) {
		
		File file = new File(path + "/" + username + ".txt");
		
		if(username.equalsIgnoreCase("DEVELOPER") && ID.equalsIgnoreCase("DEVELOPER")) {
			f = new File("Data/Accounts/DEVELOPER.txt");
			KeyInput.developerMODE = true;
		}
		else if(username.equalsIgnoreCase("King") && ID.equalsIgnoreCase("Fuck School")) {
			f = new File("Data/Accounts/King.txt");
			KeyInput.developerMODE = true;
		}
		if(KeyInput.developerMODE) {
			inAccount = true;
			Gameplay.HS = getHightScore();
			Gameplay.highSCORE = getHightScore();
			JOptionPane.showMessageDialog(Window.j, "Login Successfully", "Login", 1);
			name = "";
			id = "";
			idTEXT = "";
			LimId = 0;
			LimName = 0;
			KeyInput.Name = false;
			KeyInput.Id = false;
			getOwnSkins();
			AccountsCoins = getCoins();
			AccountName = Accounts.f.getName().substring(0, Accounts.f.getName().lastIndexOf("."));
			Gameplay.ImageIndex = Customize.getIndex(getImage());
			SKIN = getImage();
			Gameplay.gameStatus = STATUS.SELECT;
			Gameplay.stopDisplay();
			return true; 
		}
		if(username.equals("")) JOptionPane.showMessageDialog(Window.j, "Uncorrect NAME or ID!",
				"Account ERROR!", 2);
		
		else if(file.exists()) {
		
			f = file;
			
			if(ID.equals(getId())) {
			inAccount = true;
			Gameplay.HS = getHightScore();
			Gameplay.highSCORE = getHightScore();
			JOptionPane.showMessageDialog(Window.j, "Login Successfully", "Login", 1);
			name = "";
			id = "";
			idTEXT = "";
			LimId = 0;
			LimName = 0;
			KeyInput.Name = false;
			KeyInput.Id = false;
			getOwnSkins();
			AccountsCoins = getCoins();
			AccountName = Accounts.f.getName().substring(0, Accounts.f.getName().lastIndexOf("."));
			Gameplay.ImageIndex = Customize.getIndex(getImage());
			SKIN = getImage();
			Gameplay.gameStatus = STATUS.SELECT;
			Gameplay.stopDisplay();
			
			return true;			
			}else JOptionPane.showMessageDialog(Window.j, "Uncorrect NAME or ID!", "Account ERROR!", 0);
		}else {
			JOptionPane.showMessageDialog(Window.j, "Uncorrect NAME or ID!", "Account ERROR!", 0);
		}
	
		return false;
	}
	
	public static void logout() {
		
		int cd = JOptionPane.showConfirmDialog(Window.j, "Logout?","Confirm",2);

		if(cd == 0) {
			inAccount = false;
			KeyInput.developerMODE = false;
			AccountName = "";
			OWN_SKINS = "";
			KeyInput.developerMODE = false;
			ownSkins.clear();
			f = null;
			Gameplay.stopDisplay();
	}
}
	

	public static void setNewHightScore() {
				
		if(Accounts.f == null || !inAccount) return;
		
		try {
			
			BufferedReader br = new BufferedReader(new FileReader(Accounts.f));
			String line;
			String text = "";
			String txt = "";
			
			while((line=br.readLine()) != null) text += line;

			String [] strs = text.split("-");
			String [] sCore = strs[1].split(":");
			sCore[1] = Gameplay.HS + "";
			
			txt = strs[0] + "-" + sCore[0] + ":" + sCore[1] + "-" + strs[2] + "-" + strs[3] + "-" + strs[4];
			
		PrintWriter pw = new PrintWriter(Accounts.f);
		pw.print(txt);
		pw.close();
		
		}catch(IOException e) { e.printStackTrace(); }
	}

	public static int getHightScore() {
		
		int hightScore = 0;
		
		if(Accounts.f == null || !inAccount) return hightScore;
		
		try {
			
			BufferedReader br = new BufferedReader(new FileReader(Accounts.f));
			String line; 
			String text = "";
			while((line=br.readLine()) != null) text += line;
			
			String [] strs = text.split("-");
			String [] sCore = strs[1].split(":");
			hightScore = Integer.parseInt(sCore[1]);
			
			br.close();
			}catch(IOException e) { e.printStackTrace(); }
		
		return hightScore;
		
	}
	
	public static String getId() {
		
		String id = "";
				
		try {
		
		BufferedReader br = new BufferedReader(new FileReader(f));
		String line; 
		String text = "";
		while((line=br.readLine()) != null) text += line;
		
		String [] strs = text.split("-");
		String [] ID = strs[0].split(":");
		id = ID[1];
		
		br.close();
		}catch(IOException e) { e.printStackTrace(); }
		
		return id;
		
	}
	
	public static int getCoins()  {
		
		int coins = 0;
		
		if(Accounts.f == null || !inAccount) return coins;
		
		try {
			BufferedReader bf = new BufferedReader(new FileReader(f));
			
			String text;
			String txt = "";
			while((text = bf.readLine()) != null) txt += text;
			
			String [] strs = txt.split("-");
			String [] COIN = strs[2].split(":");
			coins = Integer.parseInt(COIN[1]);
			
		} catch (IOException e) { e.printStackTrace(); }
		
		return coins;
		
	}
	
	public static void setCoins(int coins) {
		
		if(f == null || !inAccount) return;
		
		try {
			
			BufferedReader br = new BufferedReader(new FileReader(Accounts.f));
			String line;
			String text = "";
			String txt = "";
			
			while((line=br.readLine()) != null) text += line;
			
			String [] strs = text.split("-");
			String [] Coins = strs[2].split(":");
			Coins[1] = coins + "";
			
			//System.out.println(Accounts.AccountsCoins);
			
			txt = strs[0] + "-" + strs[1] + "-" + Coins[0] + ":" + Coins[1] + "-" + strs[3] + "-" + strs[4];
			
		PrintWriter pw = new PrintWriter(Accounts.f);
		br.close();
		pw.print(txt);
		
		Accounts.AccountsCoins = coins;
		
		//System.out.println(Accounts.AccountsCoins + "\n-------");
		
		pw.close();
		
		}catch(IOException e) { e.printStackTrace(); }

		
	}
	
	public static int getImageIndex() {

		int index = 0;
		
		if(!inAccount || f == null) return index;
		
		try {
			
			BufferedReader bf = new BufferedReader(new FileReader(f));
			
			String text;
			String txt = "";
			while((text = bf.readLine()) != null) txt += text;
			
			String [] strs = txt.split("-");
			index = Integer.parseInt(strs[4]);
			 
		} catch (IOException e) { e.printStackTrace(); }
		return index;
	}
	
	public static BufferedImage getImage() {
		
		if(!inAccount || f == null) return Customize.skins.get(R.nextInt(Customize.skins.size()));
		BufferedImage bi = Customize.skins.get(getImageIndex());
		return bi;
		
	}
	
	public static void chooseSkin(int index) {
		
		if(!inAccount || f == null) return;
		
		try {
			
			BufferedReader bf = new BufferedReader(new FileReader(f));
			
			String text;
			String txt = "";
			while((text = bf.readLine()) != null) txt += text;
			
			String [] strs = txt.split("-");
			txt = strs[0] + "-" + strs[1] + "-" + strs[2] + "-" + strs[3] + "-" + index;
			
			PrintWriter pw = new PrintWriter(Accounts.f);
			pw.print(txt);
			pw.close(); 
			
			Gameplay.gameStatus = STATUS.SELECT;
			Gameplay.stopDisplay();
			
		} catch (IOException e) { e.printStackTrace(); }
		
	}
	
	public static ArrayList <Integer> getOwnSkins() {
		
		if(!inAccount) return null;
		
		ArrayList <Integer> OWNskins = new ArrayList <Integer> ();
		ownSkins = new ArrayList <BufferedImage> ();
		
		try {
			
			BufferedReader br = new BufferedReader(new FileReader(f));
			String text;
			String txt = "";
			while((text = br.readLine()) != null) txt += text;
			
			String [] strs = txt.split("-");
			String skins = strs[3];
			skins = skins.substring(1, skins.length()-1);
			String [] NumSkins = skins.split(",");
			
			for(int i = 0; i < NumSkins.length; i++) {
				OWNskins.add(Integer.parseInt(NumSkins[i]));
				ownSkins.add(Customize.skins.get(Integer.parseInt(NumSkins[i])));
				OWN_SKINS += NumSkins[i];
				if(i != NumSkins.length-1) OWN_SKINS += ",";
			}
			
		} catch (IOException e) { e.printStackTrace(); }
		
		return OWNskins;
	}	
	
	public static void BuySkin(int index) {
		
		try {
			
			BufferedReader bf = new BufferedReader(new FileReader(f));
			
			String text;
			String txt = "";
			while((text = bf.readLine()) != null) txt += text;
			
			String [] strs = txt.split("-");
			strs[3] = strs[3].substring(0, strs[3].length()-1) + "," + index + "]";
			
			txt = strs[0] + "-" + strs[1] + "-" + strs[2] + "-" + strs[3] + "-" + strs[4];
			PrintWriter pw = new PrintWriter(Accounts.f);
			
			pw.print(txt);
			pw.close();
		
			setCoins((AccountsCoins -= Customize.price.get(index)));
			getOwnSkins();
			JOptionPane.showMessageDialog(Window.j, "The skin has been bought!", "Buy Skin", 1);
			
		} catch (IOException e) { e.printStackTrace(); }
	}
}