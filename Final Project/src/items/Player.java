package items;

import static outline.Game.GAME_TIME;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.Rectangle;
import org.newdawn.slick.opengl.Texture;

import outline.Item;
import outline.Integrator;

import org.lwjgl.input.Mouse;

import outline.Animation;
import outline.Draw;
import outline.Game;
import outline.Item;
import outline.Integrator;
import outline.ItemID;
import outline.Time;

public abstract class Player extends Item{
	
	protected Animation current;
	
	protected int numOfFrames;
	
	protected float start_x;
	protected float start_y;
	protected boolean left_down;
	protected boolean right_down;
	protected boolean jump_down;
	
	protected int direction = 0;
	protected final float gravity = 0.02f;
	protected final float MAX_SPEED = 1f;
	protected final float PLAYER_SPEED = 0.3f;
	protected float health = 450;
	
	protected boolean falling = false;
	protected boolean jumping = false;
	protected boolean dead = false;

	protected boolean punching = false;
	protected boolean walking = false;
	protected boolean kicking = false;
	protected boolean ducking = false;
	
	/**
	 * creates a new Player, either a Dino or Robot
	 * @param x x coord
	 * @param y y coord
	 * @param width width
	 * @param height height
	 * @param id id
	 */
	public Player(float x, float y, float width, float height, ItemID id){
		super(x, y, width, height, id, new Rectangle((int) (x + width / 4), (int) (y + height / 4), (int) (width / 3), (int)(3 * height / 5)));
		current = new Animation(id.texture, 12);
	}
	
	/**
	 * draws the Player
	 */
	public void render(){
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		Draw.drawQuad(x, y, width, height, current.getCurrentFrame());
		glDisable(GL_BLEND);
	}
	
	/**
	 * updates the hitbox every time it's moved so that it stay with the drawn character
	 */
	protected void updateHitbox() {
		
		if(id == ItemID.Robot) {
			switch(direction) {
			case -1:
				hitbox.setLocation((int) (x + (5 * width / 12)), (int) (y + height / 4)); 
				break;
			case 1:
				hitbox.setLocation((int) (x + width / 4), (int) (y + height / 4));
				break;
			}
		}
		
		if(id == ItemID.Dino) {
			switch(direction) {
			case -1:
				hitbox.setLocation((int) (x + 4 * width / 15), (int) (y + height / 4)); 
				break;
			case 1:
				hitbox.setLocation((int) (x + 2 * width / 5), (int) (y + height / 4));
				break;
			}
		}
		
	}
	
	public Rectangle getHitbox() {
		return hitbox;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public float getHealth() {
		return health;
	}
	
	public void setHealth(float h) {
		health -= h;
	}
	
	public abstract void tick();
}