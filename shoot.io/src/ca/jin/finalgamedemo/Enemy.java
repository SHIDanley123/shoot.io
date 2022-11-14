package ca.jin.finalgamedemo;

import jgame.JGObject;

public class Enemy extends HealthBar {
	public static final String NAME = "e";
	public static final String GRAPHICS = "e";
	public static final int STARTING_HEALTH = 150;
	public static final int ENEMY_MAX_HEALTH = 150;
	
	private Player m;
	
	public Enemy (Game game, int x, int y, Player m) {
		super(game, Enemy.NAME, true, x, y, CollisionIds.ENEMY, Enemy.GRAPHICS, STARTING_HEALTH, ENEMY_MAX_HEALTH);
		xdir = 1;
		ydir = 1;
		this.m = m;
	}
	
	@Override
	public void move() {
		super.move();	
		
		double dx = (m.getLastX() - getLastX());
		double dy = (m.getLastY() - getLastY(  ));
		double c = Math.sqrt(dx * dx + dy * dy);
		
		xspeed = 8 * dx / c;
		yspeed = 8 * dy / c;
		
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
