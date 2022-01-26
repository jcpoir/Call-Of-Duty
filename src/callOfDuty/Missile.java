package callOfDuty;

/**
 * A weapon type that will shoot a 3x3 area.
 * Starts with three rounds by default.
 * @author josephpoirier
 *
 */
public class Missile extends Weapon {
	
	Base base;
	private static int INIT_SHOT_LEFT = 3;
	private static String AMMO_ICON = "⥣";
	
	public Missile() {
		super(INIT_SHOT_LEFT);
	}
	
	@Override
	public String getWeaponType() {
		return "MISSILE";
	}
	
	/**
	 * Overrides method in superclass weapon
	 * Loops through 3x3 region shooting at targets
	 * @param row, int number of row in base
	 * @param column, int number of column in base
	 * @param base, base object
	 */
	@Override
	public void shootAt(int row, int column, Base base) {
		
		int shotLeft = getShotLeft();
		
		if (shotLeft > 0) {
			
			// shoot at a 3 x 3 area by shooting 9 individual times
			for (int i = row - 1; i <= row + 1; i++) for (int j = column - 1; j <= column + 1; j++) {
				base.shootAt(i, j);
				
			}
			
			// increment base total shots count (once only)
			base.incrementShotsCount();
			// decrement ammo
			decrementShotLeft();
		
		}
		
		else System.out.println("No ammo!");
	}
	
	/**
	 * Print weapon name and ammo status to consule.
	 */
	public void printStatus() {
		
		// get number of bullets left
		int shotLeft = getShotLeft();
		System.out.println("\nMissile Launcher (" + shotLeft + ")");
		
		// print out number of bullets based on shot left
		for (int i = 0; i < shotLeft; i++) System.out.print(AMMO_ICON + " ");
	}
	
	
}