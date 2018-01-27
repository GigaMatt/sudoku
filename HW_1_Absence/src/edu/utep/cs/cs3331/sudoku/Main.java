/**
 * HW 1
 * Main.java
 * By: Matthew S Montoya
 * Instructor: Dr. Yoosnek Cheon
 * TAs: Jesus Valles && Jose Cabrera
 * Purpose: To practice implementing a sudoku game via the user console
 * Last Modified: 25 January 2018
 */

package edu.utep.cs.cs3331.sudoku;
import java.util.*;

public class Main {
	private static ConsoleUI ui = new ConsoleUI();
	private static Board board;
	private static Scanner userInput;

	public static void main(String[] args) {
		play();		
	}

	private static void play() {
		ui.welcome();

		//R1: Board supports size 4x4 && 9x9
		int size = ui.askSize();
		board = new Board(size);	//creates the backend board
		userInput = new Scanner(System.in);
		while(!board.isSolved()) {
			ui.playGame(size);
			
			//print board as-is
			
			
			//Do something
		}
	}
}
