package edu.utep.cs.cs3331.sudoku;

public class Board {

    private int size;
    private int[][] matrix;

    public Board(int size) {
        if(size == 9){
            this.size = size;
            this.matrix = new int[size][size];
        }
        else {
            this.size = 4;
            this.matrix = new int[4][4];
        }
    }

    public int getSize(){
        return size;
    }

    public int getEntry(int i, int j) {
        return matrix[i][j];
    }
    //make sure everything has been adjusted to the new row and column perspective 1 - size
    public boolean validEntry(int row, int col, int entry) {
        if(row > size || col > size)
            return false;
        return (rowColCheck(row - 1, col - 1, entry) && boxCheck(row, col, entry));
    }

    private boolean rowColCheck(int row, int col, int entry) {
        for(int i = 0; i < size; i++){
            if(matrix[row][i] == entry || matrix[i][col] == entry)
                return false;
        }
        return true;
    }

    private boolean boxCheck(int row, int col, int entry) {
        int rowBound = (int) ((row - 1)/Math.sqrt(size));
        rowBound = (int) (rowBound * Math.sqrt(size));
        int colBound = (int) ((col - 1)/Math.sqrt(size));
        colBound = (int) (colBound * Math.sqrt(size));
        for(int i = rowBound; i < rowBound + (int) (Math.sqrt(size)); i++) {
            for(int j = colBound; j < colBound + (int) (Math.sqrt(size)); j++) {
                if(entry == matrix[i][j])
                    return false;
            }
        }
        return true;
    }

    public void deleteEntry(int x, int y){
        matrix[x][y] = 0;
    }

    public void setEntry(int x, int y, int entry) {
        matrix[x][y] = entry;
    }

    public boolean isSolved() {
        for(int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if(matrix[i][j] == 0)
                    return false;
            }
        }
        return true;
    }
}
