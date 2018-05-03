package HW5;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.net.InetSocketAddress;
import java.net.Socket;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.border.TitledBorder;

import HW5.NetworkAdapter.MessageType;
import edu.utep.cs.cs3331.sudoku2D.Board;
import edu.utep.cs.cs3331.sudoku2D.SudokuDialog;

@SuppressWarnings("serial")
public class Main extends SudokuDialog implements NetworkAdapter.MessageListener{
	private JButton networkButton;
	private ImageIcon NETWORK_OFF,NETWORK_ON;
	private NetworkAdapter network;
<<<<<<< HEAD:sudoku-5-REAL-MASTER/src/HW5/Main.java
	private Board board = super.board;
=======
	private JTextArea textField;
>>>>>>> a09336081e7bd6323f84ccfbde6635395cca4a1f:new-new-new-hw5/src/edu/utep/cs/cs3331/hw5/Main.java
	
	@Override
	protected JToolBar createToolBar() {
		JToolBar toolBar = super.createToolBar();
		toolBar.addSeparator();
		NETWORK_OFF = createImageIcon("redwifi.jpg");//replace w/red wifi button or something similar
		networkButton = new JButton(NETWORK_OFF);
		JDialog confirmation = createConnectionScreen();
		networkButton.addActionListener(e ->{
			networkButtonClicked(e);
			confirmation.setVisible(true);
		});
		networkButton.setToolTipText("Pair");
		networkButton.setFocusPainted(false);
		networkButton.setBorder(BorderFactory.createEmptyBorder());		
		toolBar.add(networkButton);
		return toolBar;
	}
	
	private void networkButtonClicked(ActionEvent e) {
		new Thread(() ->{
			try {
				Socket socket = new Socket();
				socket.connect(new InetSocketAddress("127.0.0.1",8000),5000);
				pairAsClient(socket);
			} catch(Exception e1) {}
		}).start();
	}

	private void pairAsClient(Socket socket) {
		System.out.println("pairing as client");
		NETWORK_ON = createImageIcon("");//replace w/green wifi button or something similar
		networkButton.setIcon(NETWORK_ON); //figure out how to make this change show later
		//super.repaint();
		network = new NetworkAdapter(socket);
		network.setMessageListener(this);
		network.writeJoin();
		network.receiveMessages(); //loop until disconnected
	}
	
	private JDialog createConnectionScreen() {
		JDialog confirmation = new JDialog();
		confirmation.setTitle("Connection");
		confirmation.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    	confirmation.setSize(500, 500);
    	
    	confirmation.setLayout(new BorderLayout());
    	//player section of connection panel
    	JPanel player = new JPanel(new BorderLayout());
    	TitledBorder border = new TitledBorder("Player settings");
        border.setTitleJustification(TitledBorder.CENTER);
        border.setTitlePosition(TitledBorder.TOP);
        player.setBorder(border);
        //peer section of connection panel
    	JPanel peer = new JPanel(new BorderLayout());
    	TitledBorder border1 = new TitledBorder("Peer settings");
        border1.setTitleJustification(TitledBorder.CENTER);
        border1.setTitlePosition(TitledBorder.TOP);
        peer.setBorder(border1);
        //setup the text box, this is going to be where the socket output should be placed, will be edited later
        textField = new JTextArea("Server started on port 8000.");
        textField.setPreferredSize(new Dimension(240,100));
    	textField.setEditable(false); //makes it so that the user can't edit the field but the program still can
    	textField.setBackground(Color.WHITE);
    	//add everything and return
    	confirmation.add(player,BorderLayout.NORTH);
    	confirmation.add(peer,BorderLayout.CENTER);
    	confirmation.add(textField,BorderLayout.SOUTH);
		return confirmation;
	}
	
	private void pairAsServer(Socket socket) {
		network = new NetworkAdapter(socket);
		network.setMessageListener(this);
		network.receiveMessages(); //loop until disconnected
	}
	
	protected void fillNumber(int x, int y, int n) {
		super.fillNumber(x,y,n);
		if(network != null) network.writeFill(x, y, n);
	}
	
	private boolean confirmPanel(String msg) {
		int response = JOptionPane.showConfirmDialog(null, msg);
		if(response == 0) return true;
		return false;
	}
	
	public Board createJoinBoard(int size, int[] boardNums) {
		
		Board b = new Board(size);
		
		for(int i = 0; i < boardNums.length; i+=4) {
			b.contents.get(boardNums[i]*b.size()+boardNums[i+1]).setValue(boardNums[i+2]);
			if(boardNums[i+3]==1) {
				b.contents.get(boardNums[i]*b.size()+boardNums[i+1]).set = true;
			}else {
				b.contents.get(boardNums[i]*b.size()+boardNums[i+1]).set = false;
			}
		}
		
		return b;
		
	}
	
	public void messageReceived(MessageType type, int x, int y, int n, int[] others) {
		switch (type) {
		case FILL:
			fillNumber(x,y,n);
			break;
		case CLOSE:
			network.close();
			break;
		case FILL_ACK:
			network.writeFillAck(x, y, n);
			break;
		case JOIN:
<<<<<<< HEAD:sudoku-5-REAL-MASTER/src/HW5/Main.java
			if(confirmPanel("Accept a sharing request from (enter IP here?)?")) System.out.println(network.writeJoin());
=======
			
>>>>>>> a09336081e7bd6323f84ccfbde6635395cca4a1f:new-new-new-hw5/src/edu/utep/cs/cs3331/hw5/Main.java
			break;
		case JOIN_ACK:
			//make panel thing
			super.board = createJoinBoard(x, others);
			network.writeJoinAck(super.board.size(), super.boardStatus());
			break;
		case NEW:
			network.writeNew(super.board.size(), super.boardStatus());
			break;
		case NEW_ACK:
			network.writeNewAck(confirmPanel("Accept a new game request?"));
			break;
		case QUIT:
			if(confirmPanel("Do you really want to disconnet?")) network.close();
			break;
		case UNKNOWN:
			network.close();
			break;
		default:
			network.close();
			break;
		}
	}
	
	public static void main(String[] args) {
		new Main();
	}
}
