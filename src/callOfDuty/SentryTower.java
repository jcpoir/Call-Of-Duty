package callOfDuty;

/**
 * Represents a 1x1 target.
 * @author josephpoirier
 *
 */
class SentryTower extends Target {

	// define unique properties as statics
	static int LENGTH = 1;
	static int WIDTH = 1;
	static String TARGET_NAME = "SENTRYTOWER";
	
	// construct using superclass constructor with statics
	public SentryTower(Base base) {
		super(LENGTH, WIDTH, base);	
	}
	
	@Override
	void explode() {
		// Sentry tower does not explode	
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