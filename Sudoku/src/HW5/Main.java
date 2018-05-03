/**
 * CS3331
 * @version 5.0 (05/02/2018)
 * 
 * @author Anthony Ayo 
 * @author Anthony Moran
 * @author Enrique Salcido
 * @author Matthew Montoya
 **/
package HW5;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetAddress;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.border.TitledBorder;

import HW5.NetworkAdapter.MessageType;
import edu.utep.cs.cs3331.sudoku2D.Board;
import edu.utep.cs.cs3331.sudoku2D.Move;
import edu.utep.cs.cs3331.sudoku2D.Square;
import edu.utep.cs.cs3331.sudoku2D.SudokuDialog;

@SuppressWarnings("serial")
public class Main extends SudokuDialog implements NetworkAdapter.MessageListener{
	private JButton networkButton;
	private ImageIcon NETWORK_OFF,NETWORK_ON;
	private NetworkAdapter network;
	private JTextArea textField; //TODO edit in hard coded messages to display when we send/receive messages from other player
	private JTextField host,port;
	private JButton connect,disconnect;
	private Socket socket;
	private Thread curr;
	private String hostname="",ipAddress="";
	private int portnum = 8000;
	private ServerSocket server;
	private JTextField portField;
	private String clientIP ="";
	private boolean weChanged = false;

	/**
	 * Creates the tool bar
	 * @return the board's toolbar
	 */
	@Override
	protected JToolBar createToolBar() {//TODO add in to action listeners the appropriate write messages for other player
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
		NETWORK_OFF = createImageIcon("redwifi.jpg");
		networkButton = new JButton(NETWORK_OFF);
		setHostSettings();
		JDialog confirmation2 = createConnectionScreen();
		networkButton.addActionListener(e ->{
			confirmation2.setVisible(true);
			new Thread(() -> {
				networkButtonClicked(e);
			}).start();
		});
		networkButton.setToolTipText("Pair");
		networkButton.setFocusPainted(false);
		networkButton.setBorder(BorderFactory.createEmptyBorder());		
		tb.add(networkButton);
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			public void windowClosed(WindowEvent e) {
				System.exit(0);
			}
		});
		return tb;
	}

	/**
	 * Makes the confirmation panel
	 * @return a JDialog message confirmation panel
	 */
	protected JDialog makeConfirmationPanel() { //TODO make edit to play button to write new game message to other player
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

	/**
	 * Configures network settings for the host
	 */
	private void setHostSettings() {
		hostname = "Local host";
		ipAddress = "127.0.0.1";
		try {
			InetAddress addr = InetAddress.getLocalHost();
			hostname = System.getenv("COMPUTERNAME");
			ipAddress = addr.getHostAddress();
		}catch(Exception e) {}
	}

	/**
	 * Network configuration for when the GUI network button is clicked
	 * @param e ActionEvent for incoming socker
	 */
	private void networkButtonClicked(ActionEvent e) {
		portnum = 8000;
		try {
			while(true) {
				try {
					server = new ServerSocket(portnum);
					System.out.println("Server started on " + portnum);
					portField.setText(Integer.toString(portnum));
					textField.append("Server started on port " + portnum + ".\n");
					break;
				}catch(Exception e2) {portnum++;}
			}
			while(true) {
				Socket incoming = server.accept();
				new Thread(() ->{ //network adapter methods to close when we're the server
					pairAsServer(incoming);
				}).start();
			}
		} catch(Exception e1) {}
	}

	/**
	 * Pairs as the client
	 * @param socket sets the network adapter using this socket
	 */
	private void pairAsClient(Socket socket) {
		System.out.println("pairing as client");
		NETWORK_ON = createImageIcon("greenwifi.png");
		networkButton.setIcon(NETWORK_ON);
		repaint();
		network = new NetworkAdapter(this.socket);
		network.setMessageListener(this);
		network.writeJoin();
		network.receiveMessages();
	}

	/**
	 * Displays information regarding the game's network connection
	 * @return JDialog message regarding the network connection status
	 */
	private JDialog createConnectionScreen() {
		JDialog confirmation = new JDialog();
		confirmation.setResizable(false);
		JPanel options = new JPanel(new BorderLayout());
		confirmation.addWindowListener(new WindowAdapter() { //so the server gets closed when we exit the network settings
			public void windowClosed(WindowEvent e) {
				try{
					server.close();
					networkButton.setIcon(NETWORK_OFF);
					repaint();
					System.out.println("Server on port " + portnum + " was closed");
				}catch(Exception e1) {}
			}
		});
		confirmation.setTitle("Connection");
		confirmation.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		confirmation.setSize(350, 375);
		confirmation.setLayout(new BorderLayout());
		//player section of connection panel
		JPanel player = new JPanel(new BorderLayout());
		player.setBounds(0,0, 350, 200);
		TitledBorder border = new TitledBorder("Player settings");
		border.setTitleJustification(TitledBorder.CENTER);
		border.setTitlePosition(TitledBorder.TOP);
		JLabel hostName = new JLabel("Host Name");
		JTextField hostNameField = new JTextField(20);
		hostNameField.setText(hostname);
		hostNameField.setEditable(false);
		JLabel ipNumber = new JLabel("IP number");
		JTextField nameField = new JTextField(20);
		nameField.setText(ipAddress);
		nameField.setEditable(false);
		JLabel portNumber = new JLabel("Port number");
		portField = new JTextField(20);
		portField.setText(Integer.toString(portnum));
		portField.setEditable(false);
		//create individual panels for the name/text field combinations
		JPanel hostFields = new JPanel(new FlowLayout());
		hostFields.add(hostName);
		hostFields.add(hostNameField);
		JPanel ipFields = new JPanel(new FlowLayout());
		ipFields.add(ipNumber);
		ipFields.add(nameField);
		JPanel portFields = new JPanel(new FlowLayout());
		portFields.add(portNumber);
		portFields.add(portField);
		player.add(hostFields,BorderLayout.NORTH);
		player.add(ipFields,BorderLayout.CENTER);
		player.add(portFields,BorderLayout.SOUTH);
		player.setBorder(border);
		//peer section of connection panel
		JPanel peer = new JPanel(new BorderLayout());
		peer.setBounds(0, 201, 350, 200);
		TitledBorder border1 = new TitledBorder("Peer settings");
		border1.setTitleJustification(TitledBorder.CENTER);
		border1.setTitlePosition(TitledBorder.TOP);
		peer.setBorder(border1);
		JLabel hostIP = new JLabel("Host Name/IP");
		host = new JTextField(20);
		JLabel portNum = new JLabel("Port number");
		port = new JTextField(20);
		connect = new JButton("Connect");
		disconnect = new JButton("Disconnect");
		connect.addActionListener(this::connectClicked);
		disconnect.setEnabled(false);
		disconnect.addActionListener(this::disconnectClicked);
		JPanel hostSettings = new JPanel(new FlowLayout());
		hostSettings.add(hostIP);
		hostSettings.add(host);
		JPanel portSettings = new JPanel(new FlowLayout());
		portSettings.add(portNum);
		portSettings.add(port);
		JPanel buttons = new JPanel(new FlowLayout());
		buttons.add(connect);
		buttons.add(disconnect);
		peer.add(buttons,BorderLayout.SOUTH);
		peer.add(portSettings,BorderLayout.CENTER);
		peer.add(hostSettings,BorderLayout.NORTH);
		//setup the text box, this is going to be where the socket output should be placed, will be edited later
		textField = new JTextArea();
		textField.setPreferredSize(new Dimension(240,100));
		textField.setEditable(false); //makes it so that the user can't edit the field but the program still can
		textField.setBackground(Color.WHITE);
		//textField.append("Test string appending");
		//add everything and return
		options.add(player,BorderLayout.NORTH);
		options.add(peer,BorderLayout.SOUTH);
		confirmation.add(options,BorderLayout.CENTER);
		confirmation.add(textField,BorderLayout.SOUTH);
		return confirmation;
	}

	/**
	 * Configuration for when the GUI connection button is clicked
	 * @param e ActionEvent for pairing the socket
	 */
	private void connectClicked(ActionEvent e) {
		curr = new Thread(() -> {
			String hostIP = host.getText();
			String portNum = port.getText();
			try{
				socket = new Socket(hostIP,Integer.parseInt(portNum));
				disconnect.setEnabled(true);
				connect.setEnabled(false);
				pairAsClient(socket);
			}catch(Exception e1) {}    
		});
		curr.start();
	}

	/**
	 * Terminates the network connection
	 * @param e ActionEvent
	 */
	private void disconnectClicked(ActionEvent e) {
		if(confirmPanel("Do you really want to disconnect?")) {
			network.writeQuit();
			try{
				curr.interrupt();
				curr = null;
			} catch(Exception e1) {}
			connect.setEnabled(true);
			disconnect.setEnabled(false);
			networkButton.setIcon(NETWORK_OFF);
			repaint();
		}
	}

	/**
	 * Pairs as the server
	 * @param socket sets the network adapter using this socket
	 */
	private void pairAsServer(Socket socket) {
		clientIP = socket.getLocalAddress().getHostAddress();
		System.out.println("pairing as server");
		NETWORK_ON = createImageIcon("greenwifi.png");
		networkButton.setIcon(NETWORK_ON);
		repaint();
		network = new NetworkAdapter(socket);
		network.setMessageListener(this);
		disconnect.setEnabled(true);
		connect.setEnabled(false);
		network.receiveMessages(); //loop until disconnected
	}

	/**
	 * @param number the number selection from the user
	 */
	@Override
	protected void numberClicked(int number) { //works great!
		if(clickedBoard) {
			if(board.change(y, x, number)) { //if the board was clicked and there was a change
				undos.push(new Move(y,x,number)); //add move to undo stack (the undo and redo parts can be omitted)
				solver.b = board; //update solver's board
				showMessage("Added " + number + " to location (" + (y+1) + "," + (x+1) + ")");
				boardPanel.repaint();
				weChanged = true;
				network.writeFill(x,y,number);
				checkSolved(); //check if the board is now solved
			} else showMessage("Conflict putting " + number + " in location (" + (y+1) + "," + (x+1) + ")");
		}
	}

	/**
	 * Fills a number in a specific x,y position
	 * @param x the x-coordinate which was selected
	 * @param y the y-coordinate which was selected
	 */
	protected void fillNumber(int x, int y, int n) {
		super.fillNumber(x,y,n);
		if(network != null) network.writeFill(x, y, n);
		boardPanel.repaint();
	}

	/**
	 * 
	 * @param msg the message to be displayed
	 * @return true/false regarding response value
	 */
	private boolean confirmPanel(String msg) {
		int response = JOptionPane.showConfirmDialog(null, msg);
		if(response == 0) return true;
		return false;
	}

	/**
	 * Retrieves contents of P2P board
	 * @param size the size of the board
	 * @param boardNums the values of the board squares
	 * @return the board with the new changes
	 */
	public Board createJoinBoard(int size, int[] boardNums) {
		Board b = new Board(size);
		for(int i = 0; i < boardNums.length; i = i + 4) {
			//getting the value from the array
			b.contents.get(boardNums[i+1]*b.size()+boardNums[i]).setValue(boardNums[i+2]);
			//setting the set variable
			if(boardNums[i+3]==1) {
				b.contents.get(boardNums[i]*b.size()+boardNums[i+1]).set = true;
			}else {
				b.contents.get(boardNums[i]*b.size()+boardNums[i+1]).set = false;
			}
		}
		return b;
	}

	/**
	 * Cases for message declaration
	 * @param type the type of Message that was received
	 * @param x x-coordinate
	 * @param y y-coordinate
	 * @param n x-y coordinate values
	 * @param others the other integers
	 */
	public void messageReceived(MessageType type, int x, int y, int n, int[] others) {
		//TODO make additional case for when the solve button is used
		switch (type) {
		case FILL:
			network.writeFillAck(x, y, n);
			super.fillNumber(y, x, n);
			boardPanel.repaint();
			break;
		case CLOSE: //when they disconnect from us
			network.close();
			connect.setEnabled(true);
			disconnect.setEnabled(false);
			networkButton.setIcon(NETWORK_OFF);
			repaint();
			break;
		case FILL_ACK:
			network.writeFillAck(x, y, n);
			if(!weChanged) fillNumber(x,y,n);
			weChanged = false;
			//repaint();
			break;
		case JOIN:
			if(confirmPanel("Accept a sharing request from (" + clientIP + ")?")) 
				network.writeJoinAck(super.board.size(), super.boardStatus());
			repaint();
			break;
		case JOIN_ACK:
			board = createJoinBoard(y, others);
			boardPanel.setBoard(board);
			solver.b = board;
			lastCheckedSol = solver.solve(board.clone());
			super.repaint();
			repaint();
			break;
		case NEW: //TODO getting the board, the new board is sent along with the new request and is displayed the same
			//way as it would be during the join case
			network.writeNewAck(confirmPanel("Accept a new game request?"));
			break;
		case NEW_ACK:
			if(x==0) {
				network.close();
				try{
					network.socket().close();
				}catch(Exception e) {}
				connect.setEnabled(true);
				disconnect.setEnabled(false);
				networkButton.setIcon(NETWORK_OFF);
				repaint();
			}
			break;
		case QUIT:
			network.close();
			try{
				network.socket().close();
			}catch(Exception e) {}
			break;
		case UNKNOWN:
			network.close();
			break;
		default:
			network.close();
			break;
		}
	}
	
	/**
	 * Configures the UI for the entire board which is displayed to the user	
	 */
	@Override
	protected void configureUI(){
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


		//JMenuItem SOLVE_PUZZLE,TODO add new enumeration type for solve and new solve message display
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
		fileMenu.addSeparator(); //TODO also add in sending the quit message to other player
		exit.addActionListener(e->{System.exit(0);});
	}    

	public static void main(String[] args) {
		new Main();
	}
}
