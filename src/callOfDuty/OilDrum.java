package callOfDuty;

/**
 * Represents an explosive 1x1 target with blast radius 2.
 * @author josephpoirier
 *
 */
class OilDrum extends Target {
	
	// define unique properties as statics
	private static int LENGTH = 1;
	private static int WIDTH = 1;
	private static String TARGET_NAME = "OILDRUM";
	private static int EXPLODE_RADIUS = 2;
	private Base base;
	
	// construct using superclass constructor with statics
	public OilDrum(Base base) {
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
	 * Get name of specific target
	 * @return static String TARGET_NAME
	 */
	@Override
	public String getTargetName() {
		return TARGET_NAME;
	}
}