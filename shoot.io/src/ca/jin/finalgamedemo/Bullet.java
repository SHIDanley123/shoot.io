package ca.jin.finalgamedemo;

import jgame.JGObject;

public class Bullet extends JGObject {
	public static final String NAME = "b";
	public static final String GRAPHICS = "b";
	public static int speed = 8;
	public static int damage = 15;
	private Game game;

	public Bullet(Game game, int x, int y) {
		super(Bullet.NAME, true, x, y, CollisionIds.BULLET, Bullet.GRAPHICS);
		this.game = game;
		xdir = 1;
		ydir = 1;
	}

	@Override
	public void hit(JGObject obj) {
		if (and(obj.colid, CollisionIds.ENEMY) || and(obj.colid, CollisionIds.SQUARE) || and(obj.colid, CollisionIds.TRIANGLE) || and(obj.colid, CollisionIds.PENTAGON)) {
			if (obj instanceof HealthBar) {
				HealthBar hb = (HealthBar) obj;
				hb.health -= damage;
				if (hb.health < 0) {
					obj.remove();
					if (and(obj.colid, CollisionIds.ENEMY)) {
						Player.exp += 30;
					} else if (and(obj.colid, CollisionIds.SQUARE)) { 
						Player.exp += 10;
					} else if (and(obj.colid, CollisionIds.TRIANGLE)) { 
						Player.exp += 20;
					} else if (and(obj.colid, CollisionIds.PENTAGON)) { 
						Player.exp += 50;
					}
				}
			}
			this.remove();
		}
	}
}
