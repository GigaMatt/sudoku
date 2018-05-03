package edu.utep.cs.cs3331.sudoku;

/* Name: Anthony Moran
 * Course: CS 3331
 * HW 1a
 * T/Th 1:30-2:50
 */

import java.io.IOException;
import org.json.JSONException;

public class Main {
	private ConsoleUI ui = new ConsoleUI();
	private Board board;

	public static void main(String[] args) throws IOException, JSONException {
		new Main().play();

	}

	private void play() throws IOException, JSONException {
		ui.welcome();
		int size = ui.size();
		String type = ui.boardType();
		board = new Board(size);
		if(type.compareTo("partial") == 0) {
			int level = ui.getLevel();
			boolean worked = board.createPartial(level);
			if(worked) ui.showMessage("Partially solved board created!");
			else ui.showMessage("Unable to create partially solved board, creating blank board.");
		}
		while(!board.isSolved()) {
			ui.showBoard(board);
			ui.requestAction(board);
		}
		ui.showBoard(board);
		ui.showMessage("Solved!");
	}
}
