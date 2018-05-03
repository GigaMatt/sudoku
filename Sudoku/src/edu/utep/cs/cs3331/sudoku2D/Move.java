/**
 * CS3331
 * @version 5.0 (05/02/2018)
 * 
 * @author Anthony Ayo 
 * @author Anthony Moran
 * @author Enrique Salcido
 * @author Matthew Montoya
 **/
package edu.utep.cs.cs3331.sudoku2D;

public class Move {
	int x,y,value;
	/**
	 * Default constructor
	 */
	public Move() {
		this(0,0,0);
	}
	
	/**
	 * Determines where the user is changing position to
	 * @param x the x-coordinate where the user desires to go
	 * @param y the y-coordinate where the user desires to go
	 * @param value the value for the x-y coordinate
	 */
	public Move(int x, int y, int value) {
		this.x = x;
		this.y = y;
		this.value = value;
	}
}
