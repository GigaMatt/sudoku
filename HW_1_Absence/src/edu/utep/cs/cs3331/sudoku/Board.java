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

public class Board extends ConsoleUI {
	private static int[][] array;
	private static ConsoleUI ui = new ConsoleUI();
	private int size;
	private static boolean bool;

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
		
	
	public static void editBoard(int size, int x, int y, int value) {
		array[x][y] = value;
		ui.printBoard(size, array);	
	}

	
	//FIXME: 
	private static Random rand = new Random();

	public static void setIsSolved(boolean sentinal) {
		bool = sentinal;
	}
	
	public static boolean isSolved() {
		return bool;
		//return rand.nextBoolean();
	}
}