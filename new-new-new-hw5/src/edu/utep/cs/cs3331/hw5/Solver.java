
package edu.utep.cs.cs3331.hw5;

/**@author Anthony Moran */

public interface Solver {
	//solve board
	public Board solve(Board b);
	//check if board is solvable
	public boolean isSolveable(Board b);
}
