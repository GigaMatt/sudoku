/**
 * CS3331
 * @version 5.0 (05/02/2018)
 * 
 * @author Anthony Ayo 
 * @author Anthony Moran
 * @author Enrique Salcido
 * @author Matthew Montoya
 **/

package edu.utep.cs.cs3331.sudoku2D;


import java.awt.Color;

public class Square {
	/** Value of the given square.*/
	private int value = 0;
	
	/** 0-based (x,y) coordinates of the square in the Sudoku board.*/
	public int x,y;
	// for knowing if the board can be edited or not
	public boolean set = false;
	// for telling the panel what color to paint this square, default is the color of the board
	public Color color = new Color(247, 223, 150);
	
	/** Default Constructor*/
	public Square() {}
	
	/** Constructor given the (x,y) coordinate location of the square.
	 * @param x 0-based index value of the row the square belongs to.
	 * @param y 0-based index value of the column the square belongs to.
	 */
	public Square(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/** Sets the value of the square.
	 * @param value The requested number of the square. 
	 */
	public void setValue(int value) {
		this.value = value;
	}
	
	/** Gives the value of the square.
	 * @return value of the square. 
	 */
	public int getValue() {
		return value;
	}
	//getter & setter for color
	public void setColor(Color color) {
		this.color = color;
	}
	
	/**
	 * Gets the color of the square
	 * @return the color of the square 
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * create a clone of the current square so the clone can be edited without affecting this square
	 * @return the clone of the square
	 */
	public Square clone() {
		Square tmp = new Square();
		tmp.x = this.x;
		tmp.y = this.y;
		tmp.setValue(this.value);
		tmp.setColor(this.color);
		tmp.set = this.set;
		return tmp;
	}
}
