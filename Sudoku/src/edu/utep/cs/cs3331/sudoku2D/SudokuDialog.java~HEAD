package edu.utep.cs.cs3331.sudoku2D;

/**@author Anthony Moran */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

/**
 * A dialog template for playing simple Sudoku games.
 * You need to write code for three callback methods:
 * newClicked(int), numberClicked(int) and boardClicked(int,int).
 *
 * @author Yoonsik Cheon
 */
@SuppressWarnings("serial")
public class SudokuDialog extends JFrame {
	
	/** Storage for board last created from solve() so that if a board is unsolvable we sub in this one */
	Board lastCheckedSol = null;
	
	/** Keep track of moves done and what can be undone/redone */
	Stack<Move> redos = new Stack<Move>();
	Stack<Move> undos = new Stack<Move>();

    /** Default dimension of the dialog. */
    private final static Dimension DEFAULT_SIZE = new Dimension(320, 420);

    private final static String IMAGE_DIR = "/image/";

    /** Sudoku board. */
    protected Board board = new Board();

    /** Special panel to display a Sudoku board. */
    private BoardPanel boardPanel;

    /** Message bar to display various messages. */
    private JLabel msgBar = new JLabel("");
    
    /** So we know what number was clicked and the size of the board.*/
    private int size = 0;
    
    /** So we know if the board has been clicked when clicking on numbers*/
    private boolean clickedBoard = false;
    /** Last clicked position on board*/
    private int x=0,y=0;
    
    int newSize = 4, difficulty = 1;
    /** Stores the buttons created in UI so that it can be freely edited*/
    private JPanel numButtons = null;
    
    private BTSolver solver = new BTSolver(board);
    /** Used for picking random squares to make blank*/
    private Random r = new Random();

    /** Create a new dialog. */
    public SudokuDialog() {
    	this(DEFAULT_SIZE);
    }
    
    /** Create a new dialog of the given screen dimension. */
    public SudokuDialog(Dimension dim) {
        super("Sudoku");
        setSize(dim);
        board = removeEntries(solver.genSolved9(),2);
        lastCheckedSol = solver.solve(board.clone());
        solver.b = board;
        this.size = 9;
        boardPanel = new BoardPanel(board, this::boardClicked);
        configureUI();
        //setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        //setResizable(false);
    }

    /**
     * Callback to be invoked when a square of the board is clicked.
     * @param x 0-based row index of the clicked square.
     * @param y 0-based column index of the clicked square.
     */
    private void boardClicked(int x, int y) {
    	if(board.contents.get(x*board.size()+y).set) { //makes it so you can't click on set boards
    		showMessage("Can't edit this entry.");
    	}
    	else{
    		if((this.x != x) || (this.y != y)) { //new space clicked, set old space to board color
    			board.contents.get(this.x*board.size()+this.y).setColor(new Color(247, 223, 150));
    		}
    		//set new space color to magenta
    		clickedBoard = true;
    		this.x = x;
    		this.y = y;
    		board.contents.get(x*size+y).setColor(Color.MAGENTA);
    		repaint();
        	showMessage(String.format("Board clicked: x = %d, y = %d",  y+1, x+1));
    	}
    }
    /** Checks if board is solved */
    private void checkSolved() {
		if(board.isSolved()) {
			int response = JOptionPane.showConfirmDialog(null,"Congratulations, you solved the board!" + 
																" Would you like to start a new game?" +
																" (Hit no or cancel to exit the game.)");
	        if(response == 0) {
	        	createNewGame(this.size, this.difficulty);
	        }
	        else System.exit(0);
		}
		
	}

	/**
     * Callback to be invoked when a number button is clicked.
     * @param number Clicked number (1-9), or 0 for "X".
     */
    private void numberClicked(int number) {
    	if(clickedBoard) {
    		if(board.change(y, x, number)) { //if the board was clicked and there was a change
    			undos.push(new Move(y,x,number)); //add move to undo stack (the undo and redo parts can be omitted)
    			solver.b = board; //update solver's board
    			showMessage("Added " + number + " to location (" + (y+1) + "," + (x+1) + ")");
    			boardPanel.repaint();
    			checkSolved(); //check if the board is now solved
    		} else showMessage("Conflict putting " + number + " in location (" + (y+1) + "," + (x+1) + ")");
    	}
    }
    /** Undo previous move, may not always work*/
    private void undo() {
    	if(undos.isEmpty()) msgBar.setText("Nothing to undo");
    	else {
    		Move move1 = undos.pop();
    		redos.push(move1);
    		if(undos.isEmpty()) board.change(move1.x,move1.y,0); //undo first move, essentially reset space to 0
    		else {
    			Move move2 = undos.peek(); //another move was made before, see what it was
    			//if it was the same square, we change the value back to what it was previously
    			if((move1.x == move2.x) && (move1.y == move2.y)) board.change(move1.x,move1.y,move2.value);
    			//otherwise, we change the current square back to 0
    			else board.change(move1.x,move1.y,0);
    		}
    	}
    }
    /** Redo previous move, may not always work*/
    private void redo() {
    	if(redos.isEmpty()) msgBar.setText("Nothing to redo");
    	else { //a move was previously undone
    		Move move1 = redos.pop(); //get what the move was and reapply it, this may be where the problem is but idk
    		board.change(move1.x, move1.y, move1.value); 
    		//push move back to the undo stack
    		undos.push(move1);
    	}
    }
    
    private void createNewGame(int size, int difficulty) {
    	this.size = size;
    	clickedBoard = false;
    	undos.clear();
    	redos.clear();
    	x=0;
    	y=0;
    	msgBar.setText("");
    	//get new solved board
    	if(size == 4) board = solver.genSolved4();
    	else board = solver.genSolved9();
    	//create blanks in board and set the board for solver/panel
    	board = removeEntries(board,difficulty);
    	lastCheckedSol = solver.solve(board.clone());
    	boardPanel.setBoard(board);
    	//set which buttons can be pressed based on board size, can be applied when clicking on a square to show what
    	//the current valid options are
    	Component[] comp = numButtons.getComponents();
    	for (int i = 0;i<comp.length;i++) {
            if (comp[i] instanceof JButton) {
            	JButton button = (JButton)comp[i];
            	String s = button.getText();
            	if(!s.equals("X")) {
            		try {
            			int k = Integer.parseInt(s);
            			if(k>size) {
            				button.setEnabled(false);
            			} else button.setEnabled(true);
            		} catch(Exception e) {}
            	}
            }
        }
    	repaint();
    }
    /** Creates blanks on the board based on difficulty, chooses spaces randomly*/
    private Board removeEntries(Board b, int difficulty) {
    	int numNeeded = 0;
    	if(b.size() == 4) {
    		if(difficulty == 1) numNeeded = 8;
    		else if(difficulty == 2) numNeeded = 10;
    		else numNeeded = 12;
    		while(numNeeded>0) {
    			int next = r.nextInt()%16;
    			//ensure we get a positive number
    			if(next<0) next+=16;
    			Square s = b.contents.get(next);
    			//make sure we select a non-blank space
    			while(s.getValue() == 0) {
    				next = r.nextInt()%16;
    				if(next<0) next+=16;
    				s = b.contents.get(next);
    			}
    			//make the space blank
    			b.change(s.x, s.y, 0);
    			numNeeded--;
    		}
    	}
    	else {
    		if(difficulty == 1) numNeeded = 30;
    		else if(difficulty == 2) numNeeded = 24;
    		else numNeeded = 17;
    		numNeeded = 81-numNeeded;
    		while(numNeeded>0) {
    			int next = r.nextInt()%81;
    			//ensure we get a positive number
    			if(next<0) next+=81;
    			Square s = b.contents.get(next);
    			//make sure we select a non-blank space
    			while(s.getValue() == 0) {
    				next = r.nextInt()%81;
    				if(next<0) next+=81;
    				s = b.contents.get(next);
    			}
    			//make the space blank
    			b.change(s.x, s.y, 0);
    			numNeeded--;
    		}
    	}
    	//mark which spaces in the board are set, for some reason the y and x values need to be switched for it 
    	//to work properly like with many other parts of the code given
    	for(int i=0;i<b.contents.size();i++) {
    		Square t = b.contents.get(i);
    		if(t.getValue() == 0) continue;
    		b.contents.get(t.y*b.size()+t.x).set = true;
    	}
    	return b;
    }

    /**
     * Display the given string in the message bar.
     * @param msg Message to be displayed.
     */
    private void showMessage(String msg) {
        msgBar.setText(msg);
    }

    /** Configure the UI. */
    private void configureUI(){
		setIconImage(createImageIcon("sudoku.png").getImage());
		setLayout(new BorderLayout());

		JPanel buttons = makeControlPanel();
		//top, left, bottom, right
		buttons.setBorder(BorderFactory.createEmptyBorder(10,16,0,16));
		add(buttons, BorderLayout.NORTH);

		JPanel jp_board = new JPanel();
		jp_board.setBorder(BorderFactory.createEmptyBorder(10,16,0,16));
		jp_board.setLayout(new GridLayout(1,1));
		jp_board.add(boardPanel);
		add(jp_board, BorderLayout.CENTER);

		msgBar.setBorder(BorderFactory.createEmptyBorder(10,16,10,0));
		add(msgBar, BorderLayout.SOUTH);

		//create menu bar
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		setJMenuBar(menuBar);
		fileMenu.setMnemonic(KeyEvent.VK_G);
		fileMenu.getAccessibleContext().setAccessibleDescription("File menu");
		menuBar.add(fileMenu);


		JMenuItem newGame = new JMenuItem("New Game", KeyEvent.VK_N);
		KeyStroke ctrlNKeyStroke = KeyStroke.getKeyStroke("control N");
		ImageIcon newGameIcon = new ImageIcon(createImageIcon("play1_resized.png").getImage());
		newGame.setIcon(newGameIcon);
		newGame.setToolTipText("Play a new game");
		newGame.setAccelerator(ctrlNKeyStroke);
		JDialog confirmation = makeConfirmationPanel();
		newGame.addActionListener(e -> {
			confirmation.setVisible(true);
		});
		fileMenu.add(newGame);
		fileMenu.addSeparator();


		//JMenuItem SOLVE_PUZZLE
		JMenuItem solvePuzzle = new JMenuItem("Solve Puzzle", KeyEvent.VK_S);
		KeyStroke ctrlSKeyStroke = KeyStroke.getKeyStroke("control S");
		ImageIcon solvePuzzleIcon = new ImageIcon(createImageIcon("bulb_resized.png").getImage());
		solvePuzzle.setIcon(solvePuzzleIcon);
		solvePuzzle.setToolTipText("Solve the puzzle for me");
		solvePuzzle.setAccelerator(ctrlSKeyStroke);
		
		solvePuzzle.addActionListener(e ->{ //solves game, if not solvable then displays that last generated solved board
			Board tmp = solver.solve(board);
			if(tmp == null) board = lastCheckedSol;
			boardPanel.setBoard(board);
			boardPanel.repaint();
			msgBar.setText("Solved");
			repaint();
		});
		fileMenu.add(solvePuzzle);
		fileMenu.addSeparator();


		//JMenuItem TEST_SOLVEABILITY
		JMenuItem testSolveability = new JMenuItem("Check Progress", KeyEvent.VK_C);
		KeyStroke ctrlCKeyStroke = KeyStroke.getKeyStroke("control C");
		ImageIcon solveableIcon =  new ImageIcon(createImageIcon("questionMark_resized.png").getImage());
		testSolveability.setIcon(solveableIcon);
		testSolveability.setToolTipText("Check if my progress is solveable");
		testSolveability.setAccelerator(ctrlCKeyStroke);
		testSolveability.addActionListener(e ->{ //checks if board is solvable
			if(!solver.isSolveable(board)) msgBar.setText("Not solvable");
			else { //passed naive test for checking if board is not solvable
				Board tmp = board.clone(); //clone board so we don't edit the current board
				msgBar.setText("Checking for solution");
				tmp = solver.solve(tmp);
				solver.b = board;
				if(tmp == null) msgBar.setText("Not solvable");
				else { //solution found, store created solution for later use if needed
					lastCheckedSol = tmp;
					msgBar.setText("Still solvable");
				}
			}
		});
		testSolveability.setToolTipText("Check if game is solvable");
		fileMenu.add(testSolveability);
		fileMenu.addSeparator();


		//JMenuItem EXIT
		JMenuItem exit = new JMenuItem("Quit Game", KeyEvent.VK_Q);
		KeyStroke ctrlQKeyStroke = KeyStroke.getKeyStroke("control Q");
		ImageIcon exitIcon =  new ImageIcon(createImageIcon("door_resized.png").getImage());

		exit.setIcon(exitIcon);
		exit.setToolTipText("Quit the game");
		exit.setAccelerator(ctrlQKeyStroke);
		fileMenu.add(exit);
		fileMenu.addSeparator();
		exit.addActionListener(e->{System.exit(0);});



		JMenu edit = new JMenu("Edit");
		fileMenu.setMnemonic(KeyEvent.VK_G);
		fileMenu.getAccessibleContext().setAccessibleDescription("Game Menu");
		menuBar.add(edit);


		//JMenuItem UNDO
		JMenuItem undo = new JMenuItem("Undo", KeyEvent.VK_U);
		KeyStroke ctrlUKeyStroke = KeyStroke.getKeyStroke("control U");
		ImageIcon undoIcon =  new ImageIcon(createImageIcon("undo_resized.png").getImage());
		undo.addActionListener(e -> {
			undo();
			repaint();
		});

		undo.setIcon(undoIcon);
		undo.setToolTipText("Undo previous move");
		undo.setAccelerator(ctrlUKeyStroke);
		edit.add(undo);
		edit.addSeparator();


		//JMenuItem REDO
		JMenuItem redo = new JMenuItem("Redo");
		KeyStroke ctrlRKeyStroke = KeyStroke.getKeyStroke("control R");
		ImageIcon redoIcon =  new ImageIcon(createImageIcon("redo_resized.png").getImage());
		redo.setIcon(redoIcon);
		redo.addActionListener(e -> {
			redo();
			repaint();
		});
		redo.setToolTipText("Redo previous move");
		redo.setAccelerator(ctrlRKeyStroke);
		edit.add(redo);
		edit.addSeparator();
	}
    /** Creates the tool bar */
    protected JToolBar createToolBar() {
    	JToolBar tb = new JToolBar("Sudoku");
    	tb.setFloatable(false);
    	JButton b1 = new JButton(createImageIcon("playbutton.png"));
    	b1.setBorder(BorderFactory.createEmptyBorder()); //needs to be done so there is no white space around button
    	JDialog confirmation = makeConfirmationPanel();
    	b1.addActionListener(e ->{
    		confirmation.setVisible(true);
    	});
    	b1.setToolTipText("Play a new game");
    	tb.add(b1);
    	tb.addSeparator(); //adds space between buttons
    	JButton b2 = new JButton(createImageIcon("plusbutton.png"));
    	b2.setBorder(BorderFactory.createEmptyBorder());
    	b2.addActionListener(e ->{ //solves game, if not solvable then displays that last generated solved board
    		Board tmp = solver.solve(board);
    		if(tmp == null) board = lastCheckedSol;
    		boardPanel.setBoard(board);
    		boardPanel.repaint();
    		msgBar.setText("Solved");
    		repaint();
    	});
    	b2.setToolTipText("Solve the game");
    	tb.add(b2);
    	tb.addSeparator();
    	JButton b3 = new JButton(createImageIcon("checkbutton.png"));
    	b3.setBorder(BorderFactory.createEmptyBorder());
    	b3.addActionListener(e ->{ //checks if board is solvable
    		if(!solver.isSolveable(board)) msgBar.setText("Not solvable");
    		else { //passed naive test for checking if board is not solvable
    			Board tmp = board.clone(); //clone board so we don't edit the current board
    			msgBar.setText("Checking for solution");
    			tmp = solver.solve(tmp);
    			solver.b = board;
    			if(tmp == null) msgBar.setText("Not solvable");
    			else { //solution found, store created solution for later use if needed
    				lastCheckedSol = tmp;
    				msgBar.setText("Still solvable");
    			}
    		}
    	});
    	b3.setToolTipText("Check if game is solvable");
    	tb.add(b3);
    	tb.addSeparator();
    	JButton b4 = new JButton(createImageIcon("restartbutton.png"));
    	b4.setBorder(BorderFactory.createEmptyBorder());
    	b4.addActionListener(e -> { //restarts the current board, keeping all set values
    		msgBar.setText("Restarting board");
    		for(int i=0;i<board.contents.size();i++) {
    			Square s = board.contents.get(i);
    			if(!board.contents.get(s.y*board.size()+s.x).set) s.setValue(0);
    		}
    		repaint();
    		msgBar.setText("Board restarted!");
    	});
    	b4.setToolTipText("Restart the current board");
    	tb.add(b4);
    	tb.addSeparator();
    	JButton b5 = new JButton(createImageIcon("undobutton.png"));
    	JButton b6 = new JButton(createImageIcon("redobutton.png"));
    	b5.setBorder(BorderFactory.createEmptyBorder());
    	b6.setBorder(BorderFactory.createEmptyBorder());
    	b5.addActionListener(e ->{
    		undo();
    		repaint();
    	});
    	b6.addActionListener(e -> {
    		redo();
    		repaint();
    	});
    	b5.setToolTipText("Undo previous move");
    	b6.setToolTipText("Redo previous move");
    	tb.add(b5);
    	tb.addSeparator();
    	tb.add(b6);
    	return tb;
    }
    /** Creates the confirmation panel for selecting a new game */
    private JDialog makeConfirmationPanel() {
    	JDialog confirmation = new JDialog();
    	JLabel settings = new JLabel("Current size and difficulty: " + newSize + " , " + difficulty);
    	confirmation.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    	confirmation.setSize(250, 250);
    	confirmation.setTitle("New Game Selection");
        confirmation.setLayout(new BorderLayout());
        JPanel decision = new JPanel(new FlowLayout());
        JButton b = new JButton("Play");
        b.addActionListener(e->{ //player chose to create a new game, get rid of dialog so the program doesn't hang on closure of main
        	confirmation.dispose();
        	createNewGame(newSize,difficulty);
        });
        decision.add(b);
        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(e -> confirmation.dispose()); //player chose to not create new game
        decision.add(cancel);
        confirmation.add(decision,BorderLayout.SOUTH);
        JButton new4 = new JButton("4x4");
        JButton new9 = new JButton("9x9");
        //4x4 or 9x9 selected, edit size selection and display the current selections for size and difficulty
        new4.addActionListener(e -> { 
        	newSize = 4;
        	settings.setText("Current size and difficulty: " + newSize + " , " + difficulty);
        });
        new9.addActionListener(e -> {
        	newSize = 9;
        	settings.setText("Current size and difficulty: " + newSize + " , " + difficulty);	
        });
        JPanel sizeDecision = new JPanel(new BorderLayout());
        JPanel sizes = new JPanel(new FlowLayout());
        JLabel label = new JLabel("Please choose the size.");
        sizeDecision.add(label,BorderLayout.NORTH);
        sizes.add(new4);
        sizes.add(new9);
        sizeDecision.add(sizes,BorderLayout.SOUTH);
        confirmation.add(sizeDecision,BorderLayout.NORTH);
        JPanel difDecisions = new JPanel(new BorderLayout());
        JPanel difNums = new JPanel(new FlowLayout());
        JLabel difmsg = new JLabel("Select your difficulty.");
        difDecisions.add(difmsg,BorderLayout.NORTH);
        JButton easy = new JButton("easy");
        JButton medium = new JButton("med");
        JButton hard = new JButton("hard");
        //difficulty selected, edit difficulty selection and display the current selections for size and difficulty
        easy.addActionListener(e -> {
        	difficulty = 1;
        	settings.setText("Current size and difficulty: " + newSize + " , " + difficulty);
        });
        medium.addActionListener(e -> {
        	difficulty = 2;
        	settings.setText("Current size and difficulty: " + newSize + " , " + difficulty);
        });
        hard.addActionListener(e -> {
        	difficulty = 3;
        	settings.setText("Current size and difficulty: " + newSize + " , " + difficulty);
        });
        difNums.add(easy);
        difNums.add(medium);
        difNums.add(hard);
        difDecisions.add(difNums,BorderLayout.CENTER);
        difDecisions.add(settings,BorderLayout.SOUTH);
        confirmation.add(difDecisions,BorderLayout.CENTER);
        return confirmation;
    }
      
    /** Create a control panel consisting of new and number buttons. */
    private JPanel makeControlPanel() {        
    	// buttons labeled 1, 2, ..., 9, and X.
    	JPanel buttonAndTB = new JPanel(new BorderLayout()); //used for creating a nice look for toolbar/number bar
    	buttonAndTB.add(createToolBar(),BorderLayout.NORTH);
    	JPanel numberButtons = new JPanel(new FlowLayout());
    	int maxNumber = board.size() + 1;
    	for (int i = 1; i <= maxNumber; i++) {
            int number = i % maxNumber;
            JButton button = new JButton(number == 0 ? "X" : String.valueOf(number));
            button.setFocusPainted(false);
            button.setMargin(new Insets(0,2,0,2));
            button.addActionListener(e -> numberClicked(number));
    		numberButtons.add(button);
    	}
    	numButtons = numberButtons;
    	numberButtons.setAlignmentX(LEFT_ALIGNMENT);
    	buttonAndTB.add(numberButtons,BorderLayout.SOUTH);
    	JPanel content = new JPanel();
    	content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));
        content.add(buttonAndTB);
        return content;
    }

    /** Create an image icon from the given image file. */
    protected ImageIcon createImageIcon(String filename) {
        URL imageUrl = getClass().getResource(IMAGE_DIR + filename);
        if (imageUrl != null) {
            return new ImageIcon(imageUrl);
        }
        return null;
    }

    protected void fillNumber(int x, int y, int n) {
    	board.change(x, y, n); //may need to be adjusted to take care of the swapping in x and y values
    }
    
    protected int[] boardStatus() { //get an int[] representation of the no zero elements in the board
    	ArrayList<Integer> nonZeroSpaces = new ArrayList<Integer>();
    	int[] nonZeros = null;
    	for(int i=0;i<board.contents.size();i++) {
    		Square s = board.contents.get(i);
    		if(s.getValue() != 0) { //if the element is non zero, store x,y,value,set in the list
    			nonZeroSpaces.add(s.x);
    			nonZeroSpaces.add(s.y);
    			nonZeroSpaces.add(s.getValue());
    			if(s.set) nonZeroSpaces.add(1); //position is set
    			else nonZeroSpaces.add(0); //position is not set
    		}
    	}
    	nonZeros = new int[nonZeroSpaces.size()];
    	for(int i=0;i<nonZeros.length;i++) { //change list into array
    		nonZeros[i] = nonZeroSpaces.get(i);
    	}
    	return nonZeros;
    }
    
    public static void main(String[] args) {
        new SudokuDialog();
    }
}
