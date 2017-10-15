package outline;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Draw {
	
	public static final int WIDTH = 1024, HEIGHT = 512;		//width and height of the display screen
	private static Texture background;
	
	/**
	 * sets up the display as well as the build path and natives necessary to run LWJGL
	 * based on system operating system
	 */
	public static void Setup() 																			
	{
		if(System.getProperty("os.name").contains("Windows"))		//if windows, load windows natives	
			System.setProperty("org.lwjgl.librarypath", new File("natives/windows").getAbsolutePath()); 
		else if(System.getProperty("os.name").contains("Mac")) 		//if mac, load mac natives							
			System.setProperty("org.lwjgl.librarypath", new File("natives/macosx").getAbsolutePath());  
		else {
			System.out.println("Your OS is not supported");			//if not supported os, print "not supported"
			System.exit(0); 										//and exit the program
		}
		
		Display.setTitle("Alley Fiter, or something..."); 			//title of the display screen
		
		try { 																	
			Display.setDisplayMode(new DisplayMode(WIDTH,HEIGHT));	//set display to desired width and height
			Display.create(); 													
		} catch(LWJGLException e) {									//if cant, throw exception
			e.printStackTrace(); 												
		}
			
		glMatrixMode(GL_PROJECTION);			//set up coord system
		glLoadIdentity();
		glOrtho(0, WIDTH, HEIGHT, 0, 91, -1);	//makes game 2D based
		glMatrixMode(GL_MODELVIEW);				//allows drawing
		
		background = loadTexture("textures/background.png", "PNG");	//load in background texture
	}
	
	/**
	 * wrapper for setup animation
	 */
	public static void startTrans() {
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	}
	
	/**
	 * wrapper for finishing animation
	 */
	public static void endTrans() {
		glDisable(GL_BLEND);
	}
	
	/**
	 * draws the background using previously loaded texture
	 */
	public static void Background() {
		drawQuad(0, 0, WIDTH, HEIGHT, background);	//binds texture to the display screen
		glColor3d(1, 1, 1);
	}
	
	/**
	 * draws a quad shape at x, y with width and height
	 * @param x	x coord
	 * @param y y coord
	 * @param width width of quad
	 * @param height height of quad
	 */
	public static void drawQuad(float x, float y, float width, float height) {
		glDisable(GL_TEXTURE_2D); 												
		
		glBegin(GL_QUADS);
		
		glVertex2f(x, y);
		glVertex2f(x + width, y);
		glVertex2f(x + width,y + height);
		glVertex2f(x, y + height);
		
		glEnd();
	}
	
	/**
	 * does the same as drawQuad above but this one takes in a texture parameter
	 * so it draws the quad with a texture overlaid on it
	 * @param x x coord
	 * @param y y coord
	 * @param width width of quad
	 * @param height height of quad
	 * @param texture texture that is bound to the quad
	 */
	public static void drawQuad(float x, float y, float width, float height, Texture texture) {		
		glEnable(GL_TEXTURE_2D); 															 
		texture.bind();	  																	  
		glTranslatef(x, y, 0);													  
		
		glBegin(GL_QUADS);
		
		glTexCoord2f(0, 0);
		glVertex2f(0, 0);
		
		glTexCoord2f(1, 0);
		glVertex2f(width, 0);
		
		glTexCoord2f(1, 1);
		glVertex2f(width, height);
		
		glTexCoord2f(0, 1);
		glVertex2f(0, height);
		
		glEnd(); 
		
		glLoadIdentity(); 
	}
	
	/**
	 * does the same as the first drawQuad method above but this one takes in RGB values
	 * @param x x coord
	 * @param y y coord
	 * @param width width of quad
	 * @param height height of quad
	 * @param r red colour value
	 * @param g green colour value
	 * @param b blue colour value
	 */
	public static void drawQuad(float x, float y, float width, float height, float r, float g, float b) {
		glDisable(GL_TEXTURE_2D);
		
		glBegin(GL_QUADS);
		
		glColor3f(r, g, b);
		glVertex2f(x, y);
		glVertex2f(x+width, y);
		glVertex2f(x+width, y+height);
		glVertex2f(x, y+height);
		glColor3f(1,1,1);
		
		glEnd();
	}
	
	/**
	 * loads a texture from the path parameter
	 * @param path file path of the texture
	 * @param type extension of the file
	 * @return the texture loaded in
	 */
	public static Texture loadTexture(String path, String type) {
		try {
			return TextureLoader.getTexture(type, new FileInputStream(new File(path)));
		} catch (FileNotFoundException e) {
			return null;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * faster way to use loadTexture if textures folder is set up a certain way
	 * @param path folder name of textures
	 * @return the texture loaded in
	 */
	public static Texture quickLoad(String path) {
		return loadTexture("textures/" + path + ".png", "png");
	}
}