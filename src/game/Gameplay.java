package game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.util.Random;
import gameInputs.Buttons;
import gameInputs.KeyInput;
import gameInputs.Key_Mouse;
import gameInputs.MouseInput;

public class Gameplay extends Canvas implements Runnable {

	private static final long serialVersionUID = 5L;
	private boolean running;
	public static boolean isEND = false, pause = false, endOperation = true, invisible = false, pressEnterPaint = true, removed;
	private Thread thread;
	private static int frames = 0, updates = 0;
	public static int HS, highSCORE, ImageIndex, coinCount, pauseCount = 255, pauseTimer;
	public static int key, count, increaseSpeed = 5, SCORE = 0, SpawnTime = 12, changeP, countBG, pressEnterTimer, pressEnterDelay;
	public static int skinCount, coinPlus;
	private static Handler handler;
	private GameObject player;
	private Random R;
	public static MouseInput MI;
	public static KeyInput KI;
	public static STATUS gameStatus = STATUS.SELECT;
	public static Key_Mouse KM;
	public static String code = "";
	private Accounts accounts;
	private Customize customize;
	private AudioPlayer audioPlayer;
	private Images image;
	private Power power;
	public static Color background = Color.DARK_GRAY, enemies = Color.BLACK;
	public static boolean toDark = true, DarkBright;
	private int DarkBright_Timer;
	
	public Gameplay() {

		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		thread = new Thread(this);
		R = new Random();

		DarkBright_Timer = R.nextInt(1700) + 1500;
		
		image = new Images();
		customize = new Customize();
		handler = new Handler();
		accounts = new Accounts();
		audioPlayer = new AudioPlayer();
		
		image.load();
		
		player = new GameObject(Window.WIDTH / 2 - 16, Window.HEIGHT - 40, 32, 32, 0, 0, ID.Player, handler);
		handler.addGameObject(player);
		
		power = new Power(handler);
		audioPlayer.load();

		KI = new KeyInput(handler, this);
		MI = new MouseInput(handler, this);
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) { e.printStackTrace(); }

		AudioPlayer.playTheme("Background");
		
		addMouseMotionListener(MI);
		addMouseListener(MI);
		addKeyListener(KI);
		
	}

	public synchronized void start() {

		if (running)
			return;
		running = true;

		thread.start();

	}

	public synchronized void stop() {

		if (!running)
			return;
		running = false;
		System.exit(0);

	}

	public void run() {

		long lastTime = System.nanoTime();
		double unproccesed = 0;
		double nsPerSec = 1000000000.0/60.0;
		long timer = System.currentTimeMillis();

		while (running) {
			long now = System.nanoTime();
			unproccesed += (now - lastTime)/nsPerSec;
			lastTime = now;
			if (unproccesed >= 1) {
				updates++;
				tick();
				unproccesed--;
			}

			try {
				Thread.sleep(2);
			} catch (InterruptedException e) { e.printStackTrace(); }

			frames++;
			render();
						
			if ((System.currentTimeMillis() - timer) > 1000) {
				Window.j.setTitle("Square-FPS: " + frames);
				frames = 0;
				updates = 0;
				timer += 1000;
			}
		}
	}

	private void tick() {

		if (!isEND) {
			if (!pause && gameStatus == STATUS.GAME) {
				Window.j.getContentPane()
						.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(Images.cursorImg, new Point(0, 0), "NC"));
				
				if(KeyInput.isCode) {
				if (code.equals("INVISIBLE")) {
					if (!invisible) {
						invisible = true;
						KeyInput.isCode = true;
					}
					else {
						invisible = false;
						KeyInput.isCode = false;
					}
				}else if(code.equals("DESTROY")) handler.OnlyPlayer(1);
				else if(code.equals("SCOREUP")) SCORE += 1250;
				code = "";
				KeyInput.codeLimitInput = 0;
			}

				if (count % 3 == 0) SCORE++;
				
				if(count%DarkBright_Timer == 0 && count != 0) {
					DarkBright = true;
					DarkBright_Timer = R.nextInt(2000) + 1500;
				}
				darkBright();
				
				if(count != 0) {
					
				if(R.nextBoolean() && SCORE > 500 && count%500 == 0) spawn(new GameObject(R.nextInt(Window.WIDTH-20)+20, -16,
							16, 16, -R.nextInt(5) - 5, R.nextInt(5) + 2, ID.movingEnemy, handler));
					
				if(count%Power.autoGun_spawnTime == 0 && !Power.havePower) {
					spawn(new GameObject(R.nextInt(Window.WIDTH-20)+20, -16, 16, 16, 0, 7, ID.Power_autoGun, handler));
					Power.autoGun_spawnTime = R.nextInt(2000) + 1700;
				}else 
				if(count%Power.Invisible_spawnTime == 0 && !Power.havePower) {
					spawn(new GameObject(R.nextInt(Window.WIDTH-20)+20, -16,
							16, 16, 0, 7, ID.Power_Invisble, handler));
					Power.Invisible_spawnTime = R.nextInt(1700) + 1500;
				}
				else 
				if(R.nextBoolean() && count%Power.Boost_spawnTime == 0 && !Power.havePower) {
					spawn(new GameObject(R.nextInt(Window.WIDTH-17)+17,-16, 16, 16, 0, 7, ID.Power_Boost, handler));
					Power.Boost_spawnTime = R.nextInt(500) + 1000;
				}
				}
				
				if (count % 1000 == 0) {
					if (SpawnTime < 5)
						SpawnTime = 5;
					else
						SpawnTime--;
				}
				if (count % 750 == 0) {
					if (increaseSpeed > 10)
						increaseSpeed = 10;
					else
						increaseSpeed++;
				}
				if (count % SpawnTime == 0 && handler.getEnemyNumber() < 11) {
					int objWidth = R.nextInt(170) + 32;
					int objHeight = R.nextInt(7) + 120;
					GameObject newGO = new GameObject((R.nextInt(Window.WIDTH - 50) + 1), -objHeight, objWidth,
							objHeight, 0, increaseSpeed, ID.Enemy, handler);
					spawn(newGO);
				}

				handler.tickGO(0);
				Power.Boost_tick();
				Power.Invisible_tick();
				Power.autoGun_tick();
				handler.gameObjectDestroy(1);
				GameEnd();
				
				if (SCORE > HS)
					HS = SCORE;

				count++;

			} else if(gameStatus != STATUS.DEVELOP){
				Window.j.getContentPane().setCursor(Cursor.getDefaultCursor());
								
				if (!pause) {
					handler.tickGO(1);
					background = Color.DARK_GRAY;
					enemies = Color.BLACK;
					if (countBG % 17 == 0 && handler.getEnemyNumber() < 15) {
						int objWidth = R.nextInt(100) + 50;
						int objHeight = R.nextInt(60) + 30;
						GameObject newGO = new GameObject((R.nextInt(Window.WIDTH - (objWidth+3)) + (objWidth+3)), -objHeight, objWidth,
								objHeight, 0, 3, ID.Enemy, handler);
						spawn(newGO);
					}
					handler.gameObjectDestroy(1);
					countBG++;
				}

				if (gameStatus == STATUS.SELECT && !Accounts.inAccount) {
					if (skinCount % 27 == 0 && skinCount != 0) {
						Images.skin1 = Customize.skins.get(R.nextInt(Customize.skins.size()));
						Images.skin2 = Customize.skins.get(R.nextInt(Customize.skins.size()));
					}
					skinCount++;
				}

			}
		} else if (endOperation) {
			Accounts.f.setWritable(true);
			Accounts.setCoins((Accounts.AccountsCoins += (SCORE/40)));
			if (SCORE > highSCORE)
				Accounts.setNewHightScore();
			Accounts.f.setWritable(false);
			endOperation = false;
		}
	}

	private void render() {

		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D) g;
		
		Color c = new Color(255, 0, 30, 137);
		Font f = new Font("Forte", Font.BOLD, 100);
		FontMetrics fm = getFontMetrics(f);

		g.setColor(background);
		g.fillRect(0, 0, Window.WIDTH, Window.HEIGHT);

		if(gameStatus == STATUS.GAME || gameStatus == STATUS.DEVELOP) handler.renderGO(0, g);
		
		if (gameStatus != STATUS.GAME)
			handler.renderGO(1, g);

		if (KeyInput.developerMODE) {
			g.setFont(new Font("Arial", Font.BOLD, 25));
			g.setColor(Color.GREEN);
			g.drawString("D", 0, 23);
		}
		
		if(gameStatus == STATUS.DEVELOP) {
			
			g.setColor(new Color(0, 0, 0, 127));
			g.fillRect(0, 0, Window.WIDTH, Window.HEIGHT);
			g.setColor(new Color(255, 255, 255, 170));
			g.fillRect(Window.WIDTH/2-190, Window.HEIGHT/2-20, 400, 80);
			
			g.setColor(Color.BLUE);
			g.drawRect(Window.WIDTH/2-190, Window.HEIGHT/2-20, 400, 80);
			
			g.setFont(new Font("Arial", Font.BOLD, 35));
			g.drawString(code, Window.WIDTH/2-180, Window.HEIGHT/2+33);
			
			g.setFont(f);
			fm = getFontMetrics(f);
			
			g.drawString("CODE", Window.WIDTH/2-fm.stringWidth("CODE")/2, 167);
			
		} else if (gameStatus == STATUS.CUSTOMIZE) {

			g.drawImage(Customize.getSkin(ImageIndex), Window.WIDTH / 2 - 16, Window.HEIGHT / 2 - 16, this);

			if (((Customize.skins.size() - 1) - ImageIndex) > 0)
				g.drawImage(Images.rightArrow, Window.WIDTH / 2 + 29, Window.HEIGHT / 2 - 32, this);
			if (((Customize.skins.size() - 1) - ImageIndex < Customize.skins.size() - 1))
				g.drawImage(Images.leftArrow, Window.WIDTH / 2 - 90, Window.HEIGHT / 2 - 32, this);

			g.setFont(new Font("Arial", Font.BOLD, 27));
			g.setColor(Color.YELLOW);
			g.drawImage(Images.coinImg, Window.WIDTH / 2 - 50, Window.HEIGHT - 145, this);
			g.drawString(Accounts.AccountsCoins + "", Window.WIDTH / 2 - 6, Window.HEIGHT - 119);

			if (Accounts.OWN_SKINS.contains(Customize.getKey(Customize.getSkin(Gameplay.ImageIndex)) + "") || Customize.price.get(Customize.getKey(Customize.getSkin(Gameplay.ImageIndex))) == 0 ||
					KeyInput.developerMODE) {

				g.drawString("OWNED", Window.WIDTH / 2 - 52, Window.HEIGHT / 2 + 70);

			} else {
				g.drawImage(Images.coinImg_16, Window.WIDTH / 2 - 40, Window.HEIGHT / 2 + 52, this);
				g.drawString(Customize.price.get(Customize.getKey(Customize.getSkin(ImageIndex))) + "", Window.WIDTH / 2 - 16, Window.HEIGHT / 2 + 70);
			}

			g.setColor(c);
			g2d.fill(Buttons.Back);

			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.CENTER_BASELINE, 25));
			g.drawString("BACK", 24, 39);
			drawAroundREC(g);

		} else if (gameStatus == STATUS.LOGIN) {

			g.setColor(Color.RED);
			g.setFont(f);
			fm = getFontMetrics(f);

			g.drawString("Login", Window.WIDTH / 2 - fm.stringWidth("Login")/2, 100);
			f = new Font("Forte", Font.BOLD, 45);
			g.setFont(f);

			g.setColor(Color.CYAN);
			g.drawString("Name: ", KeyInput.NameInput.x-200, KeyInput.NameInput.y+KeyInput.NameInput.height/2+5);
			g.drawString("iD: ", KeyInput.IdInput.x-200, KeyInput.IdInput.y+KeyInput.IdInput.height/2+5);

			g.setColor(new Color(255, 255, 255, 100));
			g2d.fill(KeyInput.NameInput);
			g2d.fill(KeyInput.IdInput);

			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.CENTER_BASELINE, 37));

			g.drawString(Accounts.name, KeyInput.NameInput.x+17, KeyInput.NameInput.y+KeyInput.NameInput.height/2+10);
			f = new Font("Arial", Font.BOLD, 60);
			g.setFont(f);
			g.drawString(Accounts.idTEXT, KeyInput.IdInput.x+17, KeyInput.IdInput.y+KeyInput.IdInput.height/2+30);

			g.setFont(new Font("Arial", Font.CENTER_BASELINE, 25));
			g.setColor(c);
			g2d.fill(Accounts.RL_Apply);
			g2d.fill(Buttons.Back);

			g.setColor(Color.WHITE);
			g.drawString("LOGIN", Window.WIDTH / 2 - 45, Window.HEIGHT - 44);
			g.drawString("BACK", 24, 39);

			g.setColor(Color.WHITE);
			drawAroundREC(g);

		} else if (gameStatus == STATUS.REGISTER) {

			g.setColor(Color.RED);
			g.setFont(f);
			fm = getFontMetrics(f);

			g.drawString("Register", Window.WIDTH / 2 - fm.stringWidth("Register")/2, 100);
			
			f = new Font("Forte", Font.BOLD, 45);
			g.setFont(f);

			g.setColor(Color.CYAN);
			g.drawString("Name: ", KeyInput.NameInput.x-200, KeyInput.NameInput.y+KeyInput.NameInput.height/2+5);
			g.drawString("iD: ", KeyInput.IdInput.x-200, KeyInput.IdInput.y+KeyInput.IdInput.height/2+5);

			g.setColor(new Color(255, 255, 255, 100));
			g2d.fill(KeyInput.NameInput);
			g2d.fill(KeyInput.IdInput);

			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.CENTER_BASELINE, 37));

			g.drawString(Accounts.name, KeyInput.NameInput.x+17, KeyInput.NameInput.y+KeyInput.NameInput.height/2+10);
			f = new Font("Arial", Font.BOLD, 60);
			g.setFont(f);
			g.drawString(Accounts.idTEXT, KeyInput.IdInput.x+17, KeyInput.IdInput.y+KeyInput.IdInput.height/2+30);

			g.setFont(new Font("Arial", Font.CENTER_BASELINE, 25));
			g.setColor(c);
			g2d.fill(Accounts.RL_Apply);
			g2d.fill(Buttons.Back);

			g.setColor(Color.WHITE);
			g.drawString("REGISTER", Window.WIDTH / 2 - 70, Window.HEIGHT - 44);
			g.drawString("BACK", 24, 39);

			g.setColor(Color.WHITE);
			drawAroundREC(g);

		} else if (gameStatus == STATUS.SELECT) {

			g2d.setColor(c);
			g2d.fill(MouseInput.Keyboard);
			g2d.fill(MouseInput.Mouse);
			
			g2d.setColor(new Color(50, 50, 255, 150));
			g2d.setFont(f);
					
			g2d.drawString("SQUARE", Window.WIDTH / 2 - fm.stringWidth("SQUARE")/2, 150);
			g2d.setColor(Color.BLUE);

			if (Accounts.inAccount) {
				g.drawImage(Accounts.getImage(), Accounts.customize1.x, Accounts.customize1.y, null);
				g.drawImage(Accounts.getImage(), Accounts.customize2.x, Accounts.customize2.y, null);
			} else {
				g.drawImage(Images.skin1, Window.WIDTH / 2 - 240, 100, null);
				g.drawImage(Images.skin2, Window.WIDTH / 2 + 213, 100, null);
			}

			g2d.setColor(c);

			g.drawImage(Images.keyboard, MouseInput.Keyboard.x + MouseInput.Keyboard.width/2-40, MouseInput.Keyboard.y + MouseInput.Keyboard.height/2-32, null);
			g.drawImage(Images.mouse, MouseInput.Mouse.x + MouseInput.Keyboard.width/2-32, MouseInput.Mouse.y + MouseInput.Mouse.height/2-32, null);

			f = new Font("Forte", Font.ITALIC, 20);
			g.setFont(f);
			fm = getFontMetrics(f);

			if (!Accounts.inAccount) {
				g2d.fill(Accounts.Register);
				g2d.fill(Accounts.Login);
				g.setColor(Color.WHITE);
				g.drawString("Login", Accounts.Login.x + (Accounts.Login.width/2)-(fm.stringWidth("LOGIN")/2), Accounts.Login.y + Accounts.Login.height/2+5);
				g.drawString("Register", Accounts.Register.x + (Accounts.Register.width/2)-(fm.stringWidth("REGISTER")/2)+2, Accounts.Register.y + Accounts.Register.height/2+3);
			} else if (Accounts.f != null && Accounts.inAccount) {
				g2d.fill(Accounts.LogOut);
				g.setColor(Color.WHITE);
				g.drawString("Logout", Window.WIDTH / 2 - 32, Window.HEIGHT - 9);

				f = new Font("Arial", Font.BOLD, 40);
				g.setFont(f);
				
				if(KeyInput.developerMODE) g.setColor(Color.GREEN);
				
				g.drawString(Accounts.AccountName, 3, Window.HEIGHT - 130);
				g.setColor(Color.WHITE);
				f = new Font("Arial", Font.PLAIN, 25);
				g.setFont(f);
				g.drawString("High Score: " + Gameplay.highSCORE + "", 0, Window.HEIGHT - 100);
				g.drawImage(Images.coinImg, 3, Window.HEIGHT - 90, this);
				g.setColor(Color.YELLOW);
				g.drawString(Accounts.getCoins() + "", 50, Window.HEIGHT - 65);
			}

			g.setColor(Color.WHITE);
			drawAroundREC(g);

		}

		else {

			showCoinsAtGame(g);

			if (isEND) {

				g.setColor(new Color(0, 0, 0, 157));
				g.fillRect(0, 0, Window.WIDTH, Window.HEIGHT);
				g.setColor(Color.CYAN);
				g.setFont(new Font("Arial", Font.PLAIN, 25));
				displayCoin(g);
				
				if(removed) {
				GameObject.crash = handler.getGameObject(getCrashObject());
				handler.removeGameObject(GameObject.crash);
				removed = false;
				}
				
				Color preC = g.getColor();
				
				g.setColor(new Color(30, 30, 30, 50));
				g.fillRect(GameObject.crash.getX(), GameObject.crash.getY(), GameObject.crash.getWidth(), GameObject.crash.getHeight());
				
				g.setColor(Color.BLACK);
				g.drawRect(GameObject.crash.getX(), GameObject.crash.getY(), GameObject.crash.getWidth(), GameObject.crash.getHeight());
				
				g.setColor(preC);
				Font font = new Font("Arial", Font.BOLD, 30);
			    FontMetrics metrics = g.getFontMetrics(font);

				int x = (Window.WIDTH - metrics.stringWidth("HIGH SCORE: " + Gameplay.HS)) / 2;
				
				g.setFont(font);
				g.drawString("HIGH SCORE: " + Gameplay.HS, x, Window.HEIGHT / 2 - 70);
				
				x = (Window.WIDTH - metrics.stringWidth("SCORE: " + Gameplay.SCORE)) / 2;
			    
				g.drawString("SCORE: " + Gameplay.SCORE, x, Window.HEIGHT / 2 - 20);

				pressEnter(g);
				
			} else {
				
				Font F = new Font("Arial", Font.BOLD, 13);
				Power.Boost_render(g);
				Power.Invisible_render(g);
				Power.autoGun_render(g);

				if (SCORE > highSCORE) {
					g.setColor(Color.YELLOW);
				} else
					g.setColor(new Color(255,255,255,200));
				g.setFont(F);

				g.drawString("Score: " + SCORE + " - HighScore: " + HS, 1, Window.HEIGHT - 5);

				if (pause) pauseDisplay(g);
				
			}
		}
		
		bs.show();
		g.dispose();

	}

	private void GameEnd() {

		if(isEND || invisible) return;
		for (int i = 1; i < handler.getHandler().size(); i++) {
			if(handler.getGameObject(i).getID() == ID.Enemy || handler.getGameObject(i).getID() == ID.movingEnemy)
			if (handler.getGameObject(0).getBounds().intersects(handler.getGameObject(i).getBounds())) {
				
				isEND = true;
				removed = true;
				AudioPlayer.closeClip();
				AudioPlayer.playSound("Collapse");
				
				try {
					Thread.sleep(30);
				} catch (InterruptedException e) { e.printStackTrace(); }
								
				}
			}
		endOperation = true;
	}

	public int getCrashObject() {
		
		for(int i = 1; i < handler.size(); i++) {
			if(handler.getGameObject(i).getID() == ID.Enemy || handler.getGameObject(i).getID() == ID.movingEnemy)
			if(handler.getGameObject(0).getBounds().intersects(handler.getGameObject(i).getBounds())) return i;
		}
		
		return 0;
		
	}
	
	public void spawn(GameObject newOBJ) {

		boolean insert = true;

		for (int i = 1; i < handler.getHandler().size(); i++) {

			if (handler.getGameObject(i).getBounds().intersects(newOBJ.getBounds()))
				insert = false;
		}

		if (insert)
			handler.addGameObject(newOBJ);
		return;

	}

	public static void main(String [] args) {

		new Window("Square-FPS: " + frames, new Gameplay());

	}

	private void drawAroundREC(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;

		if (gameStatus == STATUS.SELECT) {
			for (int i = 0; i < Buttons.displayS.size(); i++) {
				if (Buttons.displayS.get(i))
					g2d.draw(Buttons.select.get(i));
			}
		} else if (gameStatus == STATUS.LOGIN || gameStatus == STATUS.REGISTER || gameStatus == STATUS.CUSTOMIZE) {

			if (KeyInput.Name)
				g2d.draw(KeyInput.NameInput);
			if (KeyInput.Id)
				g2d.draw(KeyInput.IdInput);

			for (int i = 0; i < Buttons.displayR_L.size(); i++) {
				if (Buttons.displayR_L.get(i))
					g2d.draw(Buttons.R_L.get(i));
			}
		}

	}

	public static void stopDisplay() {

		for (int i = 0; i < Buttons.displayS.size(); i++)
			Buttons.displayS.set(i, false);
		for (int i = 0; i < Buttons.displayR_L.size(); i++)
			Buttons.displayR_L.set(i, false);

	}

	private void displayCoin(Graphics g) {
		
		Color PreC = g.getColor();
		g.setFont(new Font("Arial", Font.PLAIN, 20));
		g.setColor(Color.YELLOW);
		
		g.drawImage(Images.coinImg, Window.WIDTH/2-30, Window.HEIGHT/2+20, this);
		g.drawString("+"+coinCount, Window.WIDTH/2, Window.HEIGHT/2+30);
		g.setColor(PreC);
		if(coinCount >= SCORE/40) return;
		coinPlus++;
		if(coinPlus%2 == 0) coinCount++;
		if(coinPlus%3 == 0) AudioPlayer.playSound("Coin");
		
	}

	private void showCoinsAtGame(Graphics g) {
		if (KeyInput.showCoinsAtGame && !pause && !isEND) {
			if (KeyInput.showCoinsCount <= 1500) {
				g.setColor(Color.YELLOW);
				g.setFont(new Font("Arial", Font.PLAIN, 20));
				g.drawString((Accounts.AccountsCoins) + "", Window.WIDTH - 57, 23);
				g.drawImage(Images.coinImg, Window.WIDTH - 100, 1, this);
				KeyInput.showCoinsCount++;
			} else {
				KeyInput.showCoinsCount = 0;
				KeyInput.showCoinsAtGame = false;
			}
		}
	}
	
	public void pauseDisplay(Graphics g) {
		
		Color preC = g.getColor();
		if(pauseCount <= 23) pauseCount = 255;
		if(pauseTimer%2 == 0) pauseCount--;
		pauseTimer++;
		
		Color c = new Color(255, 50, 0, pauseCount);
		g.setColor(c);
		g.drawImage(Images.pauseImg, Window.WIDTH-43, 7, null);
		g.drawRect(0, 0, Window.WIDTH-1, Window.HEIGHT-3);
		g.drawRect(1, 1, Window.WIDTH-3, Window.HEIGHT-5);
		g.drawRect(2, 2, Window.WIDTH-5, Window.HEIGHT-7);
		
		g.setColor(preC);
		
	}
	
	private static void darkBright() {

			if(!DarkBright || Power.haveBoost) return;
			
				if(toDark) {
					if(background.getBlue()-3 < 0) background = Color.BLACK;
					
					else background = new Color
							(background.getRed()-3, background.getBlue()-3, background.getGreen()-3);
					if(enemies.getBlue()+3 > Color.WHITE.getBlue()) enemies = Color.WHITE;
					else enemies = new Color
							(enemies.getRed()+3, enemies.getBlue()+3, enemies.getGreen()+3);
					
					if(background == Color.BLACK && enemies == Color.WHITE) {
						DarkBright = false;
						if(toDark) toDark = false;
						else toDark = true;
						return;
					}
					
					
				}else {
					if(background.getBlue()+3 > Color.DARK_GRAY.getBlue()) background = Color.DARK_GRAY;
					else background = new Color
							(background.getRed()+3, background.getBlue()+3, background.getGreen()+3);
					if(enemies.getBlue()-3 < 0) enemies = Color.BLACK;
					else enemies = new Color
							(enemies.getRed()-3, enemies.getBlue()-3, enemies.getGreen()-3);

					if(background == Color.DARK_GRAY && enemies == Color.BLACK) {
						DarkBright = false;
						if(toDark) toDark = false;
						else toDark = true;
						return;
					}
					
					
				}
			}			
	
	private void pressEnter(Graphics g) {
		
		if(pressEnterDelay%3 == 0) pressEnterTimer++;
		pressEnterDelay++;
		if(pressEnterTimer%10 == 0 && pressEnterTimer != 0) {
			if(pressEnterPaint) pressEnterPaint = false;
			else pressEnterPaint = true;
			pressEnterDelay = 0;
			pressEnterTimer = 0;
		}
		
		if(pressEnterPaint) {
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.BOLD, 30));
			g.drawString("Press ENTER", Window.WIDTH/2-100, Window.HEIGHT/2+100);
		}
		
	}
	
}