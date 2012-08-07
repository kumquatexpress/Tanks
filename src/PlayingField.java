import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class PlayingField extends JPanel {

	// Creates the ground polygon and background
	public int difficulty = 3;
	private Groundmod2 ground = new Groundmod2(difficulty);

	// Colors of the two tanks

	public Color color1 = Color.BLUE;
	public Color color2 = Color.red;

	// All PhysObjs that are likely to be created in the instance of a game

	Tanks tank1 = new Tanks(color1);
	Tanks tank2 = new Tanks(color2);
	private Projectile missile = new SmallMissile();
	private Explosion explode;

	// Whose turn it is and a method to change turns after each round

	public Turn turn = Turn.PLAYER1;

	void changeTurn() {
		if (turn == Turn.PLAYER1) {
			turn = Turn.PLAYER2;
		} else
			turn = Turn.PLAYER1;
	}

	// Variables for initializing the panel and timer

	private int interval = 40;
	private Timer timer;

	final int FIELDWIDTH = 1000;
	final int FIELDHEIGHT = 700;

	private DisplayPanel p1;
	private DisplayPanel p2;

	private JPanel toolbar = new JPanel(new BorderLayout());

	private JLabel air;
	private JLabel playerturn;

	// All Physics-affected Objects on map in this List
	public LinkedList<PhysicsObjs> objects = new LinkedList<PhysicsObjs>();

	// Air resistance and gravity functions, plus the method that is called
	// each
	// step for gravity's effects.

	private double airResistance = Math.random() * 42 - 21;

	private void gravity() {
		for (PhysicsObjs s : objects) {
			if (!s.collided(ground)) {
				s.ForceExerted(9.81 * s.m / 15, 270);
				s.ForceExerted(airResistance * Math.abs(s.v_x)
						/ 30, 360);
				s.terminalVelocity();
			} else {
				s.a_y = 0;
				s.v_y = 0;
				s.a_x = 0;
				s.a_y = 0;
			}
		}
	}

	// Finally the constructor

	public PlayingField() {
		this.setBackground(Color.CYAN);

		setPreferredSize(new Dimension(FIELDWIDTH, FIELDHEIGHT));
		setBorder(BorderFactory.createEtchedBorder(Color.BLUE,
				Color.BLACK));
		setFocusable(true);

		air = new JLabel("Wind: " + (int) airResistance, JLabel.CENTER);
		air.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));

		playerturn = new JLabel("TURN: " + turn.toString(),
				JLabel.CENTER);
		playerturn.setFont(new Font("Arial", Font.BOLD, 17));
		playerturn.setBorder(BorderFactory.createLineBorder(Color.RED,
				4));

		p1 = new DisplayPanel();
		p1.setBackground(Color.CYAN);
		p1.changeLabel(p1.name, "Player 1");
		p1.name.setForeground(Color.white);
		p1.name.setBackground(color1);
		p1.health = tank1.health;

		p2 = new DisplayPanel();
		p2.setBackground(Color.CYAN);
		p2.changeLabel(p2.name, "Player 2");
		p2.name.setBackground(color2);
		p2.name.setForeground(Color.white);
		p2.health = tank2.health;

		toolbar.add(p1, BorderLayout.NORTH);
		toolbar.add(air, BorderLayout.EAST);
		toolbar.add(playerturn, BorderLayout.CENTER);
		toolbar.add(p2, BorderLayout.SOUTH);

		this.add(toolbar);

		timer = new Timer(interval, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				step();
			}
		});
		timer.start();

		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (turn == Turn.PLAYER1) {
					if (e.getKeyCode() == KeyEvent.VK_LEFT) {
						tank1.moveLeft(ground);
					}
					if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
						if (tank1.x < 981) {
							tank1.moveRight(ground);
						} else
							tank1.x = 980;
					}
					if (e.getKeyCode() == KeyEvent.VK_F) {
						if (p1.n < 100) {
							p1.n++;
						}
					}
					if (e.getKeyCode() == KeyEvent.VK_UP) {
						p1.direction++;
					}
					if (e.getKeyCode() == KeyEvent.VK_DOWN) {
						p1.direction--;
					}

				} else {
					if (e.getKeyCode() == KeyEvent.VK_LEFT) {
						tank2.moveLeft(ground);
					}
					if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
						if (tank2.x < 981) {
							tank2.moveRight(ground);
						} else
							tank2.x = 980;
					}
					if (e.getKeyCode() == KeyEvent.VK_F) {
						if (p2.n < 100) {
							p2.n++;
						}
					}
					if (e.getKeyCode() == KeyEvent.VK_UP) {
						p2.direction++;
					}
					if (e.getKeyCode() == KeyEvent.VK_DOWN) {
						p2.direction--;
					}
				}
			}

			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_F
						&& turn == Turn.PLAYER1) {
					double lastpower = p1.n;
					p1.lastpower.setText("Last Power: "
							+ lastpower);
					tank1.fire(p1.n, p1.direction);
					missile = tank1.fire(p1.n, p1.direction);
					objects.add(missile);
					p1.n = 0;
					changeTurn();
				} else if (e.getKeyCode() == KeyEvent.VK_F
						&& turn == Turn.PLAYER2) {
					double lastpower = p2.n;
					p2.lastpower.setText("Last Power: "
							+ lastpower);
					missile = tank2.fire(-p2.n,
							-p2.direction);
					tank2.fire(-p2.n, -p2.direction);
					objects.add(missile);
					p2.n = 0;
					changeTurn();
				}
				tank1.v_x = 0;
				tank2.v_x = 0;

			}

		});
	}

	// This creates the two tanks and officially starts a game.

	public void startGame() {
		tank1.create(20, 530, 0, 0);
		tank1.setDefaults();
		tank2.create(960, 530, 0, 0);
		tank2.setDefaults();
		objects.add(tank1);
		objects.add(tank2);
		ground = new Groundmod2((int) (Math.random() * 4));
		turn = Turn.PLAYER1;
		grabFocus();
	}

	public void updateWind() {
		airResistance = Math.random() * 42 - 21;
		air.setText("Wind: " + (int) airResistance);
	}

	void step() {
		gravity();
		tank1.move();
		if (tank1.hitGround(ground)) {
			tank1.y--;
			tank1.fuel--;
		}
		tank2.move();
		if (tank2.hitGround(ground)) {
			tank2.y--;
			tank1.fuel--;
		}
		missile.move();
		this.requestFocus();
		repaint();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (missile.collided(ground)) {
			objects.remove(missile);
			missile.explode((int) missile.x, (int) missile.y,
					missile.blastradius, tank1);
			explode = missile.explode((int) missile.x,
					(int) missile.y, missile.blastradius,
					tank2);
			animate();
			ground.removePoints((int) missile.x, (int) missile.y,
					missile.blastradius);
			missile.reset();
		}
		if (missile.outsideBounds()) {
			missile.reset();
			objects.remove(missile);
		}
		for (PhysicsObjs s : objects) {
			s.draw(g);
		}
		if (tank1.outsideBounds()) {
			tank1.x = tank1.x + 2;
		}
		if (tank2.outsideBounds()) {
			tank2.x = tank2.x + 2;
		}
		if (tank1.isDestroyed() && !tank1.undamageable) {
			tank1.boomDestroyed(g);
			tank1.money += 2500;
			tank2.money += 10000;
			objects.remove(tank1);
			turn = Turn.END;
		}
		if (tank2.isDestroyed() && !tank2.undamageable) {
			tank2.money += 2500;
			tank1.money += 10000;
			tank2.boomDestroyed(g);
			objects.remove(tank2);
			turn = Turn.END;
		}
		p1.update(tank1);
		p2.update(tank2);
		playerturn.setText("Turn: " + turn.toString());
		ground.draw(g);

	}

	private void animate() {
		Timer time = new Timer(450, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				objects.remove(explode);
				updateWind();
			}
		});
		time.start();
		time.setRepeats(false);
	}

	public Turn getTurn() {
		return turn;
	}

}

abstract class Projectile extends PhysicsObjs {
	protected int blastradius;
	protected int damage;

	private static final double MASS = 20.0;
	boolean visible = false;

	public Projectile(int w, int h) {
		super(MASS, w, h);
	}

	public boolean collided(Groundmod2 g) {
		return (g.p.contains(this.x, this.y));
	}

	public Explosion explode(int x, int y, int radius, Tanks t) {
		Explosion ex = new Explosion();
		ex.create(x, y, radius);
		ex.doDamage(damage, t);
		return ex;
	}

	public abstract void draw(Graphics g);

}

class Explosion extends PhysicsObjs {
	int x;
	int y;
	int radius;
	Color color = Color.red;

	public Explosion() {
		super(0, 0, 0);
	}

	public void create(int x, int y, int radius) {
		this.x = x;
		this.y = y;
		this.radius = radius;
	}

	public void doDamage(int damage, Tanks o) {
		if (this.collidesWith(o) && !o.undamageable) {
			o.health -= damage - o.armor;
		}
	}

	private boolean collidesWith(Tanks o) {
		return ((x + radius + 5 > o.x && x - radius - 5 < o.x) && (y
				+ radius + 10 > o.y && y - radius - 10 < o.y));
	}

	public void draw(Graphics g) {
		g.drawOval(x - radius / 2, y - radius / 2 - 3, radius, radius);
		g.fillOval(x - radius / 2, y - radius / 2 - 3, radius, radius);
	}
}

@SuppressWarnings("serial")
class DisplayPanel extends JPanel {
	double n = 0;
	double direction = 0;
	int health;

	JLabel powLabel = new JLabel("Power: " + Double.toString(n));
	JLabel angLabel = new JLabel("Direction: " + Double.toString(direction));
	JLabel healthLabel = new JLabel("Health : " + health);
	JLabel name = new JLabel();
	JLabel lastpower = new JLabel("Last Power: ");
	JLabel fuel = new JLabel("Fuel :");
	JLabel money = new JLabel("Cash : ");

	public DisplayPanel() {
		powLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK,
				2));
		angLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK,
				2));
		lastpower.setBorder(BorderFactory.createLineBorder(Color.BLACK,
				2));

		healthLabel.setOpaque(true);
		healthLabel.setBackground(Color.PINK);

		name.setOpaque(true);

		this.add(name);
		this.add(powLabel);
		this.add(angLabel);
		this.add(healthLabel);
		this.add(lastpower);
		this.add(fuel);
		this.add(money);
	}

	public void changeLabel(JLabel j, String s) {
		j.setText(s);
	}

	public void update(Tanks t) {
		powLabel.setText("Power : " + Double.toString(n));
		angLabel.setText("Angle : " + Double.toString(direction));
		healthLabel.setText("Health : " + t.health);
		fuel.setText("Fuel : " + t.fuel);
		money.setText("Cash : " + t.money);
	}
}
