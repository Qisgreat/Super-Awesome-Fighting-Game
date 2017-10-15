package outline;

public enum ItemID {
	
	Robot("textures/RobotIdleLeft"), Dino("textures/DinoIdleRight"), RobotPunch("textures/RobotPunchLeft"), DinoPunch("textures/DinoPunchRight"),
	RobotKick("textures/RobotKickLeft"), DinoKick("textures/DinoKickRight"), Floor("textures/platform.png");
	
	public String texture;
	
	ItemID(String texture) {
		this.texture = texture;
	}
}
