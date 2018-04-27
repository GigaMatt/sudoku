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
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.TitledBorder;

import HW5.NetworkAdapter.MessageType;
import edu.utep.cs.cs3331.sudoku2D.SudokuDialog;

@SuppressWarnings("serial")
public class Main extends SudokuDialog implements NetworkAdapter.MessageListener{
	private JButton networkButton;
	private ImageIcon NETWORK_OFF,NETWORK_ON;
	private NetworkAdapter network;
	private JTextField textField;
	
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
    	confirmation.setSize(250, 250);//maybe should make a little bigger
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
        //setup the text box, this is going to be where the socket output is placed, will be edited later
        textField = new JTextField();
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
