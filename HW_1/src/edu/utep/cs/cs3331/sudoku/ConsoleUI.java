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
		System.out.println("Welcome to Sudoku!");
	}
	
	public int askSize() {
		//FIXME: test this
		return 0;
	}

	public void showMessage(String string) {
		out.println("Solved!");
	}

}
