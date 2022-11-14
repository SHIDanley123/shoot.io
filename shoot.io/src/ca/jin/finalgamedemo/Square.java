package ca.jin.finalgamedemo;

import jgame.JGObject;

public class Square extends HealthBar {
	public static final String NAME = "s";
	public static final String GRAPHICS = "s";
	public static final int STARTING_HEALTH = 100;
	public static final int SQUARE_MAX_HEALTH = 100;
	
	private int spindegree;
	
	public Square (Game game, int x, int y, double spindegree) {
		super(game, Square.NAME, true, x, y, CollisionIds.SQUARE, Square.GRAPHICS, STARTING_HEALTH, SQUARE_MAX_HEALTH);
		xdir = 1;
		ydir = 1;
		xspeed = 0;
		yspeed = 0;
		this.spindegree = (int) spindegree;
	}
	
	@Override
	public void move() {
		super.move();	
		
		if (spindegree == 360) {
			spindegree = 0;
		}
		//rotation
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
	
	//TODO override move method because it is always called, add the code there.
	
	
	
}
