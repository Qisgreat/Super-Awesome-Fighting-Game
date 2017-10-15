package outline;

import org.lwjgl.Sys;

public class Time {
	
	private boolean isPaused = false;	//is time paused?
	public long lastFrame = 0;			//time value of the last update
	public long totalTime = 0;			//total time
	private boolean firstUpdate = true;	//is firstUpdate? avoid delta errors
	public float d = 0;					//delta value
	public float multi = 1;				//speed multiplier
	
	public void update() {				//updates the delta value and the total time
		 d = getDelta();
		 totalTime += d;
	}
	
	public long getTime() {				//gets the current time in milliseconds
		return Sys.getTime() * 1000 / Sys.getTimerResolution();
	}
	
	public long getLastFrame() {		//returns the last frame
		return lastFrame;
	}
	
	public float getDelta() {			//returns the diff. between the current time and the last time it was updated
		int delta = (int) (getTime() - lastFrame);
		lastFrame = getTime();			//updates the time of the last frame
		return delta;
	}
	
	public void reset() {
		totalTime = 0;
		lastFrame = 0;
		firstUpdate = true;
		d =0;
	}
	
	public float Delta() {
		if(isPaused || firstUpdate) {	//id the game is paused or if its first update
			firstUpdate = false;		//sets the first update to false
			return 0;
		}else
			return d * multi;			//returns the delta multiplied by the multiplier to affect the speed
	}
	
	/**
	 * @return returns total time
	 */
	public long getTotalTime() {
		return totalTime;
	}
	
	/**
	 * @return returns multiplier
	 */
	public float getMulti() {
		return multi;
	}
	
	/**
	 * changes the speed
	 * @param s
	 */
	public void changeSpeed(int s) {
		if(s > -1 && s < 5)
			multi = s;
	}
	
	/**
	 * pauses and unpauses
	 */
	public void pause() {
		isPaused = !isPaused;
	}
		
}
