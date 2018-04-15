/*
 * CS 3331
 * Homework 4
 * @author Anthony Ayo
 * @author Matthew Montoya
 * @author Enrique Salcido
 * @author Yoonsik Cheon
 * Purpose: To practice implementing Java Graphics
 * Last Modified: 12 April 2018
 */

package sudoku.model;

public interface BoardSolver {
	Board solveBoard(Board board);
	boolean isSolvable(Board board);
}
