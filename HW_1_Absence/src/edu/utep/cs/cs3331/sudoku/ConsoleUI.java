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

	//R1
	public int askSize() {
		size = new Scanner(System.in);
		out.print("What board size do you want?\n"
				+ "Enter '4' or '9'");
		int boardSize = size.nextInt();
		if (boardSize != 4 || boardSize != 9) {
			out.println("Incorrect size.");
			askSize();
		}	
		return boardSize;
	}
	
	

	public void playGame(int size) {
		//STEP 1: Print the empty shitty board
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
			playGame(size);
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
		********************************************************************/		out.println("Enter the value you wish to put in or enter '0' or a negative number to terminate.");
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
		board.editBoard(x_coordinate, y_coordinate, value);
			
		
		
		//STEP 3: Print new board
		//REPEAR
		
	}


	//FIXME: make sure to import the size of n, so that the board knows how 
	/**
	 * askNumber(int n)
	 * 		This will take in n, the size of the board that the user is playing with, then determine if the user can use 1-4 04 1-9
	 */

			board.editBoard(size, x_coordinate, y_coordinate, value, backendSudoku);
			printBoard(backendSudoku);

		}
	}


	//R5: User terminates game



	public void printBoard(int size) {
		//FIXME: DO SOMETHING

	}


	public void showSolvedMessage(String string) {
		out.println(string);
	}

	public void showExitMessage() {
		out.println("You selected '0'.\n"
				+ "Game over.");
	}
}