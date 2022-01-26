package callOfDuty;

/**
 * A weapon type that shoots a 1x1 area.
 * Starts with 20 rounds by default.
 * @author josephpoirier
 *
 */
public class RocketLauncher extends Weapon {
	
	Base base;
	private static int INIT_SHOT_LEFT = 20;
	private static String AMMO_ICON = "↥";
	
	public RocketLauncher() {
		super(INIT_SHOT_LEFT);
	}
	
	@Override
	public String getWeaponType() {
		return "ROCKET_LAUNCHER";
	}

	/**
	 * Overrides method in superclass weapon
	 * Shoot at a given tile
	 * @param row, int number of row in base
	 * @param column, int number of column in base
	 * @param base, a base object
	 */
	@Override
	public void shootAt(int row, int column, Base base) {
		
		int shotLeft = getShotLeft();
		
		// only allow weapon to fire if ammo remains
		if (shotLeft > 0) {
			base.shootAt(row, column);
			
			// tabulate shots for weapon, base
			base.incrementShotsCount();
			decrementShotLeft();
		}
		
		else System.out.println("No Ammo!");
	}
	
	/**
	 * Print weapon name and ammo status to consule.
	 */
	public void printStatus() {
		
		int shotLeft = getShotLeft();
		System.out.println("\nRocket Launcher (" + shotLeft + ")");
		
		// print out number of bullets based on shot left
		for (int i = 0; i < shotLeft; i++) System.out.print(AMMO_ICON);
	}
}
