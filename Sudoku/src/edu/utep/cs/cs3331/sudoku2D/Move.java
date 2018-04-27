package edu.utep.cs.cs3331.sudoku2D;

/**@author Anthony Moran
 * Used for easy tracking of what moves were done, includes their x,y location and value that was changed
 */

public class Move {
	int x,y,value;
	
	public Move() {
		this(0,0,0);
	}
	
	public Move(int x, int y, int value) {
		this.x = x;
		this.y = y;
		this.value = value;
	}
}
