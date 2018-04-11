package sudoku.model;

import java.util.Iterator;
import java.util.Set;

//methods in this class probably do not work
//expect them to take a long time
public class Solver implements BoardSolver {
	
	Board b = new Board();
	
	//returns the solved board
	//call this method by doing: board = solveBoard(board);
	//display solved board and see if it was solved correctly
	public Board solveBoard(Board board) {
		
		if(isSolvable(board)) {
			b = board;
			
			backTracking(b);
			
			return b;
		}else {
			return board;
		}
		
	}
	
	//returns true if the current board configuration is solvable
	//returns false if the current board configuration is not solvable
	public boolean isSolvable(Board board) {
		
		b.squares = board.squares;//copy board
		
		for(int i = 0; i < board.squares.size(); i++) 
			if(board.squares.get(i).emptySet())
				return false;
		
		backTracking(b);
		if(b == null) {
			return false;
		}
		return true;
		
	}
	
	//this is used for solving
	private void backTracking(Board board) {
		
		if(board.isSolved()) {
			return;
		}else if(isSolvable(board)) {
			for(int i = 0; i < board.squares.size(); i++) {
				if(board.squares.isEmpty()) {
					Set<Integer> possibleMoves = board.squares.get(i).getPossibleMoves();
					for(Iterator<Integer> j = possibleMoves.iterator(); j.hasNext(); ) {
						board.squares.get(i).value = j.next();
						backTracking(board);
					}
				}
			}
		}else {
			board = null;
			return;
		}
		
	}
	
}
