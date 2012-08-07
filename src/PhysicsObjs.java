import java.awt.Graphics;

public abstract class PhysicsObjs {
	double m;
	int w;
	int h;

	double x;
	double y;

	boolean visible = false;

	double v_x;
	double v_y;
	double a_x;
	double a_y;

	private double T_v_x = 13;
	private double T_v_y = 17;

	public PhysicsObjs(double m, int w, int h) {
		this.m = m;
		this.w = w;
		this.h = h;
	}

	public void create(double x, double y, double v_x, double v_y) {
		this.x = x;
		this.y = y;
		this.v_x = v_x;
		this.v_y = v_y;
	}

	public boolean outsideBounds() {
		return (this.x > 1000 || this.x < 0 || this.y > 700);
	}

	public void reset() {
		this.v_x = 0;
		this.v_y = 0;
		this.a_x = 0;
		this.a_y = 0;
		visible = false;
	}

	public boolean collided(Groundmod2 g) {
		return (g.p.contains(this.x, this.y + h) || g.p.contains(this.x
				+ w, this.y + h));
	}

	public void ForceExerted(double n, double direction) {
		a_x += (n * Math.cos(Math.toRadians(direction + 180))) / m;
		a_y += (n * Math.sin(Math.toRadians(direction + 180))) / m;
	}

	public void terminalVelocity() {
		if (Math.abs(v_x) > T_v_x) {
			if (v_x < 0) {
				v_x = -T_v_x;
			} else
				v_x = T_v_x;
		}
		if (Math.abs(v_y) > T_v_y) {
			if (v_y < 0) {
				v_y = -T_v_y;
			} else
				v_y = T_v_y;
		}
	}

	public void move() {
		x += v_x;
		y += v_y;
		v_x += a_x;
		v_y += a_y;
	}

	public abstract void draw(Graphics g);

}
