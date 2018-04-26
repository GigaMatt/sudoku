package HW5;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.net.InetSocketAddress;
import java.net.Socket;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import HW5.NetworkAdapter.MessageType;
import edu.utep.cs.cs3331.sudoku2D.SudokuDialog;

@SuppressWarnings("serial")
public class Main extends SudokuDialog implements NetworkAdapter.MessageListener{
	private JButton networkButton;
	private ImageIcon NETWORK_OFF,NETWORK_ON;
	private NetworkAdapter network;
	
	@Override
	protected JToolBar createToolBar() {
		JToolBar toolBar = super.createToolBar();
		toolBar.addSeparator();
		NETWORK_OFF = createImageIcon("plusButton.png");//replace w/red wifi button or something similar
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
		NETWORK_ON = createImageIcon("");//replace w/green wifi button or something similar
		networkButton.setIcon(NETWORK_ON);
		network = new NetworkAdapter(socket);
		network.setMessageListener(this);
		network.writeJoin();
		network.receiveMessages(); //loop until disconnected
	}
	
	private JDialog createConnectionScreen() {
		JDialog confirmation = new JDialog();
		confirmation.setTitle("Connection");
		confirmation.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    	confirmation.setSize(250, 250);
    	confirmation.setLayout(new BorderLayout());
    	JDialog player = new JDialog(); //put in north section of confirmation
    	player.setLayout(new BorderLayout());
    	player.setTitle("Player settings");
    	JDialog peer = new JDialog();//put in center section of confirmation
    	peer.setLayout(new BorderLayout());
    	peer.setTitle("Peer settings");
		//code here to look like cheon's connection confirmation panel
    	//figure out what/how to put the text box in the south section of confirmation
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
	
	public void messageReceived(MessageType type, int x, int y, int n, int[] others) {
		switch (type) {
		case FILL:
			super.fillNumber(x,y,n);
			break;
		case CLOSE:
			
			break;
		case FILL_ACK:
			
			break;
		case JOIN:
			break;
		case JOIN_ACK:
			
			break;
		case NEW:
			
			break;
		case NEW_ACK:
			
			break;
		case QUIT:
			
			break;
		case UNKNOWN:
			
			break;
		default:
			
			break;
		}
	}
	
	public static void main(String[] args) {
		new Main();
	}
}
