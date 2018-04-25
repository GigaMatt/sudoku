/*
 * CS 3331
 * Homework 5
 * @author Anthony Ayo
 * @author Anthony Moran
 * @author Matthew Montoya
 * @author Enrique Salcido

 * Purpose: To practice implementing Java Networking
 * Last Modified: 1 May 2018
 */

package edu.utep.cs.cs3331.hw5;

public interface Solver {
	//solve board
	public Board solve(Board b);
	//check if board is solvable
	public boolean isSolveable(Board b);
}
