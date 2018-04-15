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

import java.util.Iterator;
import java.util.Set;

//methods in this class probably do not work
//expect them to take a long time
public class Solver implements BoardSolver {
	
	Board b = new Board(9);
	
	public Board solveBoard(Board board){
		
		if(solve(board))
			return b;
		else
			return board;
		
	}
	
	private boolean solve(Board board) {
		
		b = board;
		
		for(int i = 0; i < b.size; i++){
			for(int j = 0; j < b.size; j++){
				if(b.getEntry(i,j) == 0){
					Set<Integer> pvSquare = b.getPossibleValues(i,j);
					Iterator<Integer> itr = pvSquare.iterator();
					while(itr.hasNext()){
						b.setEntry(i, j, (int) itr.next());
						if(solve(board))
							return true;
					}
					return false;
				}
			}
		}
		return true;
		
	}
	
	public boolean isSolvable(Board board) {
		
<<<<<<< HEAD
		return solve(board);
=======
		if(board.isSolved()==true) {
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
>>>>>>> 110df3b762d742e5424f661c15ae8a67f26f06fd
		
	}
	
}
