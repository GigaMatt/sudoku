/*
 * CS 3331
 * Homework 5
 * @author Anthony Ayo
 * @author Anthony Moran
 * @author Matthew Montoya
 * @author Enrique Salcido

 * Purpose: To practice implementing Java Networking
 * Last Modified: 1 May 2018
 */

package edu.utep.cs.cs3331.hw5;
import java.awt.Color;

public class Square {
	/* Value of the given square.*/
	private int value = 0;
	/* 0-based (x,y) coordinates of the square in the Sudoku board.*/
	public int x,y;
	/* For knowing if the board can be edited or not*/
	public boolean set = false;
	/* For telling the panel what color to paint this square, default is the color of the board*/
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
	
	
	/*Setter for color*/
	public void setColor(Color color) {
		this.color = color;
	}
	
	/*Getter for color*/
	public Color getColor() {
		return color;
	}
	
	
	/*Create a clone of the current square so the clone can be edited without affecting this square*/
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