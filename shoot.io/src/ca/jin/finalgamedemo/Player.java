package ca.jin.finalgamedemo;

import jgame.JGObject;

public class Player extends HealthBar {

	public static final String NAME = "zm";
	public static final String GRAPHICS = "zm";
	
	public static final int STARTING_HEALTH = 300;
	public static final int MAX_HEALTH = 300;
	
	//all regeneration will start after 10 seconds of getting damaged
	public static int regenSpeed = 5;
	
	//speed
	public static int maxSpeed = 8;
	public static double decay = maxSpeed/11.0;
	
	//increments by ten, use %100 or if statements to check progress
	//every 100 exp, you get level up
	public static int exp = 0;
	public static int level = 1;
	public static int upgradePoints = 5;
	public static int bodyDamage = 75;
		
	private Game game;	
	
	//shots speed
	public static int shootSpeed = 20;
			
	public Player(Game game, int x, int y) {
		super(game, Player.NAME, true, x, y, CollisionIds.PLAYER, Player.GRAPHICS, STARTING_HEALTH, MAX_HEALTH);
		this.game = game;
		xdir = 1;
		ydir = 1;
	}
	
	@Override
	public void hit(JGObject obj) {
		if (and(obj.colid, CollisionIds.ENEMY) || and(obj.colid, CollisionIds.SQUARE) 
				|| and(obj.colid, CollisionIds.TRIANGLE) || and(obj.colid, CollisionIds.PENTAGON)) {
			if (obj instanceof HealthBar) {
				HealthBar hb = (HealthBar) obj;
				hb.health -= bodyDamage;
				health -= 25;			
				if (and(obj.colid, CollisionIds.ENEMY)) {
					hb.xspeed = xspeed;
					hb.yspeed = yspeed;
					xspeed = -(xspeed / 2);
					yspeed = -(yspeed / 2);
				} else if (and(obj.colid, CollisionIds.SQUARE)) { 
					hb.xspeed = xspeed;
					hb.yspeed = yspeed;
					xspeed = -(xspeed / 2);
					yspeed = -(yspeed / 2);
				} else if (and(obj.colid, CollisionIds.TRIANGLE)) { 
					hb.xspeed = xspeed;
					hb.yspeed = yspeed;
					xspeed = -(xspeed / 2);
					yspeed = -(yspeed / 2);
				} else if (and(obj.colid, CollisionIds.PENTAGON)) { 
					hb.xspeed = xspeed;
					hb.yspeed = yspeed;
					xspeed = -(xspeed / 2);
					yspeed = -(yspeed / 2);
				}
				if (hb.health < 0) {
					obj.remove();
					if (and(obj.colid, CollisionIds.ENEMY)) {
						exp += 30;
					} else if (and(obj.colid, CollisionIds.SQUARE)) { 
						exp += 10;
					} else if (and(obj.colid, CollisionIds.TRIANGLE)) { 
						exp += 20;
					} else if (and(obj.colid, CollisionIds.PENTAGON)) { 
						exp += 50;
					}
				}
				if (health <= 0) {
					this.remove();
					game.lifeLost();
					level = 1;
					exp = 0;
					upgradePoints = 5;
					maxSpeed = 10;
					regenSpeed = 1;
					maxHealth = 150;
					Bullet.speed = 8; 
					Bullet.damage = 10;
				}
			}
		}
	}
}
