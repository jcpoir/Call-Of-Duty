package callOfDuty;

/**
 * Represents a 3x1 target
 * @author josephpoirier
 *
 */
class Barrack extends Target {
	
	// define unique properties as statics
	static int LENGTH = 3;
	static int WIDTH = 1;
	static String TARGET_NAME = "BARRACK";
	
	// construct using superclass constructor with statics
	public Barrack(Base base) {
		super(LENGTH, WIDTH, base);	
	}

	@Override
	void explode() {
		// Barrack does not explode	
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