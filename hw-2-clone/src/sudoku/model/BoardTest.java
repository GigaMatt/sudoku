package sudoku.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

class BoardTest {
	
	private Board board;
	
	@BeforeEach
	public void setUp() {
		board = new Board();
	}
	
	@Test
	void testBoardSize() {
		assertEquals(9, board.size); //default size
		board = new Board(4);
		assertEquals(4, board.size); //change size
		board = new Board(1);
		assertEquals(9, board.size); //change size to undefined size
	}
	
	@Test
	void testBoardSquares() {
		//test all the squares were initialized and have a initial value of 0, 9 by 9 case
		for(int i =0; i < board.size; i++) {
			for(int j = 0; j < board.size; j++) {
				assertEquals(0, board.getEntry(i,j));
			}
		}
		//test all the squares were initialized and have a initial value of 0, 4 by 4 case
		board = new Board(4);
		for(int i =0; i < board.size; i++) {
			for(int j = 0; j < board.size; j++) {
				assertEquals(0, board.getEntry(i,j));
			}
		}
	}
	
	@Test
	void testSetEntryGetEntry() {
		assertEquals(0, board.getEntry(2, 2)); //default value
		board.setEntry(2, 2, 2); //setting square 2,2 to 2
		assertEquals(2, board.getEntry(2, 2)); //changed value
		board.setEntry(2, 2, 10); //trying to change square 2,2 to 10
		assertEquals(2, board.getEntry(2, 2)); //value was not possible
	}
	
	@Test
	void testValidEntry() {
		assertTrue(board.validEntry(0, 0, 1)); //trying to add a value in range
		assertFalse(board.validEntry(0, 0, 11)); //trying to add a value out of range
		board.setEntry(0, 0, 2); //setting square 0,0 to 2
		assertFalse(board.validEntry(0, 1, 2)); //same row
		assertFalse(board.validEntry(1, 0, 2)); //same column
		assertFalse(board.validEntry(1, 1, 2)); //same block
	}	
	
	@Test
	void testDeleteEntry() {
		board.setEntry(5, 8, 4); //setting a value
		assertEquals(4, board.getEntry(5, 8)); //testing that the value was added
		board.deleteEntry(5, 8); //deleting the value
		assertEquals(0, board.getEntry(5, 8)); //testing that the value was deleted
		board.deleteEntry(1, 6); //deleting an empty square
		assertEquals(0, board.getEntry(1, 6)); //testing that the value was not altereds
	}
	
	@Test
	void testIsSolved() {
		board = new Board(4);
		assertFalse(board.isSolved());
		
		//solves a 4 by 4 board
		int count = 0;
		for(int i = 0; i < board.size; i++) {
			board.setEntry(0, i, (count%4 + 1));
		}
		count = 2;
		for(int i = 0; i < board.size; i++) {
			board.setEntry(1, i, (count%4 + 1));
		}
		count = 1;
		for(int i = 0; i < board.size; i++) {
			board.setEntry(2, i, (count%4 + 1));
		}
		count = 3;
		for(int i = 0; i < board.size; i++) {
			board.setEntry(3, i, (count%4 + 1));
		}
		
		assertTrue(board.isSolved());
	}
	
}
