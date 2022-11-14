package ca.jin.finalgamedemo;

import jgame.JGColor;
import jgame.JGObject;

public abstract class HealthBar extends JGObject {
	protected Game game;
	private static final int HP_BAR_HEIGHT = 14;
	private static final int HP_BAR_SPACER = 3;
	private static final int HP_BAR_WIDTH = 32;
	public int health;
	public int maxHealth;
	
	public HealthBar (Game game, String name, boolean unique_id, double x, double y, int collisionid, String gfxname,
			int startingHealth, int maxHealth) {
		super(name, unique_id, x, y, collisionid, gfxname);
		this.game = game;
		this.health = startingHealth;
		this.maxHealth = maxHealth;
	}
	 
	@Override
	public void paint() {
		super.paint();
		
		game.setColor(JGColor.white);
		
		double pct = ((double) health) / maxHealth;
		
		if (pct >= 0.5) {
			game.setColor(JGColor.green);
		} else if (pct >= 0.25) {
			game.setColor(JGColor.yellow);
		} else {
			game.setColor(JGColor.red);
		}
		game.drawRect(x + 30, y - HP_BAR_HEIGHT - HP_BAR_SPACER + 30, HP_BAR_WIDTH * pct, HP_BAR_HEIGHT, true, false);
		game.setColor(JGColor.white);
		game.drawRect(x + 30, y - HP_BAR_HEIGHT - HP_BAR_SPACER + 30, HP_BAR_WIDTH, HP_BAR_HEIGHT, false, false);
	} 
}
