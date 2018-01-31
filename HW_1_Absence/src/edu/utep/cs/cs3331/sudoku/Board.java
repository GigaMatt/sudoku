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

import java.lang.reflect.Array;
import java.util.Random;

public class Board extends ConsoleUI {
	private static int[][] backendBoard;
	private static ConsoleUI ui = new ConsoleUI();
	private int size;
	private static boolean bool;

	//public Board() {
	//this(4);	//creates a 4x4 board
	//}

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

	public static boolean checkBox(int x_val, int y_val, int value) {
		if(x_val<=Math.sqrt(backendBoard.length)) {
			if(y_val<=Math.sqrt(backendBoard.length)) {
				System.out.println("We're in Q1");//Q1
				for(int i=0; i<Math.sqrt(backendBoard.length); i++) {
					for(int j=0; j<Math.sqrt(backendBoard.length); j++){
						if(backendBoard[i][j] == value)
							return false;
					}
				}
			}

			else if(y_val > 2 * Math.sqrt(backendBoard.length)) {
				System.out.println("We're in Q7");//Q7
				
				for(int i=0; i<Math.sqrt(backendBoard.length); i++) {
					for(int j=(int) ((int) 2*Math.sqrt(backendBoard.length)); j<backendBoard.length; j++){
						if(backendBoard[i][j] == value)
							//System.out.println("We cannot put in this box");//Q1
							return false;
					}
				}
			}
			
			else {
				System.out.println("We're in Q4");//Q4
				
				for(int i=0; i<Math.sqrt(backendBoard.length); i++) {
					for(int j=(int) Math.sqrt(backendBoard.length); j<2* Math.sqrt(backendBoard.length); j++){
						if(backendBoard[i][j] == value)
							//System.out.println("We cannot put in this box");//Q1
							return false;
					}
				}
			}
		}
		
		
		else if(x_val> 2 *Math.sqrt(backendBoard.length)) {
			if(y_val<=Math.sqrt(backendBoard.length)) {
				System.out.println("We're in Q3");//Q3
				
				for(int i=(int) (2* Math.sqrt(backendBoard.length)); i<backendBoard.length; i++) {
					for(int j=0; j<Math.sqrt(backendBoard.length); j++){
						if(backendBoard[i][j] == value)
							//System.out.println("We cannot put in this box");//Q1
							return false;
					}
				}
			}

			else if(y_val > 2 * Math.sqrt(backendBoard.length)) {
				System.out.println("We're in Q9");//Q9
				
				for(int i=(int) (2* Math.sqrt(backendBoard.length)); i<backendBoard.length; i++) {
					for(int j=(int) ((int) 2*Math.sqrt(backendBoard.length)); j<backendBoard.length; j++){
						if(backendBoard[i][j] == value)
							return false;
					}
				}
			}
			else if(y_val > Math.sqrt(backendBoard.length)) {
				System.out.println("We're in Q6");//Q6
				
				for(int i=(int) (2* Math.sqrt(backendBoard.length)); i<backendBoard.length; i++) {
					for(int j=(int) Math.sqrt(backendBoard.length); j<2* Math.sqrt(backendBoard.length); j++){
						if(backendBoard[i][j] == value)
							//System.out.println("We cannot put in this box");//Q1
							return false;
					}
				}
			}
		}
		
		
		else {
			if(y_val<=Math.sqrt(backendBoard.length)) {
				System.out.println("We're in Q2");//Q1
				for(int i=(int) Math.sqrt(backendBoard.length); i< 2* Math.sqrt(backendBoard.length); i++) {
					for(int j=0; j<Math.sqrt(backendBoard.length); j++){
						if(backendBoard[i][j] == value)
							return false;
					}
				}	
			}
			else if(y_val >2 * Math.sqrt(backendBoard.length)) {
				System.out.println("We're in Q8");//Q8
				
				for(int i=(int) Math.sqrt(backendBoard.length); i< 2* Math.sqrt(backendBoard.length); i++) {
					for(int j=(int) ((int) 2*Math.sqrt(backendBoard.length)); j<backendBoard.length; j++){
						if(backendBoard[i][j] == value)
							return false;
					}
				}
			}
			else {
				System.out.println("We're in Q5");//Q5
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

	//FIXME: 
	//private static Random rand = new Random();

	public static void setIsSolved(boolean sentinal) {
		bool = sentinal;
	}

	public static boolean isSolved() {
		return bool;
		//return rand.nextBoolean();
	}
}