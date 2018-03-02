package sudoku.model;

import java.util.ArrayList;
import java.util.List;

/** An abstraction of Sudoku puzzle. */
public class Board {
	
	public int size;
    private List<Square> squares;
    
    public Board() {
    	this.size = 9;
    	squares = new ArrayList<Square>(81);
    }

    public Board(int size) {
        this.size = size;
    	squares = new ArrayList<Square>(size*size);
    	for(int i = 0; i < size; i++) {
    		for(int j = 0; j < size; j++) {
    			squares.add(new Square(i, j));
    		}
    	}
    }

    public int size(){
        return size;
    }
    
    public Square getSquare(int i, int j) {
    	return squares.get(i*size + j);
    }
    
    public int getEntry(int i, int j) {
    	return getSquare(i,j).value;
    }
    
    //make sure everything has been adjusted to the new row and column perspective 1 - size
    public boolean validEntry(int row, int col, int entry) {
        if(row >= size || col >= size)
            return false;
        return (rowColCheck(row, col, entry) && boxCheck(row, col, entry));
    }

    private boolean rowColCheck(int row, int col, int entry) {
        for(int i = 0; i < size; i++) {
        	if (entry == squares.get(size*i + col).value || entry == squares.get(size*row + i).value) {
        		return false;
        	}
        }
        return true;
    }

    private boolean boxCheck(int row, int col, int entry) {//this method is not working properly
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

    public void deleteEntry(int x, int y){
        squares.get(size*x + y).value = 0;
    }

    public void setEntry(int x, int y, int entry) {
        squares.get(size*x + y).value = entry;
    }

    public boolean isSolved() {
        for(int i = 0; i < squares.size(); i++)
        	if(squares.get(i).value == 0)
        		return false;
        return true;
    }
    
}
