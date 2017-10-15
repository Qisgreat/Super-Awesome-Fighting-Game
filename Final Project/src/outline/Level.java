package outline;

import java.util.ArrayList;
import items.Floor;
import items.Robot;
import items.Dino;

public class Level {
	
	private ArrayList<Item> objs = new ArrayList<Item>();
	private static Floor flr = new Floor();
	
	public Level() {
		objs.add(flr);
		
		Dino p1 = new Dino(0, 194, 256, 256);
		Integrator.setPlayer1(p1);
		objs.add(p1);
		
		Robot p2 = new Robot(768, 194, 256, 256);
		Integrator.setPlayer2(p2);
		objs.add(p2);
		
	}
	
	public ArrayList<Item> getObjs() {
		return objs;
	}
	
	public static Floor getFloor() {
		return flr;
	}
}
