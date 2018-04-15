/*
 * CS 3331
 * Homework 4
 * @author Anthony Ayo
 * @author Matthew Montoya
 * @author Enrique Salcido
 * @author Yoonsik Cheon
 * Purpose: To practice implementing Java Graphics
 * Last Modified: 12 April 2018
 */

package sudoku.model;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/** An abstraction of Sudoku puzzle. */
public class Board{

	/** Size of the Sudoku game. */
	public int size;

	/** x coordinate of square chosen. */
	public int x;

	/** y coordinate of square chosen. */
	public int y;

	/** The squares the compose the board. */
	public static List<Square> squares;
	
	//boolean[][] secureNumbers = new boolean[size][size];



	/**
	 * Initiate Board

	public Board() {
		this.size = 9;
		int startNum =0;
		int entry = 0;
		squares = new ArrayList<Square>(size*size);
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				squares.add(new Square(i, j));
			}
		}


		//FILL THE BOARD WITH SOLVEABLE NUMBERS
		for(int r = 0; r < size; r++){					//0 to n-1
			startNum = (int) (Math.sqrt(size) * (r % Math.sqrt(size)) + (r/Math.sqrt(size)));
			for(int s = 0; s < size; s++) {				//c =0 to n-1
				entry = (((startNum + s) % size) + 1);
				setEntry(r,s,entry);	//setEntry(i, j, entry)
			}
		}


		//RANDOMLY DELETE 56 numbers on the board
		int fill = size;
		Random rand = new Random();
		if(fill == 9) {
			for (int traverse = 0; traverse < 81; traverse++) {
				int x = rand.nextInt(9);
				int y = rand.nextInt(9);
				setEntry(x,y,0);
			}
		}
	}
	 */


	/**
	 * Initiate Board & Create ArrayList of n*n squares
	 * @param size
	 */
	public Board(int size) {
		this.size = size;
		int startNum =0;
		int entry = 0;

		squares = new ArrayList<Square>(size*size);
		for(int i = 0; i < size; i++) {
			startNum = (int) (Math.sqrt(size) * (i % Math.sqrt(size)) + (i/Math.sqrt(size)));
			for(int j = 0; j < size; j++) {
				squares.add(new Square(i, j));
				entry = (((startNum + j) % size) + 1);
				//secureNumbers[i-1][j-1] = true;
				setEntry(i,j,entry);	//setEntry(i, j, entry)
			}
		}


		//FILL THE BOARD WITH SOLVEABLE NUMBERS
/*		for(int r = 0; r < size; r++){					//0 to n
			startNum = (int) (Math.sqrt(size) * (r % Math.sqrt(size)) + (r/Math.sqrt(size)));
			for(int s = 0; s < size; s++) {	
				entry = (((startNum + s) % size) + 1);
				secureNumbers[r][s] = true;
				setEntry(r,s,entry);	//setEntry(i, j, entry)
			}
		}*/
		
		//SCAN THE BOARD AND IMPLEMENT A T/F 2-D ARRAY FOR USER, THEN REFERENCE W/SUDOKU DIALOG


		//RANDOMLY DELETE 64 numbers on the board
		Random rand = new Random();
		int remaining = 60;
		while(remaining > 0) {
			int x = rand.nextInt(9);
			int y = rand.nextInt(9);
			if(checkRandomEntry(x,y)) {
				setEntry(x,y,0);
				//secureNumbers[x][y] = false;
				remaining--;
			}
		}
	}


	private Square getSquare(int i, int j) {
		return squares.get(i*size + j);
	}

	/**
	 * Return value of a square
	 * @param i
	 * @param j
	 * @return
	 */
	public int getEntry(int i, int j) {
		return getSquare(i,j).value;
	}

	/**
	 * Ensure user's desired coordinates are valid
	 * @param row
	 * @param col
	 * @param entry
	 * @return
	 */
	public boolean validEntry(int row, int col, int entry) {
		if(row >= size || col >= size)
			return false;
		if(entry > size) 
			return false;
		return (rowColCheck(row, col, entry) && boxCheck(row, col, entry));
	}

	/**
	 * 
	 * @param row
	 * @param col
	 * @param entry
	 * @return
	 */
	private boolean rowColCheck(int row, int col, int entry) {
		for(int i = 0; i < size; i++) {
			if (entry == squares.get(size*i + col).value || entry == squares.get(size*row + i).value) {
				return false;
			}
		}
		return true;
	}


	/**
	 * Ensure user's coordinate value is valid per sudoku rules
	 * @param row
	 * @param col
	 * @param entry
	 * @return
	 */
	private boolean boxCheck(int row, int col, int entry) {
		int rowBound = (int) (row/Math.sqrt(size));
		rowBound = (int) (rowBound * Math.sqrt(size));
		int colBound = (int) (col/Math.sqrt(size));
		colBound = (int) (colBound * Math.sqrt(size));

		for(int i = rowBound; i < (rowBound + (int) (Math.sqrt(size))); i++) {
			for(int j = colBound; j < (colBound + (int) (Math.sqrt(size))); j++) {
				if(entry == getEntry(i, j))
					return false;
			}
		}

		return true;

	}

	/**
	 * Delete coordinate value
	 * @param x
	 * @param y
	 */
	public void deleteEntry(int x, int y){
			squares.get(size*x + y).value = 0;

	}


	/**
	 * Checks if random coordinate entry is 0
	 * @param x
	 * @param y
	 */
	public boolean checkRandomEntry(int x, int y){
		if(squares.get(size*x + y).value == 0)
				return false;
		return true;
	}
	
	
	//public boolean areSecureNumbers(int x, int y) {
		//return secureNumbers[x][y];
		// TODO Auto-generated method stub
		//return false;
	//}


	/**
	 * Insert user's desired value into desired coordinate
	 * @param x
	 * @param y
	 * @param entry
	 */
	public void setEntry(int x, int y, int entry) {
		if(entry <= size && entry >= 0) {
			squares.get(size*x + y).value = entry;
			squares.get(size*x + y).updateSet(entry);
		}
	}


	/**
	 * Check if sudoku board is solved
	 * @return
	 */
	public boolean isSolved() {
		for(int i = 0; i < squares.size(); i++)
			if(squares.get(i).value == 0)
				return false;
		return true;
	}


	public Set<Integer> getPossibleValues(int x, int y) {
		Set<Integer> s = new HashSet<Integer>();
		s.addAll(Arrays.asList(new Integer[] {1,2,3,4,5,6,7,8}));

		for(int i = 0; i < size; i++) {
			if(getEntry(x,i) != 0) {
				s.remove(getEntry(x,i));
			}
		}

		if(s.isEmpty())
			return s;

		for(int i = 0; i < size; i++) {
			if(getEntry(i,y) != 0) {
				s.remove(getEntry(x,i));
			}
		}

		if(s.isEmpty())
			return s;

		return s;

	}

}
