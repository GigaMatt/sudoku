package edu.utep.cs.cs3331.sudoku;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class ConsoleUI {

    private InputStream in;
    private PrintStream out;
    private Scanner scnr;

    public ConsoleUI() {
        in = System.in;
        out = System.out;
        scnr = new Scanner(in);
    }

    public ConsoleUI(InputStream in, PrintStream out){
        this.in = in;
        this.out = out;
        this.scnr = new Scanner(this.in);
    }

    public void welcome() {
        out.println("Welcome to Sudoku");
    }

    public void instructions(int size) {
        out.println("The row starts from the top and goes up and down");
        out.println("The column starts from the left and moves right");
        out.println("Please enter numbers between 1 - " + size);
    }

    public void printBoard(Board board) {//make this pretty if you have time
        for(int i = 0; i < (2 * board.getSize()); i++)
                out.print(" .");
        out.println();
        for(int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                if((i+1)%Math.sqrt(board.getSize()) == 0){
                    if ((j)% Math.sqrt(board.getSize()) == 0)
                        out.print("!.");
                    else
                        out.print("|.");
                    if (board.getEntry(i, j) != 0)
                        out.print(board.getEntry(i, j));
                    else
                        out.print(".");
                    out.print(".");
                }else {
                    if (j % Math.sqrt(board.getSize()) == 0)
                        out.print("!_");
                    else
                        out.print("|_");
                    if (board.getEntry(i, j) != 0)
                        out.print(board.getEntry(i, j));
                    else
                        out.print("_");
                    out.print("_");
                }
            }
            out.print("!");
            out.println();
        }
    }

    public void standard() {
        out.println("Enter -1 to quit");
        out.println("Enter 0 to delete a number in cell (x,y)");
    }

    public int getEntry() {
        out.println("Enter the entry");
        return Integer.parseInt(scnr.nextLine());
    }

    public int getSize() {
        out.println("Enter size");
        return Integer.parseInt(scnr.nextLine());
    }

    public void notValid() {
        out.println("Entry not valid");
    }

    public void congrats() {
        out.println("Congratulations!");
    }

    public void tryAgain() {
        out.println("Try Again!");
    }

    public int row() {
        out.println("Enter the row");
        return Integer.parseInt(scnr.nextLine());
    }

    public int col() {
        out.println("Enter the column");
        return Integer.parseInt(scnr.nextLine());
    }
}
