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

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Square {
	
	/** x coordinate of square chosen. */
	public int x;
	
	/** y coordinate of square chosen. */
	public int y;
	
	/** Value of square chosen. */
	public int value;
	
	public Set<Integer> possibleValues = new HashSet(Arrays.asList(new Integer[] {1,2,3,4,5,6,7,8,9}));
	
	public Square() {
		x = 0;
		y = 0;
		value = 0;
	}
	
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
	
	public void updateSet(int x) {
		possibleValues.remove(x);
	}
	
	public boolean emptySet(){
		return possibleValues.isEmpty();
	}
	
	public Set<Integer> getPossibleMoves() {
		return possibleValues;
	}
	
}
