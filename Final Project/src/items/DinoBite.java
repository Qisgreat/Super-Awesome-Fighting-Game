package items;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static outline.Game.GAME_TIME;

import org.lwjgl.util.Rectangle;

import outline.Integrator;
import outline.Level;
import outline.Animation;
import outline.Item;
import outline.ItemID;
import outline.Draw;

public class DinoBite extends Item {
	
	private float power = 5;
	
	public DinoBite(float x, float y, ItemID id) {
		super(x, y, 50, 50, id, new Rectangle((int) x, (int) y, (int) 50, (int) 50));
	}
	
	public void render() {
//		Draw.drawQuad(x, y, 50, 50);
	}
	
	public float getPower() {
		return power;
	}
	
	public void tick() {
	}
	
	public void drawHealth() {
	}
	
}
