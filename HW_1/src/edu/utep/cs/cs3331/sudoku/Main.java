package edu.utep.cs.cs3331.sudoku;

import java.util.*;


public class Main {
	private ConsoleUI ui = new ConsoleUI();
	private Board board = new Board();
	
	public void main(String[] args) {
		new Main().play();		
	}

	private void play() {
		ui.welcome();
		
		//R1: Board supports size 4x4 && 9x9
		int size = ui.askSize();
		board = new Board(size);
		
		int sizeSquared = Math.pow(size, 2);
		
		Scanner input = new Scanner(System.in);
		while(!board.isSolved()) {
			//Do something
			
			
			
			
			
			//R5: User terminates game
			System.out.println("Enter '0' to terminate.")
			if(userInput.equalsIgnoreCase("0")) {
				ui.showExitMessage(userInput);
				break;
			}
			
			if(userInput > 9 || userInput < 0)
				System.out.println("Invalid input.\nPlease enter numbers between 1 & 9.");
			
			if(sizeSquared == 0) {
				board.isSolved();
				break;
				
		}
			
			/*
			 * package test1;

import java.util.*;

public class hello {
	public static void main(String[] args) {
		int n = 9;
		createBoard(n);
	}

	public static void createBoard(int n) {
		int[][] array = new int[(n*2)+1][n];
		for(int i=0; i<array.length; i++) {
			for(int j=0; j<array[i].length; j++) {
				if(n==9)
					if(i%2==0) {
						if((i == 6) || (i == 12))
							if(j == 3 || j==6)
								System.out.print("*===");
							else 
								System.out.print("+===");
						else if(i+4 != Math.sqrt(n))
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
			 */
		
		
		ui.showMessage("Solved!");
		// TODO Auto-generated method stub
		
	}

}
