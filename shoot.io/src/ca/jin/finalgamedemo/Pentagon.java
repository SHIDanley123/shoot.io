package ca.jin.finalgamedemo;

import jgame.JGObject;

public class Pentagon extends HealthBar {
	public static final String NAME = "p";
	public static final String GRAPHICS = "p";
	public static final int STARTING_HEALTH = 300;
	public static final int PENTAGON_MAX_HEALTH = 300;

	private int spindegree;
	
	public Pentagon(Game game, int x, int y, double spindegree) {
		super(game, Pentagon.NAME, true, x, y, CollisionIds.PENTAGON, Pentagon.GRAPHICS, STARTING_HEALTH, PENTAGON_MAX_HEALTH);
		xdir = 1;
		ydir = 1;
		xspeed = 0;
		yspeed = 0;
		this.spindegree = (int) spindegree;
	}

	@Override
	public void move() {
		super.move();	
		
		//rotation
		if (spindegree == 360) {
			spindegree = 0;
		}
		
		this.setGraphic(this.GRAPHICS+(int)spindegree);
		spindegree++;
		
		if (Math.abs(xspeed) <= Player.decay) {
			xspeed = 0;
		} else if (xspeed < 0) {
			xspeed += (Player.decay * 4);
		} else if (xspeed > 0) {
			xspeed -= Player.decay;
		}
		if (Math.abs(yspeed) <= Player.decay) {
			yspeed = 0;
		} else if (yspeed < 0) {
			yspeed += (Player.decay * 4);
		} else if (yspeed > 0) {
			yspeed -= Player.decay;
		}
	}
}
