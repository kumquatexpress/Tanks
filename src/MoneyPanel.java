import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MoneyPanel extends JPanel {
	private Tanks tank;
	private String title;
	JLabel money = new JLabel();
	JLabel ar = new JLabel();

	public MoneyPanel(Tanks t, String s) {
		tank = t;
		title = s;
		JButton missile = new JButton("missile : $5000");
		missile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tank.money >= 5000) {
					tank.money -= 5000;
					tank.projectileType = 1;
				}
			}
		});
		JButton armor = new JButton("armor + 5 : $10000");
		armor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tank.money >= 10000) {
					tank.armor += 5;
					tank.money -= 10000;
				}
			}
		});
		this.add(ar);
		this.setLayout(new BorderLayout());
		this.setBackground(Color.WHITE);
		this.add((new JLabel(title)), BorderLayout.NORTH);
		this.add(money, BorderLayout.SOUTH);
		this.add(missile, BorderLayout.WEST);
		this.add(armor);

	}

	public void update() {
		money.setText("Cash Money: " + tank.money);
		ar.setText("Armor: " + tank.armor);
	}

}
