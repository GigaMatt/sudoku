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
	private static int[][] backendBoard;
	private static ConsoleUI ui = new ConsoleUI();
	private int size;
	private static boolean bool;

	public Board() {
		this(4);	//creates a 4x4 board
	}

	public Board(int size) {	//creates the board to be of size n
		backendBoard = new int[size][size];
		for(int i=0; i<backendBoard.length; i++) {
			for(int j=0; j<backendBoard[i].length; j++) {
				backendBoard[i][j] = 0;
			}
		}
		ui.printBoard(size, backendBoard);
	}


	public static void editBoard(int size, int x, int y, int value) {
		backendBoard[x-1][y-1] = value;

		boolean isSolved = true;
		for(int i=0; i<backendBoard.length; i++) {
			for(int j=0; j<backendBoard[i].length; j++) {
				if(backendBoard[i][j] == 0) {
					isSolved=false;
					setIsSolved(isSolved);
					break;
				}
			}
		}
		ui.printBoard(size, backendBoard);	
	}

	public static boolean rowChecker(int x_coordinate, int value) {
		for (int i=0; i<backendBoard.length; i++)
			if (backendBoard[x_coordinate][i] == value)
				return false;
		return true;
	}

	public static boolean columnChecker(int y_coordinate, int value) {
		for (int i=0; i<backendBoard.length; i++)
			if (backendBoard[i][y_coordinate] == value)
				return false;
		return true;
	}
	
	public static boolean boxChecker(int x_coordinate, int y_coordinate, int value) {
        int[] box = {
        		x_coordinate-(x_coordinate%backendBoard.length), y_coordinate-(y_coordinate%backendBoard.length)
        		};

        for(int i=box[0]; i<=(box[0]+2); i++)
            for(int j=box[1]; j<=(box[1]+2); j++)
                if(backendBoard[i][j] == value)
                    return false;
        return true;
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