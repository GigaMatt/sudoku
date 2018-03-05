/*
 * CS 3331
 * Homework 2
 * @author Enrique Salcido
 * @author Matthew S Montoya
 * @author Yoonsik Cheon
 * Purpose: To practice implementing Java Applets & Graphics
 * Last Modified: 4 March 2018
 */

package sudoku.model;

public class Square {
	
	/** x coordinate of square chosen. */
	public int x;
	
	/** y coordinate of square chosen. */
	public int y;
	
	/** Value of square chosen. */
	public int value;
	
	/**
	 * Identify a certain coordinate on the board
	 * @param x
	 * @param y
	 */
	public Square(int x, int y) {
		this.x = x;
		this.y = y;
		value = 0;
	}
	
}
