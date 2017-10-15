package items;

import org.lwjgl.util.Rectangle;

import outline.Draw;
import outline.Item;
import outline.ItemID;

public class Floor extends Item {
	
	/**
	 * creates the floor of the game so the players don't fall of the screen
	 */
	public Floor() {
		super(0, 450, 1024, 16, ItemID.Floor, new Rectangle((int)0, (int)480, (int)1024, (int)16));
	}
	
	/**
	 * draws the transparent floor
	 */
	public void render() {
		Draw.drawQuad(x, y, width, height, texture);
	}
	
	@Override
	public void tick() {
	}
	
	@Override
	public void drawHealth() {
	}
	
}
