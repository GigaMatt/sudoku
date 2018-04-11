package sudoku.model;

import java.util.Iterator;
import java.util.Set;

public class Solver implements BoardSolver {
	
	Board b = new Board();
	
	public Board solveBoard(Board board) {
		
		if(isSolvable(board)) {
			b = board;
			
			backTracking(b);
			
			return b;
		}else {
			return board;
		}
		
	}
	
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
