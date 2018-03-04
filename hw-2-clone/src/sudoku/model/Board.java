/*
 * CS 3331
 * Homework 2
 * @author Enrique Salcido
 * @author Matthew S Montoya
 * Purpose: To practice implementing Java Applets & Graphics
 * Last Modified: 4 March 2018
 */

package sudoku.model;
import java.util.ArrayList;
import java.util.List;

/** An abstraction of Sudoku puzzle. */
public class Board {
	
	public int size;
	public int x;
	public int y;
    private List<Square> squares;
    
    
    /**
     * Initiate Board
     */
    public Board() {
    	this.size = 9;
    	squares = new ArrayList<Square>(81);
    }

    
    /**
     * Create ArrayList of n*n squares
     * @param size
     */
    public Board(int size) {
        this.size = size;
    	squares = new ArrayList<Square>(size*size);
    	for(int i = 0; i < size; i++) {
    		for(int j = 0; j < size; j++) {
    			squares.add(new Square(i, j));
    		}
    	}
    }

    /**
     * Return size of the board
     * @return
     */
    public int size(){
        return size;
    }
    
    public Square getSquare(int i, int j) {
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
     * Insert user's desired value into desired coordinate
     * @param x
     * @param y
     * @param entry
     */
    public void setEntry(int x, int y, int entry) {
        squares.get(size*x + y).value = entry;
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
    
}
