package sudoku.model;

public interface BoardSolver {
	Board solveBoard(Board board);
	boolean isSolvable(Board board);
}
