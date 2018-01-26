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
		out.print("What size do you want?");
		int boardSize = size.nextInt();
		return boardSize;
	}


	//FIXME: make sure to import the size of n, so that the board knows how 
	public void askNumber() {	//public void askNumber(int n)
		input = new Scanner(System.in);
		int sudokuNumber = input.nextInt();
		out.print("Enter '0' to terminate.");
		if(sudokuNumber == 0)
			showExitMessage(sudokuNumber);

		if(sudokuNumber > 9 || sudokuNumber < 0) {	//if(sudokuNumber > n || sudokuNumber < (n-n))
			//showExitMessage(sudokuNumber);	FIXME: DELETE THIS
			out.println("Invalid input.\nPlease enter numbers between 1 & 9.");	//out.println("Invalid input.\nPlease enter numbers between 1 & "+n);

		}
	}


	//R5: User terminates game



	public void printBoard() {
		//FIXME: DO SOMETHING

	}


	public void showSolvedMessage(String string) {
		out.println(string);
	}

	public void showExitMessage(int zero) {
		out.println("You selected '0'.\n"
				+ "Game over.");
	}

}
