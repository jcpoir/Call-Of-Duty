package callOfDuty;

/**
 * Represents a 5x1 target.
 * @author josephpoirier
 *
 */
class HeadQuarter extends Target {
	
	// define unique properties as statics
	static int LENGTH = 5;
	static int WIDTH = 1;
	static String TARGET_NAME = "HEADQUARTER";
	
	// construct using superclass constructor with statics
	public HeadQuarter(Base base) {
		super(LENGTH, WIDTH, base);		
	}

	@Override
	void explode() {
		// Headquarter does not explode
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