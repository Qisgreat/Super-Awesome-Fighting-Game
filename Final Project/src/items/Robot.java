package items;

import static outline.Game.GAME_TIME;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.Rectangle;

import outline.Integrator;
import outline.Level;
import outline.Animation;
import outline.ItemID;
import outline.Draw;

public class Robot extends Player {

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
	
	public Robot(float x, float y, float width, float height) {
		super(x, y, width, height, ItemID.Robot);						//below are all the animations for Robot
		run_left = new Animation("textures/RobotRunLeft", 12);
		run_right = new Animation("textures/RobotRunRight", 12);
		punch_left = new Animation("textures/RobotPunchLeft", 48);
		punch_right = new Animation("textures/RobotPunchRight", 48);
		kick_left = new Animation("textures/RobotKickLeft", 24);
		kick_right = new Animation("textures/RobotKickRight", 24);
		idle_left = new Animation("textures/RobotIdleLeft", 0);
		idle_right = new Animation("textures/RobotIdleRight", 0);
		duck_left = new Animation("textures/RobotDuckLeft", 15);
		duck_right = new Animation("textures/RobotDuckRight", 15);
	}
	
	/**
	 * checks key presses by player to move character and attack with them
	 */
	public void tick() {
		if(current != null)
			current.update();
		
		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {		//if right arrow pressed
			current = run_right;							//animate moving right
			xSpeed = PLAYER_SPEED;							//move right
			direction = 1;									//set direction to the right
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {			//if left arrow pressed
			current = run_left;								//animate moving left
			xSpeed = -PLAYER_SPEED;							//move left
			direction = -1;									//set direction to the left
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_UP)) {			//if up arrow pressed
			if(!jumping) {									//and not already jumping
				jumping = true;								//set jumping to true
				ySpeed = -0.5f;								//and then apply negative y speed to go up into the air
			}
		}
		
		if(!Keyboard.isKeyDown(Keyboard.KEY_LEFT) && !Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			switch(direction)								//if neither character is moving, both are idl
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
		
//		if(!Keyboard.isKeyDown(Keyboard.KEY_DOWN) && ducking){
//			hitbox = new Rectangle((int) (x + width / 4), (int) (y + height / 4), (int) (width / 3), (int)(3 * height / 5));
//			ducking = false;
//		}
//		
//		if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
//			hitbox = new Rectangle((int) (x + width / 4), (int) (y - height), (int) (width / 3), (int)(3 * height / 10));
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
		
		if(Keyboard.isKeyDown(Keyboard.KEY_L)) {				//allows characters to punch, creats hitboxes for the punches
			switch(direction) {
			case -1:
				current = punch_left;
				if(current.getFrameNum() == 15) {
					RobotPunch pLeft = new RobotPunch(x + (3 * width / 12) - 50 , y + height / 2, ItemID.RobotPunch);
					pLeft.render();
					if(Integrator.getPlayer1().getHitBox().intersects(pLeft.getHitBox())) {
						Integrator.getPlayer1().setHealth(pLeft.getPower());
						Integrator.getPlayer1().setX(Integrator.getPlayer1().getX() - 150);
					}
				}
				break;
			case 1:
				current = punch_right;
				if(current.getFrameNum() == 15) {
					RobotPunch pRight = new RobotPunch(x + 9 * width / 12, y + height / 2, ItemID.RobotPunch);
					pRight.render();
					if(Integrator.getPlayer1().getHitBox().intersects(pRight.getHitBox())) {
						Integrator.getPlayer1().setHealth(pRight.getPower());
						if(Integrator.getPlayer1().getX() + 150 > Draw.WIDTH - Integrator.getPlayer1().getWidth())
							Integrator.getPlayer1().setX(Draw.WIDTH - width);
						else
							Integrator.getPlayer1().setX(Integrator.getPlayer1().getX() + 150);
					}
				}
				break;
			}
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_K)) {				//same as punches but for kicks
			switch(direction) {
			case -1:
				current = kick_left;
				if(current.getFrameNum() == 12) {
					RobotPunch kLeft = new RobotPunch(x + (3 * width / 12) - 50 , y + 3 * height / 4, ItemID.RobotKick);
					kLeft.render();
					if(Integrator.getPlayer1().getHitBox().intersects(kLeft.getHitBox())) {
						Integrator.getPlayer1().setHealth(kLeft.getPower());
						Integrator.getPlayer1().setX(Integrator.getPlayer1().getX() - 75);
					}
				}
				break;
			case 1:
				current = kick_right;
				if(current.getFrameNum() == 12) {
					RobotKick kRight = new RobotKick(x + 9 * width / 12, y + 3 * height / 4, ItemID.RobotKick);
					kRight.render();
					if(Integrator.getPlayer1().getHitBox().intersects(kRight.getHitBox())) {
						Integrator.getPlayer1().setHealth(kRight.getPower());
						if(Integrator.getPlayer1().getX() + 150 > Draw.WIDTH - Integrator.getPlayer1().getWidth())
							Integrator.getPlayer1().setX(Draw.WIDTH - width);
						else
							Integrator.getPlayer1().setX(Integrator.getPlayer1().getX() + 75);
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
			x+=xSpeed*GAME_TIME.Delta();
		else if(x + xSpeed*GAME_TIME.Delta() < 0)
			x = 0;
		else if(x + xSpeed*GAME_TIME.Delta() > Draw.WIDTH)
			x = Draw.WIDTH - width;
		
		if(y + ySpeed*GAME_TIME.Delta() < Level.getFloor().getY() - height)
			y+=ySpeed*GAME_TIME.Delta();
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
		Draw.drawQuad(564, 10, health, 50, 255, 0, 0);
	}
}

