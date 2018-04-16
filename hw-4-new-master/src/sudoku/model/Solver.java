package sudoku.model;

//methods in this class probably do not work
//expect them to take a long time
public class Solver implements BoardSolver {

	Board b = new Board();

	public Board solveBoard(Board board){

		b = board;

		if(solve(b)) {
			return b;
		}else
			return board;

	}

	private boolean solve(Board board) {

		for(int i = 0; i < board.size; i++){
			for(int j = 0; j < board.size; j++){
				if(board.getEntry(i,j) == 0){
					for(int entry = 1; entry < 10; entry++) {
						if(board.validEntry(i, j, entry)) {
							board.setEntry(i, j, entry);
							if(solve(board))
								return true;
							else
								board.setEntry(i, j, 0);
						}
					}
					return false;
				}
			}
		}
		return true;
	}

	public boolean isSolvable(Board board) {
		for(int i = 0; i < b.size; i++) {
			for(int j = 0; j < b.size; j++) {
				if( (board.getEntry(i, j) != 0)){
					if (b.getEntry(i, j) != board.getEntry(i, j))
						return false;
				}
			}
		}
		return true;
	}
}