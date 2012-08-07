import java.awt.Color;
import java.awt.Graphics;

public class Missile extends Projectile {

	private Color color = Color.black;
	private static final int BLASTRADIUS = 38;
	private static final int RADIUS = 10;
	private static final int DAMAGE = 40;

	public Missile() {
		super(RADIUS, RADIUS);
		blastradius = BLASTRADIUS;
		damage = DAMAGE;
	}

	@Override
	public void draw(Graphics g) {
		if (visible)
			g.drawOval((int) x, (int) y, RADIUS, RADIUS);
		g.setColor(color);
		g.fillOval((int) x, (int) y, RADIUS, RADIUS);

	}
}
