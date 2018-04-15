package sudoku.model;

import java.util.Iterator;
import java.util.Set;

//methods in this class probably do not work
//expect them to take a long time
public class Solver implements BoardSolver {
	
	Board b = new Board();
	
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
		
		return solve(board);
		
	}
	
}
