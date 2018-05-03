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

public interface Solver {	
	/**
	 * Solve the board
	 * @param b the board which will be solved
	 * @return
	 */
	public Board solve(Board b);
	
	/**
	 * Checks if the board is solvable
	 * @param b the board which is being checked for solvability
	 * @return whether the board is or isn't solveable
	 */
	public boolean isSolveable(Board b);
}
