package callOfDuty;

/**
 * Represents a feature in base that may be shot at.
 * May respond by exploding, and will get destroyed once shot
 * enough.
 * @author josephpoirier
 */
public abstract class Target {
	
	// define instance variables
	// made private so that vars are only accessible via getters/setters
	
	private int[] coordinate;
	private int length;
	private int width;
	private boolean horizontal;
	private int[][] hit;
	private Base base;
	
	// Constructor
	
	public Target(int width, int length, Base base) {
		
		// bandaid solution — I was thinking about width and length backwards for this entire assignment
		// switching the two in the constructor makes all of my programs behave as they should for the tests
		this.setLength(length);
		this.setWidth(width);
		this.setBase(base);
		this.setHorizontal(true);
		
	}
	
	// (1) Getters & Setters
	
	/**
	 * Get coordinates of target head (top-left)
	 * @return int[] coordinate
	 */
	public int[] getCoordinate() {
		return this.coordinate;
	}
	
	/**
	 * Get target length
	 * @return int length
	 */
	public int getLength() {
		return this.length;
	}
	
	/**
	 * Get target width
	 * @return int width
	 */
	public int getWidth() {
		return this.width;
	}
	
	/**
	 * Check if target is horizontal
	 * @return boolean horizontal
	 */
	public boolean getHorizontal() {
		return this.horizontal;
	}
	
	/**
	 * Return array of # hits on target per coords
	 * @return int[][] hit
	 */
	public int[][] getHit() {
		return this.hit;
	}
	
	public Base getBase() {
		return this.base;
	}
	
	/**
	 * Set instance coordinate variable
	 * @param int[] coordinate
	 */
	void setCoordinate(int[] coordinate) {
		this.coordinate = coordinate;
	}
	
	/**
	 * Set length
	 * @param int length
	 */
	void setLength(int length) {
		this.length = length;
	}
	
	/**
	 * Set width
	 * @param int width
	 */
	void setWidth(int width) {
		this.width = width;
	}
	
	/**
	 * Set instance horizontal variable
	 * Also initialize hit, since hit depends on horizontal value
	 * @param boolean horizontal
	 */
	void setHorizontal(boolean horizontal) {
		
		// set horizontal
		this.horizontal = horizontal;
		
		// define hit based on target orientation
		if (horizontal) {
			this.hit = new int[getLength()][getWidth()];
		}
		else this.hit = new int[getWidth()][getLength()];
	}
	
	/**
	 * Set hit array
	 * @param int[][] hit
	 */
	void setHit(int[][] hit) {
		this.hit = hit;
	}
	
	/**
	 * Set base
	 * @param Base base
	 */
	void setBase(Base base) {
		this.base = base;
	}
	
	public void printArray(int[][] arr) {
		
		for (int[] row : arr) {
			System.out.println();
			for (int item : row) System.out.print(item + " ");
		}
	}
	
	// (2) Abstract Methods
	
	abstract void explode();
	public abstract String getTargetName();
	
	// (3) Other Methods
	
	/**
	 * If shot is on-target(and target is not yet destroyed), add a hit to the corresponding part of the 
	 * hit array
	 * @param int row
	 * @param int column
	 */
	public void getShot(int row, int column) {
		
		int[] topLeft = this.getCoordinate();
		int[] bottomRight = this.getBottomRight(this.coordinate, this.length, this.width, this.horizontal);
		
		// define conditions for checking if shot is within bounds
		boolean cond1 = row >= topLeft[0]; boolean cond2 = row <= bottomRight[0];
		boolean cond3 = column >= topLeft[1]; boolean cond4 = column <= bottomRight[1];
		
		// if target is already destroyed, don't bother updating hit array
		boolean notDestroyed = this.isDestroyed() == false;
		
		// check if row and column are in bounds
		if (cond1 & cond2 & cond3 & cond4 & notDestroyed) {
			
			// get current hit array
			int[][] hit = this.getHit();
			
			// subract topLeft from coordinates to get indices within array
			// add one hit to hit array in targeted location
			hit[row - topLeft[0]][column - topLeft[1]] += 1;
			
			this.setHit(hit);
			
			// if target is destroyed, call explode
			// function is only implemented for explosive targets
			if (this.isDestroyed()) {
				
				System.out.println(this.getTargetName() + " destroyed!");
				this.explode();
				
				// increment destroyed target count
				base.setDestroyedTargetCount(base.getDestroyedTargetCount() + 1);
			}
		}
		
	}
	
	/**
	 * Check hit array to see if target has been destroyed
	 * @return boolean isDestroyed
	 */
	public boolean isDestroyed() {
		
		boolean isDestroyed = true;
		
		// get hit array (will check to see that all parts have been hit
		int[][] hit = getHit();
		
		// GROUND targets will not have hit array initialized and so will cause an error otherwise
		if (this.getTargetName() != "GROUND") {
		
			// for row in rows
			for (int[] items : hit) {
				
				// for item in row
				for (int item : items) {
					
					// if any part of the target has yet to be hit, target is not destroyed
					if (item == 0) {	
						isDestroyed = false;
					}
				}	
			}
		}
		// GROUND cannot be destroyed, so simply set to false
		else isDestroyed = false;
		
		return isDestroyed;
	}
	
	/**
	 * Check if target has been hit at given given row and column
	 * @param row
	 * @param column
	 * @return isHit, is the given row and column it
	 */
	public boolean isHitAt(int row, int column) {
		
		boolean isHit;
		int[] topLeft = this.getCoordinate();
		int[][] hit = this.getHit();
		
		// if the value in hit is zero, coords have yet to be hit
		
		if (hit[row - topLeft[0]][column - topLeft[1]] == 0) {
			isHit = false;
		}
		
		else {
			isHit = true;
		}
		
		return isHit;
	}
	
	/**
	 * Useful version of toString, which takes coordinates as arguments
	 */
	public String toString(int[] coordinate) {
		
		// define output String
		String icon;
		
		String targetName = this.getTargetName();
		Boolean isDestroyed = this.isDestroyed();
		
		// define boolean conditions b/c ground, tank, are treated differently
		Boolean isGround = targetName == "GROUND";
		Boolean isTank = targetName == "TANK";
		
		// check if tile has been revealed yet using isHitAt
		boolean isHit = isHitAt(coordinate[0],coordinate[1]);
		
		// first, if tile hasn't been hit then reveal mist icon
		if (isHit == false) icon = ".";
		
		else {
		
			// if targeted tile is ground . . .
			if (isGround) {
				icon = "-";
			}
			
			// if targeted tile is not ground . . . 
			else {
				
				// if targeted object is destroyed . . .
				if(isDestroyed) {
					icon = "X";
				}
				
				// if targeted object is still viable . . .
				else {
					
					if(isTank) {
						icon = "T";
					}
					
					// non-tanks denoted with "O"s
					else {
						icon = "O";
					}
				}
			}
		}
		
		return icon;
	}
	
	/**
	 * Get the icon associated with a given tile. 
	 * Hit buildings: "O", Hit tanks: "T", Destroyed buildings: "X" etc.
	 */
	public String toString() {
		
		// define output String
		String icon;
		
		String targetName = this.getTargetName();
		Boolean isDestroyed = this.isDestroyed();
		
		// define boolean conditions b/c ground, tank, are treated differently
		Boolean isGround = targetName == "GROUND";
		Boolean isTank = targetName == "TANK";
		
		// if targeted tile is ground . . .
		if (isGround) {
			icon = "-";
		}
		
		// if targeted tile is not ground . . . 
		else {
			
			// if targeted object is destroyed . . .
			if(isDestroyed) {
				icon = "X";
			}
			
			// if targeted object is still viable . . .
			else {
				
				if(isTank) {
					icon = "T";
				}
				
				// non-tanks denoted with "O"s
				else {
					icon = "O";
				}
			}
		}
		
		
		return icon;
	}
	
	// (4) Helper Methods
	
	/**
	 * Get the coordinates of the bottom right corner of the target
	 * Helps with figuring out if target is hit
	 * @param int[] coordinate
	 * @param int length
	 * @param int width
	 * @param int horizontal
	 * @return int[] bottomRight — coordinates of the bottom right corner
	 */
	public int[] getBottomRight(int[] coordinate, int length, int width, boolean horizontal) {
		
		int[] bottomRight = new int[2];
		
		// if target is horizontal . . .
		if (horizontal) {
			
			// add length and width appropriately
			bottomRight[0] = coordinate[0] + length;
			bottomRight[1] = coordinate[1] + width;
		}
		
		// if target is vertical . . . 
		else {
			
			// add length and width appropriately (opposite)
			bottomRight[0] = coordinate[0] + width;
			bottomRight[1] = coordinate[1] + length;
		}
		
		return bottomRight;
	}	
}