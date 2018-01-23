package edu.utep.cs.cs3331.sudoku;

public class Main {
	private ConsoleUI ui = new ConsoleUI();
	private Board board = new Board();
	
	public void main(String[] args) {
		new Main().play();		
	}

	private void play() {
		ui.welcome();
		int size = ui.askSize();
		board = new Board(size);
		while(!board.isSolved()) {
			//Do something
			
		}
		ui.showMessage("Solved!");
		// TODO Auto-generated method stub
		
	}

}
