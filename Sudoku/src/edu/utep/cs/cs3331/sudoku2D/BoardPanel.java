package edu.utep.cs.cs3331.sudoku2D;

/**@author Anthony Moran */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

/**
 * A special panel class to display a Sudoku board modeled by the
 * {@link sudoku.model.Board} class. You need to write code for
 * the paint() method.
 *
 * @see sudoku.model.Board
 * @author Yoonsik Cheon
 */
@SuppressWarnings("serial")
public class BoardPanel extends JPanel {
    
	public interface ClickListener {
		
		/** Callback to notify clicking of a square. 
		 * 
		 * @param x 0-based column index of the clicked square
		 * @param y 0-based row index of the clicked square
		 */
		void clicked(int x, int y);
	}
	
    /** Background color of the board. */
	private static final Color boardColor = new Color(247, 223, 150);

    /** Board to be displayed. */
    private Board board;

    /** Width and height of a square in pixels. */
    private int squareSize = 0;

    /** Create a new board panel to display the given board. */
    public BoardPanel(Board board, ClickListener listener) {
        this.board = board;
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
            	int xy = locateSquaree(e.getX(), e.getY());
            	if (xy >= 0) {
            		listener.clicked(xy / 100, xy % 100);
            		Square s = board.contents.get(xy/100*board.size()+xy%100);
            		//set color of clicked board to magenta so it stands out
            		s.setColor(Color.MAGENTA);
            		repaint();
            	}
            }
        });
    }

    /** Set the board to be displayed. */
    public void setBoard(Board board) {
    	this.board = board;
    }
    
    /**
     * Given a screen coordinate, return the indexes of the corresponding square
     * or -1 if there is no square.
     * The indexes are encoded and returned as x*100 + y, 
     * where x and y are 0-based column/row indexes.
     */
    private int locateSquaree(int x, int y) {
    	if (x < 0 || x > board.size() * squareSize
    			|| y < 0 || y > board.size() * squareSize) {
    		return -1;
    	}
    	int xx = x / squareSize;
    	int yy = y / squareSize;
    	return xx * 100 + yy;
    }

    /** Draw the associated board. */
    @Override
    public void paint(Graphics g) {
        super.paint(g); 

        // determine the square size
        Dimension dim = getSize();
        squareSize = Math.min(dim.width, dim.height) / board.size();

        // draw background
        g.setColor(boardColor);
        g.fillRect(0, 0, squareSize * board.size(), squareSize * board.size());
        
        
        g.setColor(Color.GRAY);

        //draws grid
        for(int i=1;i<=board.size();i++) {
        	if(i%(int)Math.sqrt(board.size()) == 0) { //lines at end/start of a new big square
        		g.setColor(Color.BLACK);
        		g.drawLine(i*squareSize, 0, i*squareSize, squareSize * board.size());
            	g.drawLine(0, i*squareSize, squareSize * board.size(), i*squareSize);
            	g.setColor(Color.GRAY);
        	}
        	else { //lines inside a big square
        		g.drawLine(i*squareSize, 0, i*squareSize, squareSize * board.size());
        		g.drawLine(0, i*squareSize, squareSize * board.size(), i*squareSize);
        	}
        }
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, squareSize * board.size(), squareSize * board.size()); //draw border
        
        //make sure only the clicked square is colored magenta
        for(int i=0;i<board.contents.size();i++) {
        	Square s = board.contents.get(i);
        	if(!s.getColor().equals(boardColor)) {
        		if(s.set) continue;
        		else g.setColor(Color.MAGENTA);
        		g.fillRect(s.x*squareSize, s.y*squareSize, squareSize, squareSize);
        		g.setColor(Color.BLACK);
        	}
        }
        
        //display contents of board
        displayBoard(g,dim);
        
    }

    public void displayBoard(Graphics g, Dimension dim) {
    	for(int i=0;i<board.size();i++) {
        	for(int j=0;j<board.size();j++) {
        		Square s = board.contents.get(j*board.size()+i);
        		int value = board.contents.get(i*board.size()+j).getValue();
        		if(value != 0) {
        			if(s.set) g.setColor(Color.RED); //if the space is set, make the string color red
        			g.drawString(Integer.toString(value), 2*dim.width/(5*board.size()) + 
        					j*squareSize, 7*dim.height/(11*board.size()) + i*squareSize);
        			g.setColor(Color.BLACK);
        		}
        	}
        }
    }
}
