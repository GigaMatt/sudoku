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
import java.util.ArrayList;
import java.util.Random;

public class BTSolver implements Solver{
	Random r = new Random(); //used to randomly arrange a newly generated solved board
	public Board b = new Board();
	
	public Board solve(Board b) {
		if(isSolveable(b)) {
			if(!BTSolve(b)) {
				return null;
			}
			makeNormalColors(b);
			return b;
		}
		else return null;
	}
	
	public BTSolver(Board b) {
		this.b = b;
	}

	/*
	 * Naive check for if a board is solvable
	 * @see edu.utep.cs.cs3331.sudoku2D.Solver#isSolveable(edu.utep.cs.cs3331.sudoku2D.Board)
	 * @param b the board we are checking
	 * @return whether or not the board is solvable
	 */
	public boolean isSolveable(Board b) {
		if(!hasOptions()) return false;
		else return true;
	}
	
	/**
	 * Back tracking implementation of solving a board
	 * @param b the board we are checking
	 * @return whether or not the board is solvable
	 */
	private boolean BTSolve(Board b) {
		if(b.isSolved()) return true; //board is solved, return
		if(!isSolveable(b)) return false; //board is not solvable, return and try other options
		else {
			Square s = getNextSmallestBlank(b); //find square with the least options, leads to failure early
			if(s.getValue() == 0) {
				for(int k=1;k<=b.size();k++) { //test all values 1-9
					if(b.isValid(s.x, s.y, k)) { 
						b.change(s.x, s.y, k); //if move is valid then make it and continue trying to solve
						if(BTSolve(b)) return true;
						b.change(s.x, s.y, 0); //move wasn't valid, try next number
					}
				}
			}
		}
		return false; //checked all possible values for square s, board must be unsolvable
	}
	
	/**
	 * Returns the square with the least amount of options
	 * @param b the board which we are checking
	 * @return the square with the least amount of options
	 */
	private Square getNextSmallestBlank(Board b) {
		ArrayList<Integer> options = getEntryOptions();
		int min = Integer.MAX_VALUE;
		Square curr = new Square();
		//find the square with least amount of options
		for(int i=0;i<options.size();i++) {
			int num = options.get(i);
			if((num != 0) && (num<min)) {//ensures we choose a square that did have an option
				if(b.contents.get(i).getValue() == 0) curr = b.contents.get(i);
			}
		}
		return curr;
	}
	
	/**
	 * Checks that all blank spaces has options
	 * @return true/false if options are available
	 */
	private boolean hasOptions() {
		ArrayList<Integer> options = getEntryOptions();
		for(int i=0;i<options.size();i++) {
			if((options.get(i) == 0) && (b.contents.get(i).getValue() == 0)) return false;
		}
		return true;
	}
	
	/**
	 * Get the amount of options for each space
	 * @return an arraylist with each space's options.
	 */
	private ArrayList<Integer> getEntryOptions() {
		ArrayList<Integer> options = new ArrayList<Integer>();
		for(int i=0;i<b.contents.size();i++) {
			int num = 0;
			Square s = b.contents.get(i);
			if(s.getValue() != 0) { //if the space has already been given a value, add a 0 to the list to keep spaces
									//in line with the list of squares
				options.add(0);
				continue;
			}
			for(int j=1;j<=b.size();j++) {
				if(b.isValid(s.x, s.y, j)) num++; //checking how many numbers are valid for given square
			}
			options.add(num);
		}
		return options;
	}
	
	/**
	 * Makes it so all spaces are the same color of the board
	 * @param b the board we intend to edit
	 */
	private void makeNormalColors(Board b) {
		for(int i=0;i<b.contents.size();i++) b.contents.get(i).setColor(new Color(247, 223, 150));
	}
	
	/**
	 * Generates and permutes a solved 9x9 board
	 * @return a solved board
	 */
	public Board genSolved9() {
		b = new Board(9);
		b = solve(b);
		int[] locs = new int[3];
		//get initial location of the first 3 rows
		locs = createNewLocs(locs);
		ArrayList<Square> rows123 = new ArrayList<Square>();
		ArrayList<Square> rows456 = new ArrayList<Square>();
		ArrayList<Square> rows789 = new ArrayList<Square>();
		//store the 3 sections of rows in their corresponding list, i.e. rows 1,2,3 go to list rows123, etc.
		for(int i=0;i<3;i++) {
			for(int j=0;j<9;j++) {
				rows123.add(b.contents.get(i*9+j));
				rows456.add(b.contents.get((i+3)*9+j));
				rows789.add(b.contents.get((i+6)*9+j));
			}
		}
		//clear current list of squares since they aren't needed anymore
		b.contents.clear();
		//permutes the sections of rows, each section gets a random permutation so it creates 27 possibilities from the
		//initial solved board
		rows123 = switch3Rows(rows123,locs[0],locs[1],locs[2]);
		locs = createNewLocs(locs);
		rows456 = switch3Rows(rows456,locs[0],locs[1],locs[2]);
		locs = createNewLocs(locs);
		rows789 = switch3Rows(rows789,locs[0],locs[1],locs[2]);
		//get the order of the 3 sections of rows
		locs = createNewLocs(locs);
		//determine where each section of rows goes
		if(locs[0] == 1) {//rows123 first
			b.contents.addAll(rows123);
			if(locs[1] == 2) {//rows456 second
				b.contents.addAll(rows456);
				b.contents.addAll(rows789);
			} else {//rows789 second
				b.contents.addAll(rows789);
				b.contents.addAll(rows456);
			}
		}
		else if(locs[1] == 1) {//rows456 first
			b.contents.addAll(rows456);
			if(locs[0] == 2) {//rows123 second
				b.contents.addAll(rows123);
				b.contents.addAll(rows789);
			} else {//rows789 second
				b.contents.addAll(rows789);
				b.contents.addAll(rows123);
			}
		}
		else {//rows789 first
			b.contents.addAll(rows789);
			if(locs[0] == 2) {//rows123 second
				b.contents.addAll(rows123);
				b.contents.addAll(rows456);
			} else {//rows456 second
				b.contents.addAll(rows456);
				b.contents.addAll(rows123);
			}
		}
		//add the newly permuted rows to the original board and return the board
		for(int i=0;i<b.size();i++) {
			for(int j=0;j<b.size();j++) {
				b.contents.get(i*b.size()+j).x = i;
				b.contents.get(i*b.size()+j).y = j;
			}
		}
		return b;
	}
	
	/**
	 * Gets a random permutation of locations for the 3 rows
	 * @param locs array of locations
	 * @return an integer array of locations
	 */
	private int[] createNewLocs(int[] locs) {
		int loc1 = r.nextInt(3)+1; //where first row goes
		int loc2 = r.nextInt(2)+1; //where second row goes
		int loc3 = 0; //where third row goes
		//makes sure that the locations are all distinct based on loc1 and where loc2 goes
		//if loc2=1 then it goes "before" loc1 (subtract 1 from loc1 then make sure it is 1-3)
		//otherwise it goes "after" loc1 (add 1 to loc1 then make sure it is 1-3)
		if(loc2 == 1) {
			if(loc1 == 1) loc2=3;
			else loc2 = loc1-1;
			if(loc1 == 3) loc3=1;
			else loc3 = loc1+1;
		}
		else {
			if(loc1 == 1) loc3=3;
			else loc3 = loc1-1;
			if(loc1 == 3) loc2=1;
			else loc2 = loc1+1;
		}
		int[] locs2 = {loc1,loc2,loc3};
		return locs2;
	}
	
	/**
	 * Generate a solved 4x4 and permute it
	 * @return a 4x4 solved board
	 */
	public Board genSolved4() {
		b = new Board(4);
		b = solve(b);
		//get random locations for the placement of first 2 rows and then order of first two rows
		int loc1 = r.nextInt()%2+1;
		int loc2 = r.nextInt()%2+1;
		ArrayList<Square> rows12 = new ArrayList<Square>();
		ArrayList<Square> rows34 = new ArrayList<Square>();
		//fill in corresponding lists with their rows
		for(int i=0;i<2;i++) {
			for(int j=0;j<4;j++) {
				rows12.add(b.contents.get(i*4+j));
				rows34.add(b.contents.get((i+2)*4+j));
			}
		}
		//clear original contents since they aren't needed
		b.contents.clear();
		if(loc1 == 1) { //rows12 go first
			if(loc2 == 1) { //rows12 in order
				b.contents.addAll(rows12);
				loc2 = r.nextInt()%2+1;//randomly choose if the second set of rows should be switched or not
				if(loc2 == 1) b.contents.addAll(rows34); //rows34 in order
				else { //rows34 w/4 first
					b.contents.addAll(switchRows(rows34));
				}
			} else { //rows12 w/2 first
				b.contents.addAll(switchRows(rows12));
				loc2 = r.nextInt()%2+1;//randomly choose if the second set of rows should be switched or not
				if(loc2 == 1) b.contents.addAll(rows34); //rows34 in order
				else { //rows34 w/4 first
					b.contents.addAll(switchRows(rows34));
				}
			}
		} else {//rows34 go first
			if(loc2 == 1) { //rows34 in order
				b.contents.addAll(rows34);
				loc2 = r.nextInt()%2+1;//randomly choose if the second set of rows should be switched or not
				if(loc2 == 1) b.contents.addAll(rows12); //rows12 in order
				else { //rows12 w/2 first
					b.contents.addAll(switchRows(rows12));
				}
			}
			else { //rows34 w/4 first
				b.contents.addAll(switchRows(rows34));
				loc2 = r.nextInt()%2+1;//randomly choose if the second set of rows should be switched or not
				if(loc2 == 1) b.contents.addAll(rows12); //rows12 in order
				else { //rows12 w/2 first
					b.contents.addAll(switchRows(rows12));
				}
			}
		}
		//add back in the newly permuted rows and return the board
		for(int i=0;i<b.size();i++) {
			for(int j=0;j<b.size();j++) {
				b.contents.get(i*b.size()+j).x = i;
				b.contents.get(i*b.size()+j).y = j;
			}
		}
		return b;
	}
	
	/**
	 * Method to switch two rows for permuting the 4x4
	 * @param rows the rows we intend to switch
	 * @return an ArrayList with the switched rows
	 */
	private ArrayList<Square> switchRows(ArrayList<Square> rows){
		ArrayList<Square> rows2 = new ArrayList<Square>();
		for(int i=1;i>=0;i--) {
			for(int j=0;j<4;j++) {
				rows2.add(rows.get(i*4+j));
			}
		}
		return rows2;
	}
	
	
	/**
	 * Switch around the 3 given rows based on the provided locations for row1, row2, row3
	 * @param rows the ArrayList of squares with the rows we will edit
	 * @param loc1 the first location row
	 * @param loc2 the second location row
	 * @param loc3 the third location row
	 * @return an ArrayList with the switched rows
	 */
	private ArrayList<Square> switch3Rows(ArrayList<Square> rows, int loc1, int loc2, int loc3){
		ArrayList<Square> rows2 = new ArrayList<Square>();
		if(loc1 == 1) {//row 1 goes first
			for(int i=0;i<rows.size()/3;i++) rows2.add(rows.get(i));
			if(loc2 == 2) {//row 2 second
				for(int i=rows.size()/3;i<2*rows.size()/3;i++) rows2.add(rows.get(i));
				for(int i=2*rows.size()/3;i<rows.size();i++) rows2.add(rows.get(i));
			}
			else {//row 3 second
				for(int i=2*rows.size()/3;i<rows.size();i++) rows2.add(rows.get(i));
				for(int i=rows.size()/3;i<2*rows.size()/3;i++) rows2.add(rows.get(i));
			}
		} else if(loc2 == 1) {//row 2 first
			for(int i=rows.size()/3;i<2*rows.size()/3;i++) rows2.add(rows.get(i));
			if(loc1 == 2) {//row 1 second
				for(int i=0;i<rows.size()/3;i++) rows2.add(rows.get(i));
				for(int i=2*rows.size()/3;i<rows.size();i++) rows2.add(rows.get(i));
			}else {//row 3 second
				for(int i=2*rows.size()/3;i<rows.size();i++) rows2.add(rows.get(i));
				for(int i=0;i<rows.size()/3;i++) rows2.add(rows.get(i));
			}
		} else {//row 3 first
			for(int i=2*rows.size()/3;i<rows.size();i++) rows2.add(rows.get(i));
			if(loc1 == 2) {//row 1 second
				for(int i=0;i<rows.size()/3;i++) rows2.add(rows.get(i));
				for(int i=rows.size()/3;i<2*rows.size()/3;i++) rows2.add(rows.get(i));
			} else {//row 2 second
				for(int i=rows.size()/3;i<2*rows.size()/3;i++) rows2.add(rows.get(i));
				for(int i=0;i<rows.size()/3;i++) rows2.add(rows.get(i));
			}
		}
		return rows2;
	}
}
