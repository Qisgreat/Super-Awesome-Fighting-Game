package Menu;

import static outline.Game.GAME_TIME;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;

import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.opengl.Texture;

import outline.Animation;
import outline.Draw;

public class MenuScreen {
	
	private boolean play;
	private Texture back;
	
	/**
	 * sets up the title and buttons to the correct specification
	 */
	public MenuScreen(){
		back = Draw.loadTexture("textures/Menu.png", "PNG");
	}
	
	/**
	 * draws the menu with title and buttons
	 */
	public void drawMenu(){
		render();
	}
	
	public void render(){
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		Draw.drawQuad(0, 0, Draw.WIDTH, Draw.HEIGHT, back);
		glDisable(GL_BLEND); 
	}
	
}
