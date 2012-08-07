import java.awt.Color;
import java.awt.Graphics;

public class Tanks extends PhysicsObjs {
	final static int MASS = 500;
	final static int WIDTH = 20;
	final static int HEIGHT = 20;
	int money = 0;
	int armor = 0;
	int fuel;
	int health;
	int projectileType = 0;
	boolean undamageable = false;

	Color color;

	public Tanks(Color c) {
		super(MASS, WIDTH, HEIGHT);
		health = 100;
		fuel = 300;
		this.color = c;
	}

	public void moveLeft(Groundmod2 g) {
		if (!this.outsideBounds() && !this.hitGround(g)
				&& this.hasFuel()) {
			this.x += -2.0;
			fuel -= 2;
		}

	}

	public void moveRight(Groundmod2 g) {
		if (!this.outsideBounds() && !this.hitGround(g)
				&& this.hasFuel()) {
			this.x += 2.0;
			fuel -= 2;
		}
	}

	private boolean hasFuel() {
		return this.fuel > 0;
	}

	public boolean hitGround(Groundmod2 g) {
		return (g.p.contains(x, y + h - 1) || g.p.contains(x + w, y + h
				- 1));
	}

	public Projectile fire(double n, double direction) {
		Projectile missile = StaticData.projectiles.get(projectileType);
		missile.visible = true;
		missile.create(x + 10, y - 10, 0, 0);
		missile.ForceExerted(n * 3, -direction + 180);
		missile.v_y += 5;
		return missile;
	}

	public boolean isDestroyed() {
		return (health <= 0);
	}

	public void boomDestroyed(Graphics g) {
		this.undamageable = true;
		g.setColor(color);
		g.drawOval((int) x - 50, (int) y - 50, 100, 100);
		g.fillOval((int) x - 50, (int) y - 50, 100, 100);
	}

	public void setDefaults() {
		this.health = 100;
		this.fuel = 300;
		this.undamageable = false;
	}

	@Override
	public void draw(Graphics g) {
		g.draw3DRect((int) x, (int) y, WIDTH, HEIGHT, true);
		g.setColor(color);
		g.fill3DRect((int) x, (int) y, WIDTH, HEIGHT, true);
	}

}
