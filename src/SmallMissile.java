
import java.awt.Color;
import java.awt.Graphics;

public class SmallMissile extends Projectile {

	private Color color = Color.black;
	private static final int BLASTRADIUS = 20;
	private static final int RADIUS = 5;
	private static final int DAMAGE = 25;

	public SmallMissile() {
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
