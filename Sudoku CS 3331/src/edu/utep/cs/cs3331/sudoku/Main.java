package edu.utep.cs.cs3331.sudoku;

public class Main {

    private static ConsoleUI ui;
    private static Board board;

    public static void main(String[] args){
        play();
    }

    private static void play() {
        ui = new ConsoleUI();
        ui.welcome();
        int size = ui.getSize();
        ui.instructions(size);
        board = new Board(size);

        while (!board.isSolved()) {
            ui.printBoard(board);
            ui.standard();
            int entry = ui.getEntry();
            if (entry == -1)
                break;
            int row = ui.row();
            int col = ui.col();
            if (row < 0 || col < 0)
                ui.notValid();
            else {
                if (entry == 0 && row <= board.getSize() && row <= board.getSize())
                    board.deleteEntry(row - 1, col - 1);
                else if (board.validEntry(row, col, entry))
                    board.setEntry(row - 1, col - 1, entry);
                else
                    ui.notValid();
            }
        }

        if (board.isSolved()){
            ui.printBoard(board);
            ui.congrats();
        }else ui.tryAgain();

    }

}
