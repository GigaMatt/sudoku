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

	public Board(int size) {
		this.size = size;
	}
	//FIXME: 
	private static Random rand = new Random();
	
	public boolean isSolved() {
		return rand.nextBoolean();
	}
}
/*
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
*/