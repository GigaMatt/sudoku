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

import java.util.Random;

public class Board {
	private int size;
	//public boolean isSolved;

	public Board() {
		this(4);	//creates a 4x4 board
	}

	public Board(int size) {	//creates the board to be of size n
		this.size = size;
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
			int[][] backendSudoku = new int[size][size];
			printBoard(array);
		}
		if(size==9) {
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
			printBoard(array);
		}

	}
	
	
	public int[][] editBoard(int size, int x, int y, int value){//, int[][] backendSudoku) {
		//backendSudoku[x][y] = value;
		//FIXME: May have to send back to the ConsoleUI to print
		return backendSudoku;
	}


	//print the board
	public void printBoard(int size, int[][] array) {
		
	}
	
	//FIXME: 
	private static Random rand = new Random();

	public boolean isSolved() {
		return rand.nextBoolean();
	}
}