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
