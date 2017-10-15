package outline;

import java.util.ArrayList;

import org.newdawn.slick.opengl.Texture;

import outline.Game;
import outline.Time;

import static outline.Draw.*;

public class Animation {
	
	private ArrayList<Texture> frames = new ArrayList<Texture>();
	private int fps;
	private int frame = 0;
	private Time time = new Time();
	private boolean finished = false;
	private boolean check = false;
	
	/**
	 * Animation constructor
	 * @param frames the frames of the animation
	 * @param fps the fps of the animation
	 */
	public Animation(ArrayList<Texture> frames, int fps) {
		this.frames = frames;
		this.fps = fps;
	}
	
	/**
	 * different constructor; takes in a path to frames instead of directly taking the frames
	 * @param path path to the frames of the animation
	 * @param fps the fps of the animation
	 */
	public Animation(String path, int fps) {
		this.fps = fps;
		int count = 0;
		do {
			Texture t;
			if((t = loadTexture(path + "/sprite_" + count + ".png", "PNG")) != null){
				frames.add(t);
				count++;
			}else {
				break;
			}
		}while(true);
	}
	
	/**
	 * checks is the firstRun has been done
	 * @return true or false bases on check
	 */
	public boolean checkFirstRun() {
		return check;
	}
	
	/**
	 * checks if the animation is done
	 * @return true or false based on finished
	 */
	public boolean isDone() {
		return finished;
	}
	
	/**
	 * updates the animation based on the frames and the time
	 */
	public void update() {
		if(frame > 0 && !check)
			check = true;
		if(check && frame == 0)
			finished = true;
		if(time.getTime() - time.getLastFrame() >= 1000/(fps * Game.GAME_TIME.getMulti())) {
			nextFrame();
			time.update();
		}
	}
	
	/**
	 * getter
	 * @return
	 */
	public Texture getCurrentFrame() {
		return frames.get(frame);
	}
	
	/**
	 * @return number of current frame
	 */
	public int getFrameNum() {
		return frame;
	}
	
	/**
	 * loops to next frame
	 */
	public void nextFrame() {
		frame++;
		loopFrame();
	}
	
	/**
	 * loops to previous frame
	 */
	public void lastFrame() {
		frame--;
		loopFrame();
	}
	
	/**
	 * loops frame
	 */
	public void loopFrame() {
		frame %= frames.size();
	}
	
	public Texture getFrame(int frame) {
		return frames.get(frame);
	}
	
	public void setFrame(int frame) {
		this.frame = frame;
		loopFrame();
	}
	
}
