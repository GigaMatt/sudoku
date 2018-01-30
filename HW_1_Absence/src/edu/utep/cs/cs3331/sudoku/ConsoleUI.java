/**
 * HW 1
 * ConsoleUI.java
 * By: Matthew S Montoya
 * Instructor: Dr. Yoosnek Cheon
 * TAs: Jesus Valles && Jose Cabrera
 * Purpose: To practice implementing a sudoku game via the user console
 * Last Modified: 25 January 2018
 */
package edu.utep.cs.cs3331.sudoku;
import java.util.*;
///////////////////////////./////////////////////////////////////////////////
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Array;

public class ConsoleUI {
	private InputStream in;
	private PrintStream out;
	private Scanner input;
	private Scanner size;

	public ConsoleUI() {
		in = System.in;
		out = System.out;
	}

	public ConsoleUI(InputStream in, PrintStream out) {
		this.in = in;
		this.out = out;
	}

	public void welcome() {
		out.println("Welcome to Sudoku!");
	}

	/**
	 * R1: Ask the user for the size of the board they would like to use
	 * @return
	 */
	public int askSize() {
		size = new Scanner(System.in);
		out.print("What board size do you want?\n"
				+ "Enter '4' or '9'");
		int boardSize = size.nextInt();
		if (boardSize != 4 && boardSize != 9) {
			out.println("Incorrect size.");
			askSize();
		}

		//return boardSize to the main
		//main will then call the board to be built
		return boardSize;
	}

	public void playGame(Board board, int size) {
		while(!board.isSolved()) {
			//STEP 1: Print the empty bad board
			//printBoard(size);

			//STEP 2: Have user select x,y and value, then add value (pass through real values to backendView)
			input=new Scanner(System.in);
			int x_coordinate=0, y_coordinate=0, value=0;

			/********************************************************************
			 ************************GET THE X-COORDINATE************************
			 ********************************************************************/		
			out.println("Select the coordinate you'd like to edit or enter '0 or a negative number' to terminate.\nLet's begin with the row (beginning from the top):");
			x_coordinate = input.nextInt();
			if(x_coordinate < 1) {
				showExitMessage();
				Board.setIsSolved(true);
				break;
			}
			while(x_coordinate > size) {
				out.println("Invalid input.\nPlease enter numbers between 1 & "+size+" or enter '0' or a negative number to terminate.");
				x_coordinate = input.nextInt();
				if(x_coordinate < 1)
					showExitMessage();
				if(x_coordinate>0 && x_coordinate<=size) {
					break;
				}

				if(x_coordinate > size || x_coordinate < 0) {
					out.println("Invalid input.\nPlease enter numbers between 1 & "+size+".");
					playGame(board, size);
				}
			}

			/********************************************************************
			 ************************GET THE Y-COORDINATE************************
			 ********************************************************************/
			if(!Board.isSolved()) {
				out.println("Great! You chose "+x_coordinate+". Now, for the column (beginning from the top). Select the coordinate you'd like to edit or enter '0' or a negative number to terminate.");
				y_coordinate = input.nextInt();
				if(y_coordinate <1) {
					showExitMessage();
					Board.setIsSolved(true);
					break;
				}
				while(y_coordinate > size) {		//if an invalid number is entered
					out.println("Invalid input.\nPlease enter numbers between 1 & "+size+" or enter '0' or a negative number to terminate.");
					y_coordinate = input.nextInt();
					if(y_coordinate < 1)
						showExitMessage();
					if(y_coordinate>0 && y_coordinate<=size)
						break;
				}
			}

			/********************************************************************
			 ********************GET THE COORDINATE VALUE************************
			 ********************************************************************/		
			if(!Board.isSolved()) {
				out.println("Enter the value you wish to put in or enter '0' or a negative number to terminate.");
				value = input.nextInt();
				if(value <1) {
					showExitMessage();
					Board.setIsSolved(true);
					break;
				}

				while(value > size) {		//if an invalid number is entered
					out.println("Invalid input.\nPlease enter numbers between 1 & "+size+" or enter '0' or a negative number to terminate.");
					value = input.nextInt();
					if(value < 1)
						showExitMessage();
					if(value>0 && value<=size)
						break;
				}
			}
			/********************************************************************
			 *********************EDIT + PRINT THE BOARD*************************
			 ********************************************************************/	
			if(!Board.isSolved()) {
				
				boolean goesInRow = Board.rowChecker(x_coordinate, value);
				boolean goesInColumn = Board.columnChecker(y_coordinate, value);
				boolean goesInBox =  Board.boxChecker(x_coordinate, y_coordinate, value);
				
				if(goesInRow && goesInColumn && goesInBox)
							Board.editBoard(size, x_coordinate, y_coordinate, value);
				else {
					if(!goesInRow)
						out.println("Your number cannot go in this row.");
					if(!goesInColumn)
						out.println("Your number cannot go in this column.");
					if(!goesInBox)
						out.println("Your number cannot go in this box.");
				}
			}
		}
	}
	
	

	/**
	 * printBoard call that will be made every time the user inputs a value in the board
	 * @param size
	 * @param backendSudoku
	 */
	public void printBoard(int size, int[][] array) {
		if(size==4) {
			out.println("+---+---+---+---+");
			out.println("| "+array[0][0]+" | "+array[0][1]+ " ! "+array[0][2]+" | "+array[0][3]+" |  ");
			out.println("+---+---*---+---+");
			out.println("| "+array[1][0]+" | "+array[1][1]+ " ! "+array[1][2]+" | "+array[1][3]+" |  ");
			out.println("+===+===*===+===+");
			out.println("| "+array[2][0]+" | "+array[2][1]+ " ! "+array[2][2]+" | "+array[2][3]+" |  ");
			out.println("+---+---*---+---+");
			out.println("| "+array[3][0]+" | "+array[3][1]+ " ! "+array[3][2]+" | "+array[3][3]+" |  ");
			out.println("+---+---+---+---+");

		}

		if(size==9) {
			out.println("+---+---+---*---+---+---*---+---+---+");
			out.println("| "+array[0][0]+" | "+array[0][1]+" | "+array[0][2]+" ! "
					+ ""+array[0][3]+" | "+array[0][4]+" | "+array[0][5]+" ! "
					+ ""+array[0][6]+" | "+array[0][7]+" | "+array[0][8]+" |");
			out.println("+---+---+---*---+---+---*---+---+---+");
			out.println("| "+array[1][0]+" | "+array[1][1]+" | "+array[1][2]+" ! "
					+ ""+array[1][3]+" | "+array[1][4]+" | "+array[1][5]+" ! "
					+ ""+array[1][6]+" | "+array[1][7]+" | "+array[1][8]+" |");			
			out.println("+---+---+---*---+---+---*---+---+---+");
			out.println("| "+array[2][0]+" | "+array[2][1]+" | "+array[2][2]+" ! "
					+ ""+array[2][3]+" | "+array[2][4]+" | "+array[2][5]+" ! "
					+ ""+array[2][6]+" | "+array[2][7]+" | "+array[2][8]+" |");
			out.println("+===+===+===*===+===+===*===+===+===+");
			out.println("| "+array[3][0]+" | "+array[3][1]+" | "+array[3][2]+" ! "
					+ ""+array[3][3]+" | "+array[3][4]+" | "+array[3][5]+" ! "
					+ ""+array[3][6]+" | "+array[3][7]+" | "+array[3][8]+" |");			
			out.println("+---+---+---*---+---+---*---+---+---+");
			out.println("| "+array[4][0]+" | "+array[4][1]+" | "+array[4][2]+" ! "
					+ ""+array[4][3]+" | "+array[4][4]+" | "+array[4][5]+" ! "
					+ ""+array[4][6]+" | "+array[4][7]+" | "+array[4][8]+" |");
			out.println("+---+---+---*---+---+---*---+---+---+");
			out.println("| "+array[5][0]+" | "+array[5][1]+" | "+array[5][2]+" ! "
					+ ""+array[5][3]+" | "+array[5][4]+" | "+array[5][5]+" ! "
					+ ""+array[5][6]+" | "+array[5][7]+" | "+array[5][8]+" |");			
			out.println("+===+===+===*===+===+===*===+===+===+");
			out.println("| "+array[6][0]+" | "+array[6][1]+" | "+array[6][2]+" ! "
					+ ""+array[6][3]+" | "+array[6][4]+" | "+array[6][5]+" ! "
					+ ""+array[6][6]+" | "+array[6][7]+" | "+array[6][8]+" |");			
			out.println("+---+---+---*---+---+---*---+---+---+");
			out.println("| "+array[7][0]+" | "+array[7][1]+" | "+array[7][2]+" ! "
					+ ""+array[7][3]+" | "+array[7][4]+" | "+array[7][5]+" ! "
					+ ""+array[7][6]+" | "+array[7][7]+" | "+array[7][8]+" |");			
			out.println("+---+---+---*---+---+---*---+---+---+");
			out.println("| "+array[8][0]+" | "+array[8][1]+" | "+array[8][2]+" ! "
					+ ""+array[8][3]+" | "+array[8][4]+" | "+array[8][5]+" ! "
					+ ""+array[8][6]+" | "+array[8][7]+" | "+array[8][8]+" |");			
			out.println("+---+---+---*---+---+---*---+---+---+");
		}

	}

	public void showSolvedMessage(String string) {
		out.println(string);
	}

	/**
	 * R5: User Terminates the game
	 */
	public void showExitMessage() {
		out.println("You selected '0'.\n"
				+ "Game over.");
		Board.setIsSolved(true);
	}
}