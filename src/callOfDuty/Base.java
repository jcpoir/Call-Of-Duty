package callOfDuty;

import java.util.Random;

/**
 * Represents a 10 x 10 grid of targets.
 * @author josephpoirier
 *
 */
public class Base {
	
	private Target[][] targets;
	private int shotsCount;
	private int destroyedTargetCount;
	
	private static int DIM = 10;
	
	private static int NUM_HEADQUARTERS = 1;
	private static int NUM_ARMORIES = 2;
	private static int NUM_BARRACKS = 3;
	private static int NUM_SENTRY_TOWERS = 4;
	private static int NUM_TANKS = 4;
	private static int NUM_OIL_DRUMS = 4;
	
	// margin between buildings
	private static int BUILDING_MARGIN = 1;
	
	private int numTargets = 0;
	
	// (1) Constructor
	
	public Base() {
		
		// create new 10 x 10 target array
		targets = new Target[DIM][DIM];
		
		// puts Ground object in each cell of Ground
		for (int i = 0; i < DIM; i++) {
			for (int j = 0; j < DIM; j++) {
				
				targets[i][j] = new Ground(this);
				
				// update coordinates for each target
				int[] coordinate = new int[2]; coordinate[0] = i; coordinate[1] = j;
				targets[i][j].setCoordinate(coordinate);
				
				// set all to horizontal (default)
				targets[i][j].setHorizontal(true);
			}
		}
	}
	
	/**
	 * Places a set number of headquarters, armories, barracks, 
	 * sentry towers, tanks, and oil drums on the targets map. 
	 * All other tiles remain as ground. Function will try to 
	 * place each target until target is placed.
	 */
	public void placeAllTargetRandomly() {
		
		Random rand = new Random();
		Target target = null;
		
		// build HQ
		for (int i = 0; i < NUM_HEADQUARTERS; i++) {
			target = new HeadQuarter(this);
			placeTargetRandomly(rand, target);
		}
			
		// build Armories
		for (int i = 0; i < NUM_ARMORIES; i++) {
			target = new Armory(this);
			placeTargetRandomly(rand, target);
		}
		
		// build Barracks
		for (int i = 0; i < NUM_BARRACKS; i++) {
			target = new Barrack(this);
			placeTargetRandomly(rand, target);
		}
			
		// build SentryTowers
		for (int i = 0; i < NUM_SENTRY_TOWERS; i++) {
			target = new SentryTower(this);
			placeTargetRandomly(rand, target);
		}
				
		// build Tanks
		for (int i = 0; i < NUM_TANKS; i++) {
			target = new Tank(this);
			placeTargetRandomly(rand, target);
		}
			
		// build Oil Drums
		for (int i = 0; i < NUM_OIL_DRUMS; i++) {
			target = new OilDrum(this);
			placeTargetRandomly(rand, target);
		}
	
	}
	
	/**
	 * Place one target randomly. Try to place target until target is placed.
	 * @param rand, a java util random object
	 * @param target, a target object
	 */
	public void placeTargetRandomly(Random rand, Target target) {
		
		// init variables
		boolean horizontal; int row; int column;
		
		// randomize target attributes
		horizontal = rand.nextBoolean();
		row = rand.nextInt(DIM); column = rand.nextInt(DIM);
		
		// try to place structure until placed successfully
		while (placeTargetAt(target, row, column, horizontal) == false) {
			
			// re-randomize target attributes with each loop.
			horizontal = rand.nextBoolean();
			row = rand.nextInt(DIM); column = rand.nextInt(DIM);
		}
		
	}
	
	/**
	 * Check if tile is okay to place target at (is target at given coords == GROUND)
	 * @param target, a target object
	 * @param row, int number or row in base
	 * @param column, int number of column in base
	 * @param horizontal, boolean is target horizontal
	 * @return
	 */
	public boolean okToPlaceTargetAt(Target target, int row, int column, boolean horizontal) {
		
		// define return boolean
		boolean ok = true;
		
		// get length and width of target (horizontal is provided)
		int length = target.getLength();
		int width = target.getWidth();
		
		// building margin is only applied for buildings
		boolean isBuilding = target.getTargetName() != "TANK" && target.getTargetName() != "OILDRUM";
		
		// if horizontal, add target to rows and columns accordingly
		if (horizontal) {
			
			// define bounds
			int col_low; int col_high; int row_low; int row_high;
			
			// row lower bound
			if (row != 0 && isBuilding) row_low = row - BUILDING_MARGIN;
			else row_low = row;
			
			// row upper bound
			if (row != DIM && isBuilding) row_high = row + length + BUILDING_MARGIN;
			else row_high = row + length;
			
			// col lower bound
			if (column != 0 && isBuilding) col_low = column - BUILDING_MARGIN;
			else col_low = column;
			
			// col upper bound
			if (column != DIM && isBuilding) col_high = column + width + BUILDING_MARGIN;
			else col_high = column + width;
			
			// if the target will stick off the map in either direction, return false
			boolean OOB_Width = row + length > DIM;
			boolean OOB_Length = column + width > DIM;		
			if (OOB_Width || OOB_Length) return false;
			
			for (int i = row_low; i < row_high; i++) {
				for (int j = col_low; j < col_high; j++) {
					
					// define conditions (must not touch another building, must not stick off map)
					boolean cond1 = i >= 0; boolean cond2 = i < DIM;
					boolean cond3 = j >= 0; boolean cond4 = j < DIM;
					
					// if i and j are in bounds, check to see if tile is occupied
					if (cond1 & cond2 & cond3 & cond4) {
						if (isOccupied(i, j) == false) {
							ok = true;
						}
						else return false;
						
					}
					else return false;
					
					
				}
			}
		}
		
		// if not horizontal, switch length and width
		else {
			
			// define bounds
			int col_low; int col_high; int row_low; int row_high;
			
			// row lower bound
			if (row != 0 && isBuilding) row_low = row - BUILDING_MARGIN;
			else row_low = row;
			
			// row upper bound
			if (row != DIM && isBuilding) row_high = row + width + BUILDING_MARGIN;
			else row_high = row + length;
			
			// col lower bound
			if (column != 0 && isBuilding) col_low = column - BUILDING_MARGIN;
			else col_low = column;
			
			// col upper bound
			if (column != DIM && isBuilding) col_high = column + length + BUILDING_MARGIN;
			else col_high = column + width;
			
			// if the target will stick off the map in either direction, return false
			boolean OOB_Width = row + width > DIM;
			boolean OOB_Length = column + length > DIM;		
			if (OOB_Width || OOB_Length) return false;
			
			for (int i = row_low; i < row_high; i++) {
				for (int j = col_low; j < col_high + length; j++) {
					
					// define conditions (must not touch another building, must not stick off map)
					boolean cond1 = i >= 0; boolean cond2 = i < DIM;
					boolean cond3 = j >= 0; boolean cond4 = j < DIM;
					
					// if i and j are in bounds, check to see if tile is occupied
					if (cond1 & cond2 & cond3 & cond4) {
						if (isOccupied(i, j) == false) {
							ok = true;
						}
						else return false;
					}
				}
			}
		}
		
		return ok;
	}
		
	/**
	 * Place target object in target matrix at given row and column. Set target instance
	 * variables equal to parameters.
	 * @param target, the target object to be placed
	 * @param row, int row number in base
	 * @param column, int column number in base
	 * @param horizontal, boolean is the target horizontal
	 */
	public boolean placeTargetAt(Target target, int row, int column, boolean horizontal) {

		// if target cannot be placed, return false and perform no updates
		if (okToPlaceTargetAt(target, row, column, horizontal) == false) {
			return false;
		}
		
		// set instance variable values to match parameters
		int[] coordinate = new int[2];
		coordinate[0] = row; coordinate[1] = column;
		
		target.setCoordinate(coordinate);
		target.setHorizontal(horizontal);
		
		// get length and width
		int length = target.getLength();
		int width = target.getWidth();
		
		// if horizontal, add target to rows and columns accordingly
		if (horizontal) {
			for (int i = row; i < row + length; i++) {
				for (int j = column; j < column + width; j++) {
					
					this.targets[i][j] = target;
				}
			}
		}
		
		// if not horizontal, switch length and width
		else {
			for (int i = row; i < row + width; i++) {
				for (int j = column; j < column + length; j++) {
					
					this.targets[i][j] = target;
				}
			}
		}
		
		// place target in target matrix
		this.targets[row][column] = target;
		
		// add to numTargets
		numTargets++;
		
		// if target is placed successfully,return true
		return true;
	}
	
	/**
	 * if the selected tile isn't ground, then it is occupied.
	 * @param row, # in base
	 * @param column, # in base
	 * @return
	 */
	public boolean isOccupied(int row, int column) {	
		return this.targets[row][column].getTargetName() != "GROUND";
	}
	
	/**
	 * Call getShot for the appropriate target (the one that is shot at)
	 * @param row, # in base
	 * @param column, # in base
	 * @param base, a base object, based on an array of targets
	 */
	public void shootAt(int row, int column) {
		if(row >= 0 & row < DIM & column >= 0 & column < DIM) {
			targets[row][column].getShot(row, column);
		}
	}
		
	/**
	 * Returns true if game is over (no ammo or all targets destroyed) and false if not
	 * @param weapon1 first weapon (to see if out of ammo)
	 * @param weapon2 second weapon object
	 * @return true if game is over, false if not
	 */
	public boolean isGameOver(Weapon weapon1, Weapon weapon2) {
		
		// establish conditions for game being over
		boolean cond1 = weapon1.getShotLeft() == 0;
		boolean cond2 = weapon2.getShotLeft() == 0;
		boolean noAmmo = cond1 & cond2;
		boolean noTargets = this.destroyedTargetCount == this.numTargets;
		
		if (noAmmo || noTargets) {
			return true;
		}
		
		else {
			return false;
		}
	}
	
	/**
	 * Returns true if all targets have been destroyed (user has "won")
	 * @return true if won, false if not
	 */
	public boolean win() {
		
		if (this.destroyedTargetCount == this.numTargets) {
			return true;
		}
		else return false;
	}
	
	/**
	 * Print out the entire base map, with coordinates on the axes.
	 * Do not reveal tiles that have yet to be shot at
	 */
	public void print() {
		
		// initialize display — a string array of output characters — and counters
		String[][] display = new String[DIM + 1][DIM + 1];
		
		// for each target in the target matrix . . .
		for(int i = 0; i < DIM + 1; i++) for(int j = 0; j < DIM + 1; j++) { 
				
				// get coordinates and package in int array for toString
				int[] coordinate = new int[2]; coordinate[0] = i-1; coordinate[1] = j-1;
			
				// add coordinates to the outside edge
				if (i == 0 && j == 0) display[i][j] = " ";
				else if (i == 0) display[i][j] = String.valueOf(j - 1);
				else if (j == 0) display[i][j] = String.valueOf(i - 1);
				
				// call target method toString to get an icon to add to the display.
				else display[i][j] = targets[i-1][j-1].toString(coordinate);
		}	
		printArray(display);
	}
	
	/**
	 * Print out the entire base map, with coordinates on the axes
	 */
	public void printAll() {
		
		// initialize display — a string array of output characters — and counters
		String[][] display = new String[DIM + 1][DIM + 1];
		
		// for each target in the target matrix . . .
		for(int i = 0; i < DIM + 1; i++) for(int j = 0; j < DIM + 1; j++) { 
				
				// get coordinates and package in int array for toString
				int[] coordinate = new int[2]; coordinate[0] = i; coordinate[1] = j;
			
				// add coordinates to the outside edge
				if (i == 0 && j == 0) display[i][j] = " ";
				else if (i == 0) display[i][j] = String.valueOf(j - 1);
				else if (j == 0) display[i][j] = String.valueOf(i - 1);
				
				// call target method toString to get an icon to add to the display.
				else display[i][j] = targets[i-1][j-1].toString();
		}	
		printArray(display);
	}
	
	public void printArray(String[][] arr) {
		
		for (String[] row : arr) {
			System.out.println();
			for (String item : row) System.out.print(item + " ");
		}
	}
	
	// More getters and setters
	
	public int getShotsCount() {
		return this.shotsCount;
	}
	
	public Target[][] getTargetsArray() {
		return this.targets;
	}
	
	public void incrementShotsCount() {
		this.shotsCount++;
	}
	
	public int getDestroyedTargetCount() {
		return this.destroyedTargetCount;
	}
	
	public void setDestroyedTargetCount(int i) {
		this.destroyedTargetCount = i;
	}
}
	
