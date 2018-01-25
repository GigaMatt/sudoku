package edu.utep.cs.cs3331.sudoku;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

public class ConsoleUI {
	private InputStream in;
	private PrintStream out;
	
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
		Scanner size = new Scanner(System.in);
		out.print("What size do you want?");
		int boardSize = size.nextInt();
		return boardSize;
		//FIXME: test this
		//return 0;
	}
	
	public void printBoard() {
		
	}

	public void showSolvedMessage(String string) {
		out.println(string);
	}
	
	public void showExitMessage(String string) {
		out.println("You selected 'r'.\n"
				+ "Game over."));
	}

}
