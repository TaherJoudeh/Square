package game;

import java.awt.Graphics;
import java.util.LinkedList;

public class Handler {
	
	private LinkedList <GameObject> handler = new LinkedList <GameObject> ();
	
	public int size() { return handler.size(); }
	public LinkedList <GameObject> getHandler() { return handler; }
	public void addGameObject(GameObject gameOBJ) { handler.add(gameOBJ); }
	public void removeGameObject(GameObject gameOBJ) { handler.remove(gameOBJ); }
	public void removeGameObject(int index) { handler.remove(index); }
	public GameObject getGameObject(int index) { return handler.get(index);  } 
	
	public int getEnemyNumber() {
		int count = 0;
		for(int i = 0; i < handler.size(); i++) if(getGameObject(i).getID() == ID.Enemy ||
				getGameObject(i).getID() == ID.movingEnemy) count++;
		return count;
	}
	
	public void tickGO(int index) {
		if(index >= handler.size() || getGameObject(index) == null) return;
		getGameObject(index).tick();
		tickGO(++index);
	}
	
	public void renderGO(int index, Graphics g) {
		if(index >= handler.size() || getGameObject(index) == null) return;
		getGameObject(index).render(g);
		renderGO(++index, g);
	}
	
	public void OnlyPlayer(int index) {
		if(handler.size() == 1) return;
		handler.remove(index);
		OnlyPlayer(1);
	}
	
	public void tick() {
		for(int i = 0; i < handler.size(); i++) getGameObject(i).tick();
	}
	public void render(Graphics g) {
		for(int i = 0; i < handler.size(); i++) getGameObject(i).render(g);
	}
	
	public void gameObjectDestroy(int index) {
		
		if(index >= handler.size()) return;
		
		if(getGameObject(index).getY() > Window.HEIGHT) handler.remove(getGameObject(index));
		else if(getGameObject(index).getID() == ID.Bullet && getGameObject(index).getY()+getGameObject(index).getHeight() < 0)
			handler.remove(index);
		
		gameObjectDestroy(++index);
		
	}
}