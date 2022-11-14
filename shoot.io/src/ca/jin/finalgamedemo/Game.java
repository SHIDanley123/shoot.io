package ca.jin.finalgamedemo;

import jgame.impl.EngineLogic.BGImage;
import java.util.Vector;

import ca.hapke.gyro.data.DataType.InputType;
import jgame.JGColor;
import jgame.JGFont;
import jgame.JGObject;
import jgame.JGPoint;
import jgame.JGRectangle;
import jgame.platform.StdGame;

/**
 * @author stanley.jin
 *
 */

public class Game extends StdGame {

	private static String BACKGROUNDIMAGE = "back";

	private static final int TILE_SIZE = 8;
	private static final int TILES_X = 100;
	private static final int TILES_Y = 100;

	private Player m;
	private Turret turret;

	private int timeUntilRegen;
	private int timeSpeedRegen;

	private int enemyTimer;
	private int squareTimer;
	private int triangleTimer;
	private int pentagonTimer;
	
	private JGColor red = new JGColor(255, 0, 0);
	private JGFont f = new JGFont("fart", 1, 15);
	
	private int bulletDelay = Player.shootSpeed;
	/**
	 * 
	 */

	public static void main(String[] args) {
		new Game();
	}

	public Game() {
		initEngine(TILES_X * TILE_SIZE, TILES_Y * TILE_SIZE);
	}

	@Override
	public void initCanvas() {
		setCanvasSettings(TILES_X, TILES_Y, TILE_SIZE, TILE_SIZE, null, null, null);
	}

	@Override
	public void initGame() {
		setFrameRate(30, 3);
		defineImage(Player.GRAPHICS, Player.NAME, CollisionIds.PLAYER, "PlayerBodyFinal.png", "-");
		defineImage(Turret.GRAPHICS, Turret.NAME, CollisionIds.TURRET, "zPlayerTurretFinal.png", "-");
		for (int i = 0; i <= 360; i++) {

			double rad = Math.toRadians(i);

			String tile = Turret.GRAPHICS + i;
			defineImageRotated(tile, tile, CollisionIds.TURRET, Turret.GRAPHICS, rad);

		}

		defineImage(Enemy.GRAPHICS, Enemy.NAME, CollisionIds.ENEMY, "enemy.png", "-");
		defineImage(Bullet.GRAPHICS, Bullet.NAME, CollisionIds.BULLET, "bullet.png", "-");
		
		
		defineImage(Square.GRAPHICS, Square.NAME, CollisionIds.SQUARE, "square.png", "-");
		for (int i = 0; i <= 360; i++) {

			double rad = Math.toRadians(i);

			String tile = Square.GRAPHICS + i;
			defineImageRotated(tile, tile, CollisionIds.SQUARE, Square.GRAPHICS, rad);

		}
		
		
		defineImage(Triangle.GRAPHICS, Triangle.NAME, CollisionIds.TRIANGLE, "triangle.png", "-");
		for (int i = 0; i <= 360; i++) {

			double rad = Math.toRadians(i);

			String tile = Triangle.GRAPHICS + i;
			defineImageRotated(tile, tile, CollisionIds.TRIANGLE, Triangle.GRAPHICS, rad);

		}

		
		
		
		defineImage(Pentagon.GRAPHICS, Pentagon.NAME, CollisionIds.PENTAGON, "pentagon.png", "-");
		for (int i = 0; i <= 360; i++) {

			double rad = Math.toRadians(i);

			String tile = Pentagon.GRAPHICS + i;
			defineImageRotated(tile, tile, CollisionIds.PENTAGON, Pentagon.GRAPHICS, rad);

		}
		

		defineImage(BACKGROUNDIMAGE, BACKGROUNDIMAGE, 0, "diepbg.png", "-");

		setBGImage(BACKGROUNDIMAGE);

		setPFSize(TILES_X * 3, TILES_Y * 3);
	}

	@Override
	public void initNewLife() {
		removeObjects(null, 0);

		m = new Player(this, (TILES_X * TILE_SIZE) / 2, (TILES_Y * TILE_SIZE) / 2);
		turret = new Turret(this, 100, 20, m);
	}

	@Override
	public void paintFrame() {
		super.paintFrame();

		if (m != null) {
			drawString("Level: " + Player.level, 13, 20, -1, f, red);
			drawString("Exp: " + Player.exp + "/" + 100 * Player.level, 13, 40, -1, f, red);
			drawString("HP: " + m.health, 13, 60, -1, f, red);
		
			drawString("Upgrade Points: " + Player.upgradePoints, 13, 580, -1, f, red);
			drawString("(X) Max Health: " + m.maxHealth, 13, 600, -1, f, red);
			drawString("(C) Regen Speed: " + Player.regenSpeed, 13, 620, -1, f, red);
			drawString("(V) Move Speed: " + Player.maxSpeed, 13, 640, -1, f, red);
			drawString("(B) Body Damage: " + Player.bodyDamage, 13, 660, -1, f, red);
			drawString("(N) Shoot Speed: " + Player.shootSpeed, 13, 680, -1, f, red);
			drawString("(M) Bullet Speed: " + Bullet.speed, 13, 700, -1, f, red);
			drawString("(L) Bullet Damage: " + Bullet.damage, 13, 720, -1, f, red);
		}
		
		setColor(JGColor.grey);
		drawString("Gyro Mode: " + getAccelGyroMode(), 100, 5, -1);
		
		
		
		
		boolean gyroActive = isSensorActive(InputType.JavaGyro);
		boolean joystickActive = isSensorActive(InputType.ADC);
		if (gyroActive) {
			setColor(JGColor.green);
		} else {
			setColor(JGColor.red);
		}
		drawString("JavaGyro Active? "  + gyroActive, 300, 5, -1);	

		if (joystickActive) {
			setColor(JGColor.green);
		} else {
			setColor(JGColor.red);
		}
		drawString("Joystick Active? "  + joystickActive, 300, 20, -1);
	}

	@Override
	public void doFrame() {
		super.doFrame();

		if (m != null) {
			// time until regen
			if (m.health < m.maxHealth) {
				timeUntilRegen++;
				if (timeUntilRegen >= 300) {
					timeSpeedRegen++;
					if (timeSpeedRegen >= 30) {
						m.health += m.regenSpeed;
						System.out.println("health:" + m.health);
						timeSpeedRegen = 0;
					}
					if (m.health >= m.maxHealth) {
						timeUntilRegen = 0;
					}
				}
			}

			// movement
			
			if (this.hasAccelerometer()) {
				
				
				
				double y = this.getAccelVec() [0] * 50;
				double x = this.getAccelVec() [1] * 50;
				
				System.out.println(x);
				System.out.println(y);
				
				m.xspeed = Math.min((Math.max(x,-m.maxSpeed)), m.maxSpeed);
				m.yspeed = Math.min((Math.max(y,-m.maxSpeed)), m.maxSpeed);
				
			} else {
				
				double speed = 2;
				double decay = Player.decay;
				double maxSpeed = Player.maxSpeed;
				
				if (getKey(key_left)) {
					m.xspeed -= speed;
				} else if (getKey(key_right)) {
					m.xspeed += speed;
				} else {
					if (Math.abs(m.xspeed) <= decay) {
						m.xspeed = 0;
					} else if (m.xspeed < 0) {
						m.xspeed += decay;
					} else if (m.xspeed > 0) {
						m.xspeed -= decay;
					}
				}
				m.xspeed = Math.max(-maxSpeed, Math.min(m.xspeed, maxSpeed));
				
				if (getKey(key_up)) {
					m.yspeed -= speed;
				} else if (getKey(key_down)) {
					m.yspeed += speed;
				} else {
					if (Math.abs(m.yspeed) <= decay) {
						m.yspeed = 0;
					} else if (m.yspeed < 0) {
						m.yspeed += decay;
					} else if (m.yspeed > 0) {
						m.yspeed -= decay;
					}
				}
				
				m.yspeed = Math.max(-maxSpeed, Math.min(m.yspeed, maxSpeed));
				
			}

			// camera/view field
			setViewOffset((int) m.x + 75, (int) m.y + 120, true);
			// borders
			if (m != null) {
				double mx = m.getLastX();
				double my = m.getLastY();
				int border = 10;
				int rightEdge = pfWidth() - 64;
				int bottomEdge = pfHeight() - 64;
				if (mx < 0 || mx > rightEdge || my < 0 || my > bottomEdge) {
					m.xspeed = -1 * m.xspeed;
					m.yspeed = -1 * m.yspeed;
					moveObjects(null, CollisionIds.PLAYER);
					while (m.x < border) {
						m.x++;
					}
					while (m.x > rightEdge) {
						m.x--;
					}
					while (m.y < border) {
						m.y++;
					}
					while (m.y > bottomEdge) {
						m.y--;
					}
				}

			}
			
			if (this.isSensorActive(InputType.ADC)) {
				bulletDelay--;
				if (bulletDelay < 0) {
					bulletDelay = Player.shootSpeed;	
					
					double x = this.getJoystickVec() [0];
					double y = this.getJoystickVec() [1];
					
					//bullet rotation with the turret
					double x4 = turret.x;
					double y4 = turret.y;
					
					double bx = x4 - 9 + (64*Math.sqrt(2)-32)/2;
					double by = y4 - 7 + (64*Math.sqrt(2)-32)/2;				
					
					Bullet f = new Bullet(this, (int) bx, (int) by);
	
					// where the ball is firing from
					double dx = (x-61);
					double dy = -(y -62);
					double c = Math.sqrt(dx * dx + dy * dy);
	
					double overall = Bullet.speed;
					f.xspeed = overall * dx / c;
					f.yspeed = overall * dy / c;
					
					m.xspeed = -(f.xspeed / 2);
					m.yspeed = -(f.yspeed / 2);
				}
			} else {
				bulletDelay--;
				if (getKey(KeyMouse1) && bulletDelay < 0) {
					bulletDelay = Player.shootSpeed;				
					
					//bullet rotation with the turret
					double x4 = turret.x;
					double y4 = turret.y;
					
					double bx = x4 - 9 + (64*Math.sqrt(2)-32)/2;
					double by = y4 - 7 + (64*Math.sqrt(2)-32)/2;				
					
					Bullet f = new Bullet(this, (int) bx, (int) by);

					// where the ball is firing from
					double dx = (getMouseX() + viewXOfs() - turret.x);
					double dy = (getMouseY() + viewYOfs() - turret.y);
					double c = Math.sqrt(dx * dx + dy * dy);

					double overall = Bullet.speed;
					f.xspeed = overall * dx / c;
					f.yspeed = overall * dy / c;
					
					m.xspeed = -(f.xspeed / 2);
					m.yspeed = -(f.yspeed / 2);
				}
			}
			
			
			double randomdegree = Math.random()*360;
			// object generation
			if (inGameState("InGame")) {
				enemyTimer++;
				squareTimer++;
				triangleTimer++;
				pentagonTimer++;

				if (enemyTimer == 1000) {
					enemyTimer = 0;
					JGPoint loc = generateRandomLocation();
					System.out.println(enemyTimer);
					new Enemy(this, loc.x, loc.y, m);
				}
				if (squareTimer == 50) {
					squareTimer = 0;
					JGPoint loc = generateRandomLocation();
					
					new Square(this, loc.x, loc.y, randomdegree).setGraphic(Square.GRAPHICS+(int) randomdegree);
				}
				if (triangleTimer == 75) {
					triangleTimer = 0;
					JGPoint loc = generateRandomLocation();
					new Triangle(this, loc.x, loc.y, randomdegree).setGraphic(Triangle.GRAPHICS+(int) randomdegree);
				}
				if (pentagonTimer == 100) {
					pentagonTimer = 0;
					JGPoint loc = generateRandomLocation();
					new Pentagon(this, loc.x, loc.y, randomdegree).setGraphic(Pentagon.GRAPHICS+(int) randomdegree);
				}
			}
		}
		moveObjects(null, 0);

		// exp system
		if (Player.exp >= 100 * Player.level) {
			Player.exp = 0;
			Player.upgradePoints++;
			Player.level++;
		}

		// [Power Up] Speed
		if (getKey('X') && Player.upgradePoints > 0) {
			m.maxHealth += 50;
			Player.upgradePoints--;

			System.out.print("upgrade points:" + Player.upgradePoints + "  |  ");
			System.out.println("max health:" + m.maxHealth);
			System.out.println("<---------------------->");
		}
		// [Power Up] Max Health
		if (getKey('C') && Player.upgradePoints > 0) {
			Player.regenSpeed += 5;
			Player.upgradePoints--;

			System.out.print("upgrade points:" + Player.upgradePoints + "  |  ");
			System.out.println("regen speed:" + Player.regenSpeed);
			System.out.println("<---------------------->");
		}

		// [Power up] regen increase
		if (getKey('V') && Player.upgradePoints > 0) {
			Player.maxSpeed += 2;
			Player.upgradePoints--;
			Player.decay = Player.maxSpeed / 11.0;

			System.out.print("upgrade points:" + Player.upgradePoints + "  |  ");
			System.out.println("max speed:" + Player.maxSpeed);
			System.out.println("<---------------------->");
		}
		// [Power up] bullet speed increase
		if (getKey('B') && Player.upgradePoints > 0) {
			Player.bodyDamage += 3;
			Player.upgradePoints--;

			System.out.print("upgrade points:" + Player.upgradePoints + "  |  ");
			System.out.println("body damage:" + Player.bodyDamage);
			System.out.println("<---------------------->");
		}

		// [Power up] bullet damage
		if (getKey('N') && Player.upgradePoints > 0) {
			Player.shootSpeed--;
			Player.upgradePoints--;

			System.out.print("upgrade points:" + Player.upgradePoints + "  |  ");
			System.out.println("reload:" + Player.shootSpeed);
			System.out.println("<---------------------->");
		}
		
		//[Power up] shooting speed (reload)
		if (getKey('M') && Player.upgradePoints > 0 && Player.shootSpeed>5) {
			Bullet.speed += 2;
			Player.upgradePoints--;

			System.out.print("upgrade points:" + Player.upgradePoints + "  |  ");
			System.out.println("bullet speed:" + Bullet.speed);
			System.out.println("<---------------------->");
		}

		//[Power up] body damage
		if (getKey('L') && Player.upgradePoints > 0) {
			Bullet.damage += 2;
			Player.upgradePoints--;

			System.out.print("upgrade points:" + Player.upgradePoints + "  |  ");
			System.out.println("bullet damage:" + Bullet.damage);
			System.out.println("<---------------------->");
		}
		
		moveObjects(null, CollisionIds.TURRET);

		checkCollision(CollisionIds.ENEMY, CollisionIds.PLAYER);
		checkCollision(CollisionIds.SQUARE, CollisionIds.PLAYER);
		checkCollision(CollisionIds.TRIANGLE, CollisionIds.PLAYER);
		checkCollision(CollisionIds.PENTAGON, CollisionIds.PLAYER);
		checkCollision(CollisionIds.ENEMY, CollisionIds.BULLET);
		checkCollision(CollisionIds.SQUARE, CollisionIds.BULLET);
		checkCollision(CollisionIds.TRIANGLE, CollisionIds.BULLET);
		checkCollision(CollisionIds.PENTAGON, CollisionIds.BULLET);
	}

	private JGPoint generateRandomLocation() {
		int x;
		int y;
		Vector<JGObject> targetLocation;

		do {
			x = random(0, TILES_X * TILE_SIZE * 3, 1);
			y = random(0, TILES_Y * TILE_SIZE * 3, 1);

			targetLocation = getObjects(null, 0, false, new JGRectangle(x, y, 128, 128));
		} while (targetLocation.size() > 0);

		return new JGPoint(x, y);
	}
}
