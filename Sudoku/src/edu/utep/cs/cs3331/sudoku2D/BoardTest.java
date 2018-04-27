package edu.utep.cs.cs3331.sudoku2D;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Before;
import org.junit.jupiter.api.Test;

class BoardTest {
	
	private Board board = new Board(4);

	@Before
	public void setUp() throws Exception {
		board = new Board(4);
	}

	@Test
	public void testsize() {
		assertEquals(4,board.size());
		board = new Board(9);
		assertEquals(9,board.size());
		board = new Board(4);
		assertEquals(4,board.size());
		board = new Board();
		assertEquals(4,board.size());
	}

	@Test
	public void testBlank() { //makes sure a blank board was created
		for(int i=0;i<board.size()*board.size();i++) {
			assertEquals(0,board.contents.get(i).getValue());
		}
		board = new Board(9);
		for(int i=0;i<board.size()*board.size();i++) {
			assertEquals(0,board.contents.get(i).getValue());
		}
	}
	
	@Test
	public void testChange() {
		assertTrue(board.change(1, 1, 3));
		assertFalse(board.change(5, 1, 2));
		assertFalse(board.change(1, 6, 1));
		assertFalse(board.change(1,2,8));
		assertFalse(board.change(0, 0, 3));
		assertFalse(board.change(2, 1, 3));
		assertFalse(board.change(1, 3, 3));
		assertTrue(board.change(0, 0, 2));
		assertTrue(board.change(0, 0, 0));
		assertFalse(board.change(-1,2,1));
		assertFalse(board.change(2, -1, 3));
		assertFalse(board.change(1, 1, -3));
	}
	
	@Test
	public void testisSolved() {
		assertFalse(board.isSolved());
		board.change(0, 0, 1);
		assertFalse(board.isSolved());
		for(int i=0;i<board.size();i++) {
			board.change(0, i, i+1);
		}
		assertFalse(board.isSolved()); //row solved
		board.change(1, 0, 3);
		board.change(1, 1, 4);
		assertFalse(board.isSolved()); //row&square solved
		board.change(2, 0, 2);
		board.change(3, 0, 4);
		assertFalse(board.isSolved()); //row&square&column solved
		board.change(2, 0, 0);
		assertFalse(board.isSolved()); //row&column solved
		board = new Board();
		for(int i=0;i<board.size();i++) {
			board.change(i, 0, i+1);
		}
		assertFalse(board.isSolved()); //column solved
		board.change(0, 2, 3);
		board.change(1, 3, 4);
		assertFalse(board.isSolved()); //column&square solved
		board = new Board(4);
		solve4x4();
		board.change(0, 0, 0);
		assertFalse(board.isSolved()); //missing 1 entry
		board.change(0, 0, 1);
		assertTrue(board.isSolved());
	}

	private void solve4x4() {
		for(int i=0;i<4;i++) {
			board.change(0, i, i+1);
			board.change(1, i, 4-i);
			board.change(3, (i+2)%4, i+1);
			if(i<2) {
				board.change(2, (1-i)%4, i+1);
			}
		}
		board.change(2, 3, 3);
		board.change(2, 2, 4);
	}
	
}
