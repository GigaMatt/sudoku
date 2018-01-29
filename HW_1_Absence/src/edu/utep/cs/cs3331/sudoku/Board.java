/**
 * HW 1
 * Board.java
 * By: Matthew S Montoya
 * Instructor: Dr. Yoosnek Cheon
 * TAs: Jesus Valles && Jose Cabrera
 * Purpose: To practice implementing a sudoku game via the user console
 * Last Modified: 25 January 2018
 */
package edu.utep.cs.cs3331.sudoku;

import java.util.Random;

public class Board {
	public int[][] array;
	private int size;
	//public boolean isSolved;

	public Board() {
		this(4);	//creates a 4x4 board
	}

	public Board(int size) {	//creates the board to be of size n
		int[][] backendBoard = new int[size][size];
		for(int i=0; i<backendBoard.length; i++) {
			for(int j=0; j<backendBoard[i].length; j++) {
				backendBoard[i][j] = -1;
			}
		}
	}
		
	
	public int[][] editBoard(int size, int x, int y, int value) {
		
		array[x-1][y-1] = value;
		
		//return to the console UI to print
		return array;
	}


	//print the board happens in console UI
	//public void printBoard(int size, int[][] array) {	
	//}
	
	//FIXME: 
	private static Random rand = new Random();

	public boolean isSolved() {
		return rand.nextBoolean();
	}
}