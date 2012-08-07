
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.TreeMap;

public class Groundmod2 {
	Polygon p;
	int[] xCoor = new int[1001];
	int[] yCoor = new int[1001];

	private TreeMap<Integer, Integer> points = new TreeMap<Integer, Integer>();

	public Groundmod2(int n) {
		getArrays(makePoints(n));
		this.p = new Polygon(xCoor, yCoor, xCoor.length);
	}

	private TreeMap<Integer, Integer> makePoints(int n) {
		for (int i = 1; i < 1000; i++) {
			points.put(i, 550);
		}

		switch (n) {
		case 1:
			break;
		case 2:
			int temp = 550;
			for (int i = 300; i < 450; i++) {
				points.put(i, temp);
				temp -= 1;
			}
			for (int i = 450; i < 600; i++) {
				points.put(i, temp);
				temp += 1;
			}
			break;
		case 3:
			temp = 550;
			for (int i = 150; i < 250; i++) {
				points.put(i, temp);
				temp -= 3;
			}
			for (int i = 250; i < 350; i++) {
				points.put(i, temp);
				temp += 3;
			}
			for (int i = 585; i < 700; i++) {
				points.put(i, temp);
				temp -= 2;
			}
			for (int i = 700; i < 815; i++) {
				points.put(i, temp);
				temp += 2;
			}
			break;
		case 4:
			temp = 550;
			int slope = (int) Math.random() * 100;
			int endslope = (int) Math.random() * 100 + 100;
			for (int i = slope; i < slope + endslope; i++) {
				points.put(i, temp);
				temp++;
			}
			for (int i = slope + endslope; i < slope + 2 * endslope; i++) {
				points.put(i, temp);
				temp--;
			}
			break;
		}

		points.put(0, 700);
		points.put(1000, 700);
		return points;
	}

	private void getArrays(TreeMap<Integer, Integer> t) {
		for (int i = 0; i < xCoor.length; i++) {
			xCoor[i] = i;
			yCoor[i] = t.get(i);
		}

	}

	public void removePoints(int x, int y, int radius) {
		int xMin = clip(x - radius / 2);
		int xMax = clip(x + radius / 2);
		int r = radius / 2;
		if (x < 1000) {
			for (int i = xMin; i <= xMax + 1; i++) {
				int tempY = points.get(i);
				if (!(tempY >= y)) {
					points.put(i, tempY + r);
					r += 0.5;
				}
			}
			getArrays(points);
			this.p = new Polygon(xCoor, yCoor, xCoor.length);
		}
	}

	private int clip(int x) {
		if (x < 0) {
			x = 0;
		}
		if (x > 1000) {
			x = 1000;
		}
		return x;
	}

	public void draw(Graphics g) {
		g.drawPolygon(p);
		g.setColor(Color.GREEN);
		g.fillPolygon(p);

	}
}
