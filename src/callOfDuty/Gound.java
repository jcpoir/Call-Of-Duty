package callOfDuty;

/**
 * A passive "target", doesn't respond to being shot in any way.
 * @author josephpoirier
 *
 */
class Ground extends Target {
	
	// define unique properties as statics
	static int LENGTH = 1;
	static int WIDTH = 1;
	static String TARGET_NAME = "GROUND";
	
	// construct using superclass constructor with statics
	public Ground(Base base) {
		super(LENGTH, WIDTH, base);		
	}

	@Override
	void explode() {
		// Ground does not explode
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