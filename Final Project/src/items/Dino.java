package items;

import static outline.Game.GAME_TIME;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.Rectangle;

import outline.Integrator;
import outline.Level;
import outline.Animation;
import outline.ItemID;
import outline.Draw;

public class Dino extends Player {
	
	private static Animation idle_right;
	private static Animation idle_left;
	
	private static Animation run_left;
	private static Animation run_right;
	
	private static Animation shoot_left;
	private static Animation shoot_right;
	
	private static Animation punch_right;
	private static Animation punch_left;
	
	private static Animation kick_left;
	private static Animation kick_right;

	private static Animation duck_left;
	private static Animation duck_right;
	
	public Dino(float x, float y, float width, float height) {
		super(x, y, width, height, ItemID.Dino);						//below are all the animations for Robot
		run_left = new Animation("textures/DinoRunLeft", 24);
		run_right = new Animation("textures/DinoRunRight", 24);
		punch_left = new Animation("textures/DinoPunchLeft", 24);
		punch_right = new Animation("textures/DinoPunchRight", 24);
		kick_left = new Animation("textures/DinoKickLeft", 34);
		kick_right = new Animation("textures/DinoKickRight", 34);
		idle_left = new Animation("textures/DinoIdleLeft", 0);
		idle_right = new Animation("textures/DinoIdleRight", 0);
		duck_left = new Animation("textures/DinoDuckLeft", 12);
		duck_right = new Animation("textures/DinoDuckRight", 12);
	}
	
	/**
	 * checks key presses by player to move character and attack with them
	 */
	public void tick() {
		if(current != null)
			current.update();
		
		if(Keyboard.isKeyDown(Keyboard.KEY_D)) {		//if D is pressed
			current = run_right;						//animate moving right
			xSpeed = PLAYER_SPEED;						//move right
			direction = 1;								//set direction to 1
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_A)) {		//if A is pressed
			current = run_left;							//animate moving left
			xSpeed = -PLAYER_SPEED;						//move left
			direction = -1;								//set direction to the government
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_W)) {		//if W is pressed
			if(!jumping) {								//and not already jumping
				jumping = true;							//set jumping to true
				ySpeed = -0.5f;							//and then apply negative and speed 
			}
		}
		
		if(!Keyboard.isKeyDown(Keyboard.KEY_A) && !Keyboard.isKeyDown(Keyboard.KEY_D)) {
			switch(direction)
			{
			case -1:
				current = idle_left;
				break;
			case 1:
				current = idle_right;
				break;
			}
			xSpeed = 0;
		}
		
//		if(!Keyboard.isKeyDown(Keyboard.KEY_S) && ducking){
//			hitbox = new Rectangle((int) (x + width / 4),(int) ((y + width / 4)),(int) (width / 3),(int)(3 * height / 5));
//			ducking = false;
//		}
//		
//		if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
//			hitbox = new Rectangle((int) (x + width / 4),(int) (y + width / 2),(int) (width / 3),(int)(3 * height / 10));
//			switch(direction) {
//			case -1:
//				current = duck_left;
//				break;
//			case 1:
//				current = duck_right;
//				break;
//			}
//			ducking = true;
//		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_F)) {				//allows characters to punch, cretes hitboxes for the punches
			switch(direction) {
			case -1:
				current = punch_left;
				if(current.getFrameNum() == 5) {
					DinoBite pLeft = new DinoBite(x + (3 * width / 12) - 50 , y + height / 2, ItemID.DinoPunch);
					pLeft.render();
					if(Integrator.getPlayer2().getHitBox().intersects(pLeft.getHitBox())) {
						Integrator.getPlayer2().setHealth(pLeft.getPower());
						Integrator.getPlayer2().setX(Integrator.getPlayer2().getX() - 50);
					}
				}
				break;
			case 1:
				current = punch_right;
				if(current.getFrameNum() == 5) {
					DinoBite pRight = new DinoBite(x + 9 * width / 12, y + height / 2, ItemID.DinoPunch);
					pRight.render();
					if(Integrator.getPlayer2().getHitBox().intersects(pRight.getHitBox())) {
						Integrator.getPlayer2().setHealth(pRight.getPower());
						if(Integrator.getPlayer2().getX() + 50 > Draw.WIDTH - Integrator.getPlayer2().getWidth())
							Integrator.getPlayer2().setX(Draw.WIDTH - width);
						else
							Integrator.getPlayer2().setX(Integrator.getPlayer2().getX() + 50);
					}
				}
				break;
			}
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_G)) {					//same as punches but for kicks
			switch(direction) {
			case -1:
				current = kick_left;
				if(current.getFrameNum() == 19) {
					DinoKick kLeft = new DinoKick(x + (3 * width / 12) - 50 , y + 3* height / 4, ItemID.DinoPunch);
					kLeft.render();
					if(Integrator.getPlayer2().getHitBox().intersects(kLeft.getHitBox())) {
						Integrator.getPlayer2().setHealth(kLeft.getPower());
						Integrator.getPlayer2().setX(Integrator.getPlayer2().getX() - 150);
					}
				}
				break;
			case 1:
				current = kick_right;
				if(current.getFrameNum() == 19) {
					DinoKick kRight = new DinoKick(x + 9 * width / 12, y + 3* height / 4, ItemID.DinoPunch);
					kRight.render();
					if(Integrator.getPlayer2().getHitBox().intersects(kRight.getHitBox())) {
						Integrator.getPlayer2().setHealth(kRight.getPower());
						if(Integrator.getPlayer2().getX() + 150 > Draw.WIDTH - Integrator.getPlayer2().getWidth())
							Integrator.getPlayer2().setX(Draw.WIDTH - width);
						else
							Integrator.getPlayer2().setX(Integrator.getPlayer2().getX() + 150);
					}
				}
				break;
			}
		}
		
		if(falling || jumping)			
			ySpeed += gravity;
		if(ySpeed > MAX_SPEED)
			ySpeed = MAX_SPEED;
		
		//this stuff adds the xSpeed/ySpeed times the delta between the current and previous frame for smooth animations
		if(x + xSpeed*GAME_TIME.Delta() > 0 && x + xSpeed*GAME_TIME.Delta() < Draw.WIDTH - width)
			x += xSpeed*GAME_TIME.Delta();
		else if(x + xSpeed*GAME_TIME.Delta() < 0)
			x = 0;
		else if(x + xSpeed*GAME_TIME.Delta() > Draw.WIDTH)
			x = Draw.WIDTH - width;
		
		if(y + ySpeed*GAME_TIME.Delta() < Level.getFloor().getY() - height)
			y += ySpeed*GAME_TIME.Delta();
		else {
			jumping = false;
			y = Level.getFloor().getY() - height;
		}
		
		updateHitbox();
	}
	
	/**
	 * draws the health using static drawQuad method with RGB
	 */
	public void drawHealth() {
		Draw.drawQuad(10, 10, health, 50, 255, 0, 0);
	}
}
