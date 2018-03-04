/**
 * HW 1
 * Board.java
 * By: Matthew S Montoya
 * Instructor: Dr. Yoosnek Cheon
 * TAs: Jesus Valles && Jose Cabrera
 * Purpose: To practice implementing a sudoku game via the user console
 * Last Modified: 30 January 2018
 */
package edu.utep.cs.cs3331.sudoku;

import java.lang.reflect.Array;
import java.util.Random;

public class Board extends ConsoleUI {
	private static int[][] backendBoard;
	private static ConsoleUI ui = new ConsoleUI();
	private int size;
	private static boolean bool;

	/**
	 * Board: Creates an instance of board as a 2D array
	 * @param size
	 */
	public Board(int size) {	//creates the board to be of size n
		backendBoard = new int[size][size];
		for(int i=0; i<backendBoard.length; i++) {
			for(int j=0; j<backendBoard[i].length; j++) {
				backendBoard[i][j] = 0;
			}
		}
		ui.printBoard(size, backendBoard);
	}


	/**
	 * editBoard: Edits the instance of the board we have declared
	 * @param size
	 * @param x
	 * @param y
	 * @param value
	 */
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

	
	/**
	 * checkRowAndColumn: Checks row and column to ensure user's number can be inserted into the desired position (Check 1 of 2)
	 * @param x_coordinate
	 * @param y_coordinate
	 * @param value
	 * @return
	 */
	public static boolean checkRowAndColumn(int x_coordinate, int y_coordinate, int value) {

		for(int i=0; i<backendBoard.length;i++)	//checks rows
			for(int j=0; j<backendBoard[i].length; j++)
				if(i==x_coordinate-1)
					if(backendBoard[i][j] == value)
						return false;

		for(int i=0; i<backendBoard.length;i++)	//check columns
			for(int j=0; j<backendBoard[i].length; j++)
				if(j==y_coordinate-1)
					if(backendBoard[i][j] == value)
						return false;

		return checkBox(x_coordinate, y_coordinate, value);
	}

	
	/**
	 * checkBox: Checks the quadrant to ensure the user's number can be inserted into the desired position (Check 2 of 2)
	 * @param x_val
	 * @param y_val
	 * @param value
	 * @return
	 */
	public static boolean checkBox(int x_val, int y_val, int value) {
		if(x_val<=Math.sqrt(backendBoard.length)) {
			//Q1
			if(y_val<=Math.sqrt(backendBoard.length)) {
				System.out.println("We're in Q1");
				for(int i=0; i<Math.sqrt(backendBoard.length); i++) {
					for(int j=0; j<Math.sqrt(backendBoard.length); j++){
						if(backendBoard[i][j] == value)
							return false;
					}
				}
			}
			//Q7
			else if(y_val > 2 * Math.sqrt(backendBoard.length)) {
				System.out.println("We're in Q7");
				for(int i=0; i<Math.sqrt(backendBoard.length); i++) {
					for(int j=(int) ((int) 2*Math.sqrt(backendBoard.length)); j<backendBoard.length; j++){
						if(backendBoard[i][j] == value)
							return false;
					}
				}
			}
			else {
				//Q4
				System.out.println("We're in Q4");
				for(int i=0; i<Math.sqrt(backendBoard.length); i++) {
					for(int j=(int) Math.sqrt(backendBoard.length); j<2* Math.sqrt(backendBoard.length); j++){
						if(backendBoard[i][j] == value)
							return false;
					}
				}
			}
		}
		else if(x_val> 2 *Math.sqrt(backendBoard.length)) {
			//Q3
			if(y_val<=Math.sqrt(backendBoard.length)) {
				System.out.println("We're in Q3");
				for(int i=(int) (2* Math.sqrt(backendBoard.length)); i<backendBoard.length; i++) {
					for(int j=0; j<Math.sqrt(backendBoard.length); j++){
						if(backendBoard[i][j] == value)
							return false;
					}
				}
			}
			//Q9
			else if(y_val > 2 * Math.sqrt(backendBoard.length)) {
				System.out.println("We're in Q9");
				for(int i=(int) (2* Math.sqrt(backendBoard.length)); i<backendBoard.length; i++) {
					for(int j=(int) ((int) 2*Math.sqrt(backendBoard.length)); j<backendBoard.length; j++){
						if(backendBoard[i][j] == value)
							return false;
					}
				}
			}
			//Q6
			else if(y_val > Math.sqrt(backendBoard.length)) {
				System.out.println("We're in Q6");
				for(int i=(int) (2* Math.sqrt(backendBoard.length)); i<backendBoard.length; i++) {
					for(int j=(int) Math.sqrt(backendBoard.length); j<2* Math.sqrt(backendBoard.length); j++){
						if(backendBoard[i][j] == value)
							return false;
					}
				}
			}
		}
		
		
		else {
			//Q2
			if(y_val<=Math.sqrt(backendBoard.length)) {
				System.out.println("We're in Q2");
				for(int i=(int) Math.sqrt(backendBoard.length); i< 2* Math.sqrt(backendBoard.length); i++) {
					for(int j=0; j<Math.sqrt(backendBoard.length); j++){
						if(backendBoard[i][j] == value)
							return false;
					}
				}	
			}
			//Q8
			else if(y_val >2 * Math.sqrt(backendBoard.length)) {
				System.out.println("We're in Q8");
				for(int i=(int) Math.sqrt(backendBoard.length); i< 2* Math.sqrt(backendBoard.length); i++) {
					for(int j=(int) ((int) 2*Math.sqrt(backendBoard.length)); j<backendBoard.length; j++){
						if(backendBoard[i][j] == value)
							return false;
					}
				}
			}
			else {
				//Q5
				System.out.println("We're in Q5");
				for(int i=(int) Math.sqrt(backendBoard.length); i< 2* Math.sqrt(backendBoard.length); i++) {
					for(int j=(int) Math.sqrt(backendBoard.length); j<2* Math.sqrt(backendBoard.length); j++){
						if(backendBoard[i][j] == value)
							return false;
					}
				}	
			}
		}
		return true;
	}

	
	public static void setIsSolved(boolean sentinal) {
		bool = sentinal;
	}

	public static boolean isSolved() {
		return bool;
	}
}