package callOfDuty;

import java.util.Scanner;

/**
 * Main class, contains the high-level organization of the game.
 * @author josephpoirier
 *
 */
public class CallOfDutyGame {
	
	// initialize scanner for reading user input
	static Scanner sc = new Scanner(System.in);
	
	private static CallOfDutyGame game = new CallOfDutyGame();
	
	public static void main(String args[]) {
		
		// Create a new base
		Base base = new Base();
		
		// Place targets so that buildings don't touch
		base.placeAllTargetRandomly();
		
		System.out.println("\nCALL OF DUTY");
		
		// instructions
		System.out.println("\nINSTRUCTIONS:\n\nShoot and destroy all targets before your ammunition runs out." +
				"\nTo destroy a target, you must hit each of its tiles at least once (tanks must be hit twice)." +
				"\nYour rocket launcher can target a 1x1 area and your missile launcher can target a 3x3 area." +
				"GOOD LUCK.");
		
		// create rocket launcher
		Weapon rocketLauncher = new RocketLauncher();
		Weapon missileLauncher = new Missile();
		
		// default current weapon
		Weapon currentWeapon = rocketLauncher;
		
		// with each loop, make sure that game isn't over
		while (base.isGameOver(rocketLauncher, missileLauncher) == false) {
		
			// display board
			base.print();
			System.out.println("");
			
			currentWeapon.printStatus();
			System.out.println("\n");
			
			System.out.println("Enter coordinates as row,column or \"q\" to switch weapons:");
			
			// interpret user input and switch weapon or fire
			currentWeapon = game.readUserInput(currentWeapon, rocketLauncher, missileLauncher, base);	
		}
		
		// when execution reaches this point, the game is over. reveal entire base
		base.printAll();
		
		// if user has won (destroyed all targets), congratulate!
		if (base.win()) System.out.println("\n\nCongratulations! YOU WIN. " + base.getShotsCount() + " shots required.");
		
		// otherwise, game has ended b/c user ran out of ammo
		else System.out.println("\n\nOut of ammo. YOU LOSE. Better luck next time!");
	}
	
	/**
	 * Switch to alternate weapon
	 * @param currentWeapon, weapon in user's hotbar
	 * @return the other weapon, either rocketLauncher or missileLauncher
	 */
	public Weapon switchWeapon(Weapon currentWeapon, Weapon rocketLauncher, Weapon missileLauncher) {
		
		if (currentWeapon.getWeaponType() == "MISSILE") {
			return rocketLauncher;
		}		
		else return missileLauncher;
	}
	
	/**
	 * Takes user input until it recieves a valid response (coordinates or q)
	 * if q, calls for weapon switch
	 * if coords, calls for weapon fire
	 * @param currentWeapon, weapon object last activated
	 * @param rocketLauncher, type of weapon
	 * @param missileLauncher, type of weapon
	 * @return currentWeapon — which may have switched in response to user input
	 */
	public Weapon readUserInput(Weapon currentWeapon, Weapon rocketLauncher, Weapon missileLauncher, Base base) {
		
		boolean done = false;
		String response;
		
		while (done == false) {
			
			// get user input
			response = sc.nextLine();
			
			// check if user wants to switch weapons
			if (response.equals("q")) {
				System.out.println("WEAPON CHANGED");
				currentWeapon = switchWeapon(currentWeapon, rocketLauncher, missileLauncher);
				done = true;
			}
			
			// check if user wants to fire weapon
			if (response.length() == 3) {
				
				// conditions that must be met for response to be valid coordinate
				boolean cond1 = Character.isDigit(response.charAt(0));
				boolean cond2 = response.charAt(1) == ',';
				boolean cond3 = Character.isDigit(response.charAt(2));
				
				if (cond1 & cond2 & cond3) {
					
					// upack coordinate values from user input
					int row = Character.getNumericValue(response.charAt(0));
					int column = Character.getNumericValue(response.charAt(2));
					
					// define boolean for if weapon has ammo
					boolean noAmmo = currentWeapon.getShotLeft() == 0;
					
					// if all conditions are met, shoot current weapon at specified target
					currentWeapon.shootAt(row, column, base);
					
					// if weapon is out of ammo, it will print out a warning rather than firing.
					// this means that the loop must be run again, allowing the user to switch weapons
					if (noAmmo == false) done = true;
				}
			}
		}
		
		return currentWeapon;
	}
}