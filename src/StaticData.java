
import java.util.TreeMap;

public class StaticData {

	public static TreeMap<Integer, Projectile> projectiles = new TreeMap<Integer, Projectile>();

	public static void initialize() {
		projectiles.put(0, new SmallMissile());
		projectiles.put(1, new Missile());
	}

}
