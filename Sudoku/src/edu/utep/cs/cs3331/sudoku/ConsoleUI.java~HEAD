package edu.utep.cs.cs3331.sudoku;

/* Name: Anthony Moran
 * Course: CS 3331
 * HW 1a
 * T/Th 1:30-2:50
 */

import java.io.*;

public class ConsoleUI {
	private InputStream in;
	private PrintStream out;
	private int size = 0;
	
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
	
	public int size() throws IOException {
		BufferedReader test = new BufferedReader(new InputStreamReader(in)); //so reader will read the integer rather than the byte
		boolean valid = false;
		out.print("Enter the size of the board (4,9). "); //can change later to handle larger
		String str = test.readLine();
		while(!checkNum(str)) {
			out.print("Invalid size, please enter a square number (4,9). ");
			str = test.readLine();
		}
		int size = Integer.parseInt(str);
		str = "";
		while(!valid) {
			if(size != 4 && size != 9) size = 2;//will change if larger sizes are needed
			if(isSquare(size)) valid = true;
			else {
				while(!isSquare(size)) {
					str = "";
					while(!checkNum(str)) {
						if(isSquare(size)) break;
						out.print("Invalid size, please enter a square number (4,9). ");
						str = test.readLine();
					}
					size = Integer.parseInt(str);
					if(size == 4 || size == 9) break; //these two will change if larger sizes are needed
					else size = 2;
				}
			}		
		}
		this.size = size;
		return size;
	}
	
	private boolean isSquare(int n) {
		int sqrt = (int)Math.sqrt(n);
		if(sqrt*sqrt == n) return true;
		return false;
	}
	
	public void showMessage(String msg) {
		out.println(msg);
	}
	
	private void horizontalDiv() {
		int sqrt = (int)Math.sqrt(size);
		out.print("  +");
		for(int j=0;j<sqrt;j++) {
			for(int k=0;k<(4*sqrt-1);k++) {
				out.print("-");
			}
			out.print("+");
		}
		out.println();
	}
	
	public void showBoard(Board b) {
		int i = 0;
		int n = 1;
		int sqrt = (int)Math.sqrt(size);
		out.print("x/y ");
		for(int j=1;j<=size;j++) out.print(j + "   ");
		out.println();
		while(i < 2*size) {
			if(i%(2*sqrt) == 0) horizontalDiv();
			if((i+1)%2 == 0) out.println(); //puts a line after printing out a row of the board unless it's before a divider
			else { //prints out the values for each section in board
				out.print(n + " |");
				for(int j=1;j<=size;j++) {
					if(b.showEntry(n,j) == 0) out.print("   |");
					else out.print(" " + b.showEntry(n, j) + " |");
				}
				if(n%sqrt != 0) out.println();
				n++;
			}
			i++;
		} horizontalDiv();
	}

	public void requestAction(Board b) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		out.println("Enter a number (x y n) or -1 in any position to quit. Enter 0 for n to clear an entry.");
		String[] answer = validEntry(reader.readLine().split(" "));;
		checkQuit(answer); //the following ensures the given entry is valid
		while(!b.isValid(Integer.parseInt(answer[0]), Integer.parseInt(answer[1]), Integer.parseInt(answer[2]))) {
			out.println("Number " + answer[2] + " in not permitted at square (" + answer[0] + ", " + answer[1] + ")");
			showBoard(b);
			out.println("Enter a number (x y n) or -1 to quit. Enter 0 for n to clear an entry.");
			answer = validEntry(reader.readLine().split(" "));	
			checkQuit(answer);
		} //sets the valid entry
		b.change(Integer.parseInt(answer[0]), Integer.parseInt(answer[1]), Integer.parseInt(answer[2]));
	}
	
	private void checkQuit(String[] str) {
		for(int i=0;i<str.length;i++) {
			if(str[i].length() >0) {
				if(Integer.parseInt(str[i]) == -1) {
					out.println("-1 has been entered, quitting the game. Bye!");
					System.exit(0);
				}
			}
		}
	}
	
	private boolean checkEntry(String[] answer){ //ensures integer coordinates are passed
		for(int i=0;i<answer.length;i++) {
			if(!checkNum(answer[i])) return false;
		}
		return true;
	}
	
	private boolean checkNum(String str) { //ensures an integer is entered
		try {
			int test = Integer.parseInt(str);
			}
		catch(Exception e) { 
			return false;
			}
		return true;
	}
	
	private String[] validEntry(String[] answer) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String curr;
		while(!checkEntry(answer)) { //ensures a viable option is entered
			out.println("Something was entered not in the form (x y n), please try again.");
			curr = reader.readLine();
			answer = curr.split(" ");
		}
		return answer;
	}

	public String boardType() throws IOException { //checks whether to start with blank or partial board
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		out.println("What kind of board would you like (blank or partial)?");
		String answer = reader.readLine();
		boolean valid = false;
		while(!valid) {
			if(answer.compareTo("blank") == 0 || answer.compareTo("partial") == 0) break;
			else {
				out.println("Something other than blank or partial was entered, please try again.");
				answer = reader.readLine();
			}
		}
		return answer;
	}

	public int getLevel() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		out.println("Enter the number for your difficultly level (1 = easy, 2 = medium, 3 = hard).");
		String answer = reader.readLine();
		while(!checkNum(answer)) {
			out.println("Invalid level entered, please enter a valid number (1,2,3).");
			answer = reader.readLine();
		}
		int level = Integer.parseInt(answer);
		if(level<1 || level>3) {
			out.println("Invalid level entered.");
			level = getLevel();
		}
		return level;
	}
}
