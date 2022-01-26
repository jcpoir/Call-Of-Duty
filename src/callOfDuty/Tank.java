package callOfDuty;

/**
 * Represents a 1x1 explosive target. Takes two shots to destroy
 * instead of one (as is the case for all other non-ground targets).
 * @author josephpoirier
 */
class Tank extends Target {
	
	// define unique properties as statics
	static int LENGTH = 1;
	static int WIDTH = 1;
	static String TARGET_NAME = "TANK";
	static int EXPLODE_RADIUS = 2;
	Base base;
	
	// construct using superclass constructor with statics
	public Tank(Base base) {
		super(LENGTH, WIDTH, base);		
	}

	/**
	 * Overrides method in superclass weapon
	 * Generates 5x5 explosion by firing at each individual tile iwthin the range.
	 */
	@Override
	void explode() {
		
		System.out.println("EXPLOSION!");
		
		// get coordinates of target
		int[] coordinate = getCoordinate();
		
		// calculate widest possible explosion boundary coordinates
		int row_low = coordinate[0] - EXPLODE_RADIUS;
		int row_high = coordinate[0] + EXPLODE_RADIUS;
		int col_low = coordinate[1] - EXPLODE_RADIUS;
		int col_high = coordinate[1] + EXPLODE_RADIUS;
		
		int[] bounds = new int[4]; bounds[0] = row_low; bounds[1] = row_high; bounds[2] = col_low; bounds[3] = col_high;
		
		// restrict bounds if explosion goes off the base
		for (int i = 0; i < bounds.length; i++) {
			if (bounds[i] < 0) bounds[i] = 0;
			else if (bounds[i] > 9) bounds[i] = 9;
		}
		
		// unpack new bounds
		row_low = bounds[0]; row_high = bounds[1]; col_low = bounds[2]; col_high = bounds[3];
		
		base = getBase();
		
		// "explode" by shooting at each individual target in the range
		for (int i = row_low; i <= row_high; i++) for (int j = col_low; j <= col_high; j++) {
			base.shootAt(i, j);
		}
	}
	
	/**
	 * Check hit array to see if target has been destroyed
	 * @return boolean isDestroyed
	 */
	@Override
	public boolean isDestroyed() {
		
		boolean isDestroyed = true;
		
		// get hit array (will check to see that all parts have been hit
		int[][] hit = getHit();
		
		// for row in rows
		for (int[] items : hit) for (int item : items) {
				
			// if any part of the target has yet to be hit twice, tank is not destroyed
			if (item < 2) {	
				isDestroyed = false;
			}	
		}	
		return isDestroyed;
	}

	/**
	 * Get name of specific target
	 * @return static String TARGET_NAME
	 */
	@Override
	public String getTargetName() {
		return TARGET_NAME;
	}
}