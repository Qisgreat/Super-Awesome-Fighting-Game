package outline;

import java.util.ArrayList;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import outline.Draw.*;

import items.Player;
import items.Floor;

public class Integrator {
	
	private static ArrayList<Item> items = new ArrayList<Item>();
	private static Player player1;
	private static Player player2;
	private static Level currentLevel;

	public static ArrayList<Item> getObjects() {
		return items;
	}
	
	public static void addObjects(Item i) {
		items.add(i);
	}
	
	public static Player getPlayer1() {
		return player1;
	}
	
	public static void setPlayer1(Player p) {
		player1 = p;
	}
	
	public static Player getPlayer2() {
		return player2;
	}
	
	public static void setPlayer2(Player p) {
		player2 = p;
	}
	
	public static void tick() {
		for(int i = 0; i < items.size(); i++)
			items.get(i).tick();
	}
	
	public static void render() {
		for(int i = 0; i < items.size(); i++)
			items.get(i).render();
	}
	
	public static void drawHealth() {
		for(int i = 0; i < items.size(); i++)
			items.get(i).drawHealth();
	}
	
	public static void loadLevel() {
		currentLevel = new Level();
		items = currentLevel.getObjs();
	}
	
}
