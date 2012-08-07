import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class Game {
	private Timer timer;
	final PlayingField field;
	final JFrame frame = new JFrame("Tanks");
	MoneyPanel shop1;
	MoneyPanel shop2;

	public Game() {
		StaticData.initialize();
		JButton help = new JButton("HELP!");

		frame.setLocation(50, 50);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		timer = new Timer(50, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				step();
			}
		});
		timer.start();

		help.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(
						frame,
						"Welcome. Your goal is to blow the other player up.\n\n"
								+ "Player 1 is the blue tank, Player 2 is the red.\n"
								+ "Up/Down arrows change the firing angle.\n"
								+ "Hold the F key to increase power and release to fire.\n"
								+ "Positive wind induces air resistance in <---- direction\n\n"
								+ "Blowing the other player up will net you more money to\n"
								+ "spend in the shop, subsequently allowing you to more easily\n"
								+ "blow the other player up and perhaps to blow the other player\n"
								+ "up in a different manner with bigger explosions and also to\n"
								+ "take less damage from subsequent attempts to blow you up.\n\n"
								+ "Enjoy.",
						"Help",
						JOptionPane.PLAIN_MESSAGE);
			}
		});

		field = new PlayingField();
		frame.add(field);

		frame.add(help, BorderLayout.NORTH);

		frame.pack();
		frame.setVisible(true);

		field.startGame();

	}

	private void step() {
		if (field.getTurn() == Turn.END) {
			field.setVisible(false);
			frame.pack();
			shop1 = new MoneyPanel(field.tank1, "Player 1");
			shop2 = new MoneyPanel(field.tank2, "Player 2");
			frame.add(shop1, BorderLayout.WEST);
			frame.add(shop2, BorderLayout.EAST);
			shop1.update();
			shop2.update();
			final JButton done = new JButton("done");
			done.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					field.setVisible(true);
					frame.remove(shop1);
					frame.remove(shop2);
					frame.remove(done);
					frame.add(field);
					field.startGame();
					field.setFocusable(true);
					field.grabFocus();
					field.requestFocus();
					frame.pack();
				}
			});
			frame.add(done, BorderLayout.CENTER);
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Game();
			}
		});
	}

}
