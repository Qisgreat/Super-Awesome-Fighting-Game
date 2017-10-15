package outline;

import static outline.Draw.Background;
import static outline.Draw.Setup;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import javax.swing.JOptionPane;

import outline.Draw;
import outline.Integrator;
import outline.Item;
import outline.Time;

import Menu.MenuScreen;

import org.newdawn.slick.opengl.Texture;

public class Game {
	
	public static boolean DEBUG = false;
	public static int state = 0;
	public final static Time GAME_TIME = new Time();
	public static ArrayList<Item> items = new ArrayList<Item>();
	public MenuScreen menu;
	
	/**
	 * Creates a game object that starts the game
	  * Has an arraylist of objects in the game
	 */
	public Game() {
		Setup();
		menu = new MenuScreen();
		
		Integrator.loadLevel();

		JOptionPane.showMessageDialog(null, "Press Space to start the game\nPress Escape to exit the game", "Instructions", JOptionPane.INFORMATION_MESSAGE);
		
		while(!Display.isCloseRequested()) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			
			if(state == 0) {
				menu.drawMenu();
				if(Keyboard.isKeyDown(Keyboard.KEY_SPACE))
					state = 1;
				else if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
					System.exit(0);
			}else if(state == 1) {
				GAME_TIME.update();
				Background();
					
				for(int i = 0; i < Integrator.getObjects().size(); i++) {
					Integrator.getObjects().get(i).tick();				//Every object in the game does one tick
					Integrator.getObjects().get(i).render();			//Every object in the game is rendered
					Integrator.getObjects().get(i).drawHealth();		//The two players' health are displayed and updated
				}
				
				if(Integrator.getPlayer1().getHealth() <= 0 && Integrator.getPlayer2().getHealth() > 0) {				//These three if statements check who's health depletes
					JOptionPane.showMessageDialog(null, "ROBOT WINS!", "Someone won...", JOptionPane.INFORMATION_MESSAGE);			//to 0 first then correctly displays who won
					System.exit(0);
				}else if(Integrator.getPlayer2().getHealth() <= 0 && Integrator.getPlayer1().getHealth() > 0) {
					JOptionPane.showMessageDialog(null, "DINO WINS!", "Someone won...", JOptionPane.INFORMATION_MESSAGE);
					System.exit(0);
				}else if(Integrator.getPlayer2().getHealth() <= 0 && Integrator.getPlayer1().getHealth() <= 0) {
					JOptionPane.showMessageDialog(null, "what did you do?", "???", JOptionPane.INFORMATION_MESSAGE);
					System.exit(0);
				}
				
			}
			
			Display.update();
			Display.sync(60);
		}
		
		Display.destroy();
	}

	public static void main(String[] args) {
		new Game();
	}
}