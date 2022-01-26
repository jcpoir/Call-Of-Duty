package callOfDuty;

/**
 * Represents a 2x3 explosive target.
 * @author josephpoirier
 *
 */
class Armory extends Target {
	
	// define unique properties as statics
	static int LENGTH = 3;
	static int WIDTH = 2;
	static String TARGET_NAME = "ARMORY";
	static int EXPLODE_RADIUS = 2;
	Base base;
	
	// construct using superclass constructor with statics
	public Armory(Base base) {
		super(LENGTH, WIDTH, base);		
	}
		
	
	/**
	 * Overrides explode method in the target class
	 * creates explosion of radius two around armory on all sides
	 * by iterating through an array of that size and individually
	 * shooting at each tile.
	 */
	@Override
	void explode() {
		
		System.out.println("EXPLOSION!");
		
		// get coordinates of target
		int[] coordinate = getCoordinate();
		
		// figure out dimensions of blast
		boolean horizontal = getHorizontal();
		
		int row_low; int row_high; int col_low; int col_high;
		
		if (horizontal ) {
			// calculate widest possible explosion boundary coordinates
			row_low = coordinate[0] - EXPLODE_RADIUS;
			row_high = coordinate[0] + EXPLODE_RADIUS + LENGTH;
			col_low = coordinate[1] - EXPLODE_RADIUS;
			col_high = coordinate[1] + EXPLODE_RADIUS + WIDTH;
		}
		
		else {
			// calculate widest possible explosion boundary coordinates
			row_low = coordinate[0] - EXPLODE_RADIUS;
			row_high = coordinate[0] + EXPLODE_RADIUS + LENGTH;
			col_low = coordinate[1] - EXPLODE_RADIUS;
			col_high = coordinate[1] + EXPLODE_RADIUS + WIDTH;
		}
		
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