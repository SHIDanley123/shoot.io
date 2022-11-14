package ca.jin.finalgamedemo;

import ca.hapke.gyro.data.DataType.InputType;
import jgame.JGColor;
import jgame.JGObject;

public class Turret extends JGObject {
	public static final String NAME = "t";
	public static final String GRAPHICS = "t";
	private Game game;
	private Player m;

	public Turret(Game game, int x, int y, Player m) {
		super(Turret.NAME, true, x, y, CollisionIds.TURRET, Turret.GRAPHICS);
		this.game = game;
		this.m = m;
		xdir = 1;
		ydir = 1;
	}

	@Override
	public void move() {
		super.move();

		if (game.isSensorActive(InputType.ADC)) {
			
			double dxTurret = (game.getJoystickVec() [0] -61);
			double dyTurret = -(game.getJoystickVec() [1] -62);

			double rTurretLength = 64;
			double rTurret = 32;
			double rTurretShortened = 28;
			
			
			double theta = Math.atan2(dyTurret, dxTurret);
			double cosTurret = Math.cos(theta);
			double sinTurret = Math.sin(theta);
			
			double x1 = m.x;
			double y1 = m.y;

			double x2 = x1 + rTurret;
			double y2 = y1 + rTurret;

			double x3 = x2 + rTurret * cosTurret;
			double y3 = y2 + rTurret * sinTurret;

			double x4 = x3 - ((rTurretLength * Math.sqrt(2)) / 2);
			double y4 = y3 - ((rTurretLength * Math.sqrt(2)) / 2);
			
			double degree = Math.toDegrees(theta);
			
			// System.out.println(degree);

			if (degree <= 0) {
				degree += 360;
				this.setGraphic(Turret.GRAPHICS + (int) degree);
			} else {
				this.setGraphic(Turret.GRAPHICS + (int) degree);
			}

			this.x = x4 + 10;
			this.y = y4 + 10;

		} else {
			
			double dxTurret = (game.getMouseX() + game.viewXOfs() - m.x);
			double dyTurret = (game.getMouseY() + game.viewYOfs() - m.y);

			double rTurretLength = 64;
			double rTurret = 32;
			double rTurretShortened = 28;
			
			
			double theta = Math.atan2(dyTurret, dxTurret);
			double cosTurret = Math.cos(theta);
			double sinTurret = Math.sin(theta);
			
			double x1 = m.x;
			double y1 = m.y;

			double x2 = x1 + rTurret;
			double y2 = y1 + rTurret;

			double x3 = x2 + rTurret * cosTurret;
			double y3 = y2 + rTurret * sinTurret;

			double x4 = x3 - ((rTurretLength * Math.sqrt(2)) / 2);
			double y4 = y3 - ((rTurretLength * Math.sqrt(2)) / 2);
			
			double degree = Math.toDegrees(theta);
			
			// System.out.println(degree);

			if (degree <= 0) {
				degree += 360;
				this.setGraphic(Turret.GRAPHICS + (int) degree);
			} else {
				this.setGraphic(Turret.GRAPHICS + (int) degree);
			}

			this.x = x4 + 10;
			this.y = y4 + 10;

			
			
		}
		
		
	}

	@Override
	public void paint() {
		super.paint();
		game.setColor(JGColor.red);
		game.drawLine(x - 2, y, x + 2, y, true);
		game.drawLine(x, y - 2, x, y + 2, true);
	}
}
