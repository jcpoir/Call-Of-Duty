package callOfDuty;

/**
 * Superclass of rocket launcher, missile. Represents
 * broadly an object that is capable of shooting a target.
 * @author josephpoirier
 *
 */
public abstract class Weapon {
	
	private int shotLeft;
	
	public Weapon(int shotLeft) {
		this.shotLeft = shotLeft;
	}
	
	public int getShotLeft() {
		return this.shotLeft;
	}
	
	public void decrementShotLeft() {
		this.shotLeft--;
	}
	
	public void setShotLeft(int shotLeft) {
		this.shotLeft = shotLeft;
	}
	
	public abstract String getWeaponType();
	public abstract void shootAt(int row, int column, Base base);

	protected abstract void printStatus();

}
