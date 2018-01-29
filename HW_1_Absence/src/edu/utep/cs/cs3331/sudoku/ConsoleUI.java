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
		this(System.in, System.out);
	}

	public ConsoleUI(InputStream in, PrintStream out) {
		in = System.in;
		out = System.out;
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
		if (boardSize != 4 || boardSize != 9) {
			out.println("Incorrect size.");
			askSize();
		}	

		//return boardSize to the main
		//main will then call the board to be built
		return boardSize;
	}

	public void playGame(Board board, int size) {
		//STEP 1: Print the empty bad board
		printBoard(size);

		//STEP 2: Have user select x,y and value, then add value (pass through real values to backendView)
		input=new Scanner(System.in);

		/********************************************************************
		 ************************GET THE X-COORDINATE*************************
		 ********************************************************************/		out.println("Select the coordinate you'd like to edit or enter '0 or a negative number' to terminate.\nLet's begin with the X-Value:");
		 int x_coordinate = input.nextInt();
		 if(x_coordinate < 1)
			 showExitMessage();
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

			 /********************************************************************
			  ************************GET THE Y-COORDINATE*************************
			  ********************************************************************/
			 out.println("Great! You chose "+x_coordinate+". Now, for the Y-Value. Select the coordinate you'd like to edit or enter '0' or a negative number to terminate.");
			 int y_coordinate = input.nextInt();
			 if(y_coordinate <1)
				 showExitMessage();
			 while(y_coordinate > size) {		//if an invalid number is entered
				 out.println("Invalid input.\nPlease enter numbers between 1 & "+size+" or enter '0' or a negative number to terminate.");
				 y_coordinate = input.nextInt();
				 if(y_coordinate < 1)
					 showExitMessage();
				 if(y_coordinate>0 && y_coordinate<=size)
					 break;
			 }

			 /********************************************************************
			  ********************GET THE COORDINATE VALUE*************************
			  ********************************************************************/		
			 out.println("Enter the value you wish to put in or enter '0' or a negative number to terminate.");
			 int value = input.nextInt();
			 if(value <1)
				 showExitMessage();
			 while(value > size) {		//if an invalid number is entered
				 out.println("Invalid input.\nPlease enter numbers between 1 & "+size+" or enter '0' or a negative number to terminate.");
				 y_coordinate = input.nextInt();
				 if(value < 1)
					 showExitMessage();
				 if(value>0 && value<=size)
					 break;
			 }
			 /********************************************************************
			  **************************EDIT THE BOARD****************************
			  ********************************************************************/	
			 //Edit the board with the new information
			 int[][] backendSudoku = board.editBoard(size, x_coordinate, y_coordinate, value);
			 
			 /********************************************************************
			  *************************PRINT THE BOARD****************************
			  ********************************************************************/	
			 //STEP 3: Print the new board
			 printBoard(size, backendSudoku);
		 }

		 //board.editBoard(size, x_coordinate, y_coordinate, value);
		 //printBoard will be called from board class
	}
	
	 /********************************************************************
	  ***********************PLAYING THE GAME ENDS************************
	  ********************************************************************/	

	/**
	 * Initial size of the board
	 * @param size
	 */
	public void printBoard(int size){
		if(size==4) {
			String[][] array = new String[(size*2)+1][(size*2+1)];
			for(int i=0; i<array.length; i++) {
				for(int j=0; j<array[i].length; j++) {
					if(i%2==0) {
						if(j%2!=0)		//if j is odd
							array[i][j] = "---"; //7 IFF using tab
						if(i==size)
							array[i][j] = "===";	//7 IFF using tab
						if(j%2==0)		//if j is even
							array[i][j] = "+";
						if(j==size)
							array[i][j] = "*";
					}
					if(i%2!=0) {
						if(j%2!=0)		//if j is odd
							array[i][j] = "   ";
						if(j%2==0)		//if j is even
							array[i][j] = "|";
						if(j==size)
							array[i][j] = "!";
					}
				}
			}
		}

		//if(size==9)
		else{
			int[][] array = new int[(size*2)+1][size];
			for(int i=0; i<array.length; i++) {
				for(int j=0; j<array[i].length; j++) {
					if(size==9)
						if(i%2==0) {
							if((i == 6) || (i == 12))
								if(j == 3 || j==6)
									System.out.print("*===");
								else 
									System.out.print("+===");
							else if(i+4 != Math.sqrt(size))
								if(j == 3 || j==6)
									System.out.print("*---");
								else 
									System.out.print("+---");
						}
						else {
							if((j==3 || j==6))
								System.out.print("!   ");
							else
								System.out.print("|   ");
						}
				}
				if(i%2==0)
					System.out.println("+");
				else
					System.out.println("|");
			}
		}
	}


	/**
	 * printBoard call that will be made every time the user inputs a value in the board
	 * @param size
	 * @param backendSudoku
	 */
	public void printBoard(int size, int[][] backendSudoku) {
		if(size==4) {
			String[][] array = new String[(size*2)+1][(size*2+1)];
			for(int i=0; i<array.length; i++) {
				for(int j=0; j<array[i].length; j++) {
					if(i%2==0) {
						if(j%2!=0)		//if j is odd
							array[i][j] = "---"; //7 IFF using tab
						if(i==size)
							array[i][j] = "===";	//7 IFF using tab
						if(j%2==0)		//if j is even
							array[i][j] = "+";
						if(j==size)
							array[i][j] = "*";
					}
					if(i%2!=0) {
						if(j%2!=0)		//if j is odd
							array[i][j] = "   ";
						if(j%2==0)		//if j is even
							array[i][j] = "|";
						if(j==size)
							array[i][j] = "!";
					}
				}
			}
			//Fill in the bad String board with out info from the backend board
			for(int i=0; i<backendSudoku.length; i++) {
				for(int j=0; j<backendSudoku[i].length; j++) {
					if(backendSudoku[i][j] > 0)
						array[(2*i)+1][(2*j)+1] = " "+backendSudoku[i][j]+" ";
				}
			}
		}

		//if(size==9)
		else{
			int[][] array = new int[(size*2)+1][size];
			for(int i=0; i<array.length; i++) {
				for(int j=0; j<array[i].length; j++) {
					if(size==9)
						if(i%2==0) {
							if((i == 6) || (i == 12))
								if(j == 3 || j==6)
									System.out.print("*===");
								else 
									System.out.print("+===");
							else if(i+4 != Math.sqrt(size))
								if(j == 3 || j==6)
									System.out.print("*---");
								else 
									System.out.print("+---");
						}
						else {
							if((j==3 || j==6))
								System.out.print("!   ");
							else
								System.out.print("|   ");
						}

				}
				if(i%2==0)
					System.out.println("+");
				else
					System.out.println("|");
			}
			//Fill in the bad integer board with out info from the backend board
			for(int i=0; i<backendSudoku.length; i++) {
				for(int j=0; j<backendSudoku[i].length; j++) {
					if(backendSudoku[i][j] > 0)
						array[(2*i)+1][(2*j)+1] = backendSudoku[i][j];
				}
			}
		}
		//end of else
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
	}
}