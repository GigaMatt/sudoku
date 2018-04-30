package HW5;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.InetAddress;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
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
	private JTextArea textField;
	private JTextField host,port;
	private JButton connect,disconnect;
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	private Thread curr;
	
	@Override
	protected JToolBar createToolBar() {
		JToolBar toolBar = super.createToolBar();
		toolBar.addSeparator();
		NETWORK_OFF = createImageIcon("redwifi.jpg");
		networkButton = new JButton(NETWORK_OFF);
		JDialog confirmation = createConnectionScreen();
		networkButton.addActionListener(e ->{
			//networkButtonClicked(e);
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
		NETWORK_ON = createImageIcon("greenwifi.png");
		networkButton.setIcon(NETWORK_ON);
		repaint();
		//super.repaint();
		network = new NetworkAdapter(this.socket);
		network.setMessageListener(this);
		network.writeJoin();
		network.receiveMessages();
	}
	
	private JDialog createConnectionScreen() {
		JDialog confirmation = new JDialog();
		JPanel options = new JPanel(new BorderLayout());
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
        String hostname = "Local host";
        String ipAddress = "127.0.0.1";
        try {
        	InetAddress addr = InetAddress.getLocalHost();
            hostname = addr.getHostName();
            ipAddress = addr.getHostAddress();
        }catch(Exception e) {}
        hostname = (String) System.getProperty("user.name");
        hostNameField.setText(hostname);
        hostNameField.setEditable(false);
        JLabel ipNumber = new JLabel("IP number");
        JTextField nameField = new JTextField(20);
        nameField.setText(ipAddress);
        nameField.setEditable(false);
        JLabel portNumber = new JLabel("Port number");
        JTextField portField = new JTextField(20);
        portField.setText("8000");
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
        textField = new JTextArea("Server started on port 8000.");
        textField.setPreferredSize(new Dimension(240,100));
    	textField.setEditable(false); //makes it so that the user can't edit the field but the program still can
    	textField.setBackground(Color.WHITE);
    	//add everything and return
    	options.add(player,BorderLayout.NORTH);
    	options.add(peer,BorderLayout.SOUTH);
    	confirmation.add(options,BorderLayout.CENTER);
    	confirmation.add(textField,BorderLayout.SOUTH);
		return confirmation;
	}
	
	private void connectClicked(ActionEvent e) {
		curr = new Thread(() -> {
			disconnect.setEnabled(true);
			connect.setEnabled(false);
			String hostIP = host.getText();
			String portNum = port.getText();
			try{
				socket = new Socket(hostIP,Integer.parseInt(portNum));
    			try{
	    			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    			out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
	    		} catch(Exception e2) {}
	    		pairAsClient(socket);
	    	}catch(Exception e1) {}    
		});
		curr.start();
	}
	
	private void disconnectClicked(ActionEvent e) {
		network.writeQuit();
		curr.interrupt();
		curr = null;
		connect.setEnabled(true);
    	disconnect.setEnabled(false);
    	networkButton.setIcon(NETWORK_OFF);
		repaint();
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
	
	public void messageReceived(MessageType type, int x, int y, int n, int[] others) {
		switch (type) {
		case FILL:
			super.fillNumber(x,y,n);
			break;
		case CLOSE:
			network.close();
			break;
		case FILL_ACK:
			network.writeFillAck(x, y, n);
			break;
		case JOIN:
			if(confirmPanel("Accept a sharing request from (enter IP here?)?")) network.writeJoin();
			break;
		case JOIN_ACK:
			//make panel thing
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
