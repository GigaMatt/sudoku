package edu.utep.cs.cs3331.sudoku2D;

/**@author Anthony Moran */

public interface Solver {
	//solve board
	public Board solve(Board b);
	//check if board is solvable
	public boolean isSolveable(Board b);
}
