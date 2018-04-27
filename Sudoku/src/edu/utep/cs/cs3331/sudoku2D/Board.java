package edu.utep.cs.cs3331.sudoku2D;

/**@author Anthony Moran */

import java.util.ArrayList;
import java.util.List;

public class Board {
	/** For easy access to the size of the Sudoku Board.*/
	private int size;
	
	/** For easy access to the square root of the size of the Sudoku Board.*/
	private int sqrt;
	
	/** Representation of the Sudoku Board in terms of the individual squares.*/
	public List<Square> contents;

    /** Create a new board of default size 4. */
	public Board() {
		this(4);
	}
	
	/** Create a new board of the given size. 
	 * @param size The size of the requested board.
	 */
	public Board(int size) {
		this.size = size;
		this.sqrt = (int)Math.sqrt(size);
		createList(size);
	}
	
	/** Creates a new ArrayList of squares to represent the Sudoku Board.
	 * @param size The size of the current board. 
	 */
	private void createList(int size){
		contents = new ArrayList<Square>(size*size);
		for(int x=0;x<size;x++) {
			for(int y=0;y<size;y++) {
				contents.add(new Square(x,y));
			}
		}
	}
	
	/** Checks if the board has been solved.
	 * @return if the board is solved.
	 */
	public boolean isSolved() {
		for(int i=0;i<size;i++){
			if((!solvedRow(i)) || (!solvedColumn(i))) return false;
		}
		for(int i=0;i<size;i+=sqrt) {
			for(int j=0;j<size;j+=sqrt) {
				if(!solvedSquare(i,j)) return false;
			}
		}
		return true;
	}
	
	/** Method to get the size of the current board.
	 * @return size of the board. 
	 */
	public int size() {
		return size;
	}
	
	/** Checks if the row of a Sudoku Board is solved.
	 * @param x 0-based index on row location of the selected square.
	 * @return if the row is solved.
	 */
	private boolean solvedRow(int x) {
		int sum = 0;
		for(int i=0;i<size;i++) sum += contents.get(x*size+i).getValue();
		int total = size*(size+1)/2;
		if(sum == total) return true;
		return false;
	}
	
	/** Checks if the column of a Sudoku Board is solved.
	 * @param y 0-based index on column location of the selected square.
	 * @return if the column is solved.
	 */
	private boolean solvedColumn(int y) {
		int sum = 0;
		for(int i=0;i<size;i++) sum += contents.get(i*size+y).getValue();
		int total = size*(size+1)/2;
		if(sum == total) return true;
		return false;
	}
	
	/** Checks if one of the small squares inside a Sudoku Board is solved.
	 * @param x 0-based index on row location of the selected square.
	 * @param y 0-based index on column location of the selected square.
	 * @return if the larger square that the selected square is a part of is solved.
	 */
	private boolean solvedSquare(int x, int y) {
		int startx = ((x)/sqrt) * sqrt; //finds starting index for the square
		int starty = ((y)/sqrt) * sqrt; //that the (x,y) coordinates are given
		int sum = 0;
		int total = size*(size+1)/2;
		for(int i=0;i<sqrt;i++) {
			for(int j=0;j<sqrt;j++) {
				sum += contents.get((startx+i)*size+(starty+j)).getValue();
			}
		}
		if(sum == total) return true;
		return false;
	}
	
	/** Changes the entry of the board if it is a valid move.
	 * @param x 0-based index on row location of the selected square.
	 * @param y 0-based index on column location of the selected square.
	 * @param n value being requested to put in the selected square.
	 * @return if there was a change to the board.
	 */
	public boolean change(int x, int y, int n) {
		if(!isValid(x,y,n)) return false;
		else {
			Square temp = contents.get(x*size+y);
			temp.setValue(n);
			contents.set(x*size+y, temp);
		}
		return true;
	}
	
	/** Checks if the requested move is valid.
	 * @param x 0-based index on row location of the selected square.
	 * @param y 0-based index on column location of the selected square.
	 * @param n value being requested to put in the selected square.
	 * @return if the move is valid.
	 */
	boolean isValid(int x, int y, int n) {
		if((x<0) || (x>size) || (y<0) || (y>size) || (n<0) || (n>size)) return false;
		if((inSameSquare(x,y,n)) || (inSameRC(x,y,n))) return false;
		return true;
	}
	
	/** Checks if the requested value is a duplicate in either the row or column.
	 * @param x 0-based index on row location of the selected square.
	 * @param y 0-based index on column location of the selected square.
	 * @param n value being requested to put in the selected square.
	 * @return if the requested value has a duplicate in either the row or column.
	 */
	private boolean inSameRC(int x, int y, int n) {
		for(int i=0;i<size;i++) {
			if((contents.get(i*size+y).getValue() == n) && (n != 0)) return true; //same column
			if((contents.get(x*size+i).getValue() == n) && (n != 0)) return true; //same row
		}
		return false;
	}

	/** Checks if the requested value has a duplicate in the same square.
	 * @param x 0-based index on row location of the selected square.
	 * @param y 0-based index on column location of the selected square.
	 * @param n value being requested to put in the selected square.
	 * @return if the value has a duplicate in the larger square that the selected square is part of.
	 */
	private boolean inSameSquare(int x, int y, int n) {
		int startx = ((x)/sqrt) * sqrt;
		int starty = ((y)/sqrt) * sqrt;
		for(int i=0;i<sqrt;i++) {
			for(int j=0;j<sqrt;j++) {
				if((contents.get((startx+i)*size+(starty+j)).getValue() == n) && (n != 0)) return true;
			}
		}
		return false;
	}
	
	//clones the original board so we can manipulate it without affecting the original board
	public Board clone() {
		Board temp = new Board(this.size);
		temp.contents.clear();
		for(int i=0;i<contents.size();i++) {
			Square s = new Square();
			Square tmp = contents.get(i);
			s = tmp.clone();
			temp.contents.add(s);
		}
		return temp;
	}
}
