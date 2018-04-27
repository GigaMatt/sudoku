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

public class Move {
	int x,y,value;
	
	public Move() {
		this(0,0,0);
	}
	
	public Move(int x, int y, int value) {
		this.x = x;
		this.y = y;
		this.value = value;
	}
}
