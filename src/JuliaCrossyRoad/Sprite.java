package JuliaCrossyRoad;

/**********************************************************

Sprite

Created by:  [ Julia Lau ]
Date:        [ May 28th, 2021 ]

Description:

	Sprite is an object that is also a superclass for Character and Opponent.
	
	NOTABLE Instance Variables:
	- int cuurX			Location of x-coordinate on x/y coordinate
	- int currY			Location of y-coordinate on x/y coordinate
	- int dimension		(Both Character and Opponent have a dimension of 2, creating a 2x2 object
	
***********************************************************/

public class Sprite {
	protected int currX; // x and y coordinates on board 
	protected int currY; // x and y coordinates on board
	protected int dimension;
	
	public Sprite (int startX, int startY, int dimension ) {
		currX = startX;
		currY = startY;
		this.dimension = dimension;
	}
	
	public int[] getLocation() { 
		int[] loc = new int[2];
		loc[0] = currX;
		loc[1] = currY;
		return loc;
	}
	
	public void changeX (int rate) {
		currX = currX + rate;
	}
	
	public void changeY (int rate) {
		currY = currY + rate;
	}
	
	public void setX (int loc) {
		currX = loc;
	}
}
