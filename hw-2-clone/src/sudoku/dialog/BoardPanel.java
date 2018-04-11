/*
 * CS 3331
 * Homework 2
 * @author Enrique Salcido
 * @author Matthew S Montoya
 * @author Yoonsik Cheon
 * Purpose: To practice implementing Java Applets & Graphics
 * Last Modified: 4 March 2018
 */

package sudoku.dialog;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import sudoku.model.Board;

/**
 * A special panel class to display a Sudoku board modeled by the
 * {@link sudoku.model.Board} class. You need to write code for
 * the paint() method.
 *
 * @see sudoku.model.Board
 */
@SuppressWarnings("serial")
public class BoardPanel extends JPanel {
    
	/** Callback to notify clicking of a square. 
	 * 
	 * @param x 0-based column index of the clicked square
	 * @param y 0-based row index of the clicked square
	 */
	public interface ClickListener {
		void clicked(int x, int y);
	}
	
    /** Background color of the board. */
	private static final Color boardColor = new Color(247, 223, 150);

    /** Board to be displayed. */
    private Board board;

    /** Width and height of a square in pixels. */
    private int squareSize;

    /** Create a new board panel to display the given board.
     * @param board Instance of board being worked on
     * @param listener gets the possition that was clicked on
     */
    public BoardPanel(Board board, ClickListener listener) {
        this.board = board;
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
            	int xy = locateSquaree(e.getX(), e.getY());
            	if (xy >= 0) {
            		listener.clicked(xy / 100, xy % 100);
            	}
            }
        });
    }

    /** Set the board to be displayed.
     * @param board Instance of board being worked on
     */
    public void setBoard(Board board) {
    	this.board = board;
    }
    
    /**
     * Identifies indexes of the current square
     * @param x horizontal location of square on x-y grid
     * @param y vertical location of square on x-y grid
     */
    private int locateSquaree(int x, int y) {
    	if (x < 0 || x > board.size * squareSize
    			|| y < 0 || y > board.size * squareSize) {
    		return -1;
    	}
    	int xx = x / squareSize;
    	int yy = y / squareSize;
    	return xx * 100 + yy;
    }

    /** Draw the associated board.
     * @param g Instance of Graphics
     */
    @Override
    public void paint(Graphics g) {//print numbers too
        super.paint(g); 

        // determine the square size
        Dimension dim = getSize();
        squareSize = Math.min(dim.width, dim.height) / board.size;

        // draw background
        final Color oldColor = g.getColor();
        g.setColor(boardColor); 
        g.fillRect(0, 0, squareSize * board.size, squareSize * board.size);
        g.setColor(boardColor); 
        //Color the square
        g.setColor(Color.decode("#23a008"));
		g.fillRect(squareSize * board.x, squareSize * board.y, squareSize, squareSize); 


        
        //draw the grid lines
        g.setColor(Color.black);
        for(int i = 0; i <= board.size; i++) {		//vertical lines
        	if(i%Math.sqrt(board.size) != 0) {
        		g.setColor(Color.gray);
        		g.drawLine(squareSize*i, 0, squareSize*i, Math.min(dim.width, dim.height));
        	}else {
        		g.setColor(Color.black);
        		g.drawLine(squareSize*i, 0, squareSize*i, Math.min(dim.width, dim.height));
        	}
        }
        for(int i = 0; i <= board.size; i++) {		//horizontal lines
        	if(i%Math.sqrt(board.size) != 0) {
        		g.setColor(Color.gray);
        		g.drawLine(0, squareSize*i, Math.min(dim.width, dim.height), squareSize*i);
        	}else {
        		g.setColor(Color.black);
        		g.drawLine(0, squareSize*i, Math.min(dim.width, dim.height), squareSize*i);
        	}
        }
        
        //drawing the numbers form matrix
        for(int i = 0; i < board.size; i++) {
        	for(int j = 1; j <= board.size; j++) {//from one to print out last row
        		if(board.getEntry(i, j - 1) != 0) {	
        			char[] temp = new char[] {(char) (board.getEntry(i, j - 1) + '0')};
        			g.drawChars(temp, 0, 1, (int) (squareSize*i + squareSize/2), (int) (squareSize*j - squareSize/2));
        		}
        	}
        }
        
    }

}
