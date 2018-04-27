package HW5;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.net.Socket;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/** 
 * Test the {@link NetworkAdapter} class by focusing on
 * its public methods: <code>writeXXX</code> for sending messages
 * and <code>setMessageListener(MessageListener)</code> for notifying
 * received messages.
 */
public class NetworkAdapterTest {

    private NetworkAdapter network;
    
    /** Mockup socket to observe messages being sent and received. */
    private FakeSoket socket;
    
    private boolean messageReceived = false;
    
    @Before
    public void setUp() throws Exception {
        socket = new FakeSoket();
        network = new NetworkAdapter(socket);
        messageReceived = false;
    }

    @After
    public void tearDown() throws Exception {
        socket.close();
        network.close();
    }

    @Test
    public void testWriteJoin() {
        network.writeJoin();
        checkMessageDelivery("join:");
    }
    
    @Test
    public void testWriteJoinAck1() {
        network.writeJoinAck(); // declined
        checkMessageDelivery("join_ack:0");
    }
    
    @Test
    public void testWriteJoinAck2() {
        network.writeJoinAck(4); // accepted: size of board
        checkMessageDelivery("join_ack:1,4");
    }
    
    @Test
    public void testWriteJoinAck3() {
        network.writeJoinAck(9, 0, 0, 2, 1); // accepted: size,x,y,v,f
        checkMessageDelivery("join_ack:1,9,0,0,2,1");
    }
    
    @Test
    public void testWriteJoinAck4() {
        network.writeJoinAck(4, 0, 0, 2, 1, 1, 1, 3, 0); // accepted: size,x,y,v,f,x,y,z,f,...
        checkMessageDelivery("join_ack:1,4,0,0,2,1,1,1,3,0");
    }
    
    @Test
    public void testWriteNew1() {
        network.writeNew(9); // size
        checkMessageDelivery("new:9");
    }
    
    @Test
    public void testWriteNew2() {
        network.writeNew(9, 0, 0, 2, 1); // size,x,y,v,f
        checkMessageDelivery("new:9,0,0,2,1");
    }
    
    @Test
    public void testWriteNew3() {
        network.writeNew(9, 0, 0, 2, 1, 1, 1, 3, 0); // size,x,y,v,f,x,y,z,f,...
        checkMessageDelivery("new:9,0,0,2,1,1,1,3,0");
    }
    
    @Test
    public void testWriteNewAck1() {
        network.writeNewAck(true);
        checkMessageDelivery("new_ack:1");
    }
    
    @Test
    public void testWriteNewAck2() {
        network.writeNewAck(false);
        checkMessageDelivery("new_ack:0");
    }
    
    @Test
    public void testWriteFill() {
        network.writeFill(1, 2, 3);
        checkMessageDelivery("fill:1,2,3"); // x,y,v
    }

    @Test
    public void testWriteFillAck() {
        network.writeFillAck(1, 2, 3);
        checkMessageDelivery("fill_ack:1,2,3"); // x,y,v
    }
    
    @Test
    public void testWriteQuit() {
        network.writeQuit();
        checkMessageDelivery("quit:");
    }
    
    @Test
    public void testNotifyMessages1() {
        checkMessageNotification("join:", NetworkAdapter.MessageType.JOIN);
    }
    
    @Test
    public void testNotifyMessages2() {
        checkMessageNotification("join_ack:0", 
                NetworkAdapter.MessageType.JOIN_ACK, 0);
        checkMessageNotification("join_ack:1,9,1,2,3", 
                NetworkAdapter.MessageType.JOIN_ACK, 1, 9, new int[] {1,2,3});        
        checkMessageNotification("join_ack:1,6,1,2,3,4,5,6", 
                NetworkAdapter.MessageType.JOIN_ACK, 1, 6, new int[] {1,2,3,4,5,6});
    }
    
    @Test
    public void testNotifyMessages3() {
        checkMessageNotification("new_ack:0", NetworkAdapter.MessageType.NEW_ACK,0);
        checkMessageNotification("new_ack:1", NetworkAdapter.MessageType.NEW_ACK,1);
    }
    
    @Test
    public void testNotifyMessages4() {
        checkMessageNotification("new:9", NetworkAdapter.MessageType.NEW, 9);
        checkMessageNotification("new:4,1,2,3,0", 
                NetworkAdapter.MessageType.NEW, 4, new int[] {1,2,3,0});        
        checkMessageNotification("join_ack:9,6,1,2,0,4,5,6,0", 
                NetworkAdapter.MessageType.NEW, 9, new int[] {6,1,2,0,4,5,6,0});
    }
    
    @Test
    public void testNotifyMessages5() {
        checkMessageNotification("fill:3,2,5", NetworkAdapter.MessageType.FILL, 3, 2, 5);
    }
    
    @Test
    public void testNotifyMessages6() {
        checkMessageNotification("fill_ack:1,2,8", 
                NetworkAdapter.MessageType.FILL_ACK, 1, 2, 8);
    }
    
    @Test
    public void testNotifyMessages7() {
        checkMessageNotification("quit:", 
                NetworkAdapter.MessageType.QUIT);
    }
    
    @Test
    public void testNotifyMessages8() {
        checkMessageNotification("what?:", 
                NetworkAdapter.MessageType.UNKNOWN);
    }
    
    /** Check message receipt synchronously. Does the given message trigger
     * the specified notification (message type)? */
    private void checkMessageNotification(String msg, 
            NetworkAdapter.MessageType type) {
        checkMessageNotification(msg, type, 0, 0, 0);
    }
    
    /** Check message notifcation synchronously. Does the given message trigger
     * the specified notification (message type and content) ? */
    private void checkMessageNotification(String msg, 
            NetworkAdapter.MessageType type, int x) {
        network.setMessageListener((receivedType, receivedX, y, z, others) -> {
            messageReceived = true;
            if (receivedType != NetworkAdapter.MessageType.CLOSE) {
                assertEquals(type, receivedType);
                assertEquals(x, receivedX);
            }
        });
        socket.receiveMessage(msg); // simulate message
        network.receiveMessages(); // run on the calling (JUnit) thread
        assertTrue(messageReceived);
    }
    
    /** Check message notification synchronously. */
    private void checkMessageNotification(String msg, 
            NetworkAdapter.MessageType type, int x, int y, int v) {
        network.setMessageListener((receivedType, receivedX, receivedY, receivedV, others) -> {
            messageReceived = true;
            if (receivedType != NetworkAdapter.MessageType.CLOSE) {
                assertEquals(type, receivedType);
                assertEquals(x, receivedX);
                assertEquals(y, receivedY);
                assertEquals(v, receivedV);
            }
        });
        socket.receiveMessage(msg); // simulate message
        network.receiveMessages(); // run on the calling (JUnit) thread
        assertTrue(messageReceived);
    }
    
    /** Check message notifcation synchronously. */
    private void checkMessageNotification(String msg, 
            NetworkAdapter.MessageType type, int x, int[] others) {
        network.setMessageListener((receivedType, receivedX, receivedY, receivedZ, receivedOthers) -> {
            messageReceived = true;
            if (receivedType != NetworkAdapter.MessageType.CLOSE) {
                assertEquals(type, receivedType);
                assertEquals(x, receivedX);
                assertArrayEquals(others, receivedOthers);
            }
        });
        socket.receiveMessage(msg); // simulate message
        network.receiveMessages(); // run on the calling (JUnit) thread
        assertTrue(messageReceived);
    }
    
    /** Check message notification synchronously. */
    private void checkMessageNotification(String msg, 
            NetworkAdapter.MessageType type, int x, int y, int[] others) {
        network.setMessageListener((receivedType, receivedX, receivedY, receivedZ, receivedOthers) -> {
            messageReceived = true;
            if (receivedType != NetworkAdapter.MessageType.CLOSE) {
                assertEquals(type, receivedType);
                assertEquals(x, receivedX);
                assertEquals(y, receivedY);
                assertArrayEquals(others, receivedOthers);
            }
        });
        socket.receiveMessage(msg); // simulate message
        network.receiveMessages(); // run on the calling (JUnit) thread
        assertTrue(messageReceived);
    }
    
    /** Check that the given (sent) message is delivered. */
    private void checkMessageDelivery(String msg) {
        sleep(1); // coz of asynchronous sending
        assertEquals(addEOL(msg), socket.writtenMessages());        
    }
    
    private String addEOL(String text) {
        return text + System.lineSeparator();
    }
    
    private void sleep(int secs) {
        try {
            Thread.sleep(secs * 1000);
        } catch (InterruptedException e) {
        }        
    }
    
    /** Provide custom input/output streams to observe the contents. */
    private static class FakeSoket extends Socket {
        
        private InputStream input;
        private PipedOutputStream pipe;
        
        private OutputStream output;
        
        public FakeSoket() {
            output = new ByteArrayOutputStream();
            input = new PipedInputStream();
            pipe = new PipedOutputStream();
            try {
                ((PipedInputStream) input).connect(pipe);
            } catch (IOException e) {
            }
        }
        
        /** Overridden here to read messages from a pipe. */
        @Override
        public InputStream getInputStream() {
            return input;
        }
        
        /** Overridden here to write messages to a byte array
         * whose content can be read by calling 
         * the <code>writtenMessages()</code> method. */
        @Override
        public OutputStream getOutputStream() {
            return output;
        } 
        
        /** Return the messages written to this socket. */
        public String writtenMessages() {    
            return output.toString();
        }
        
        /** Simulate receiving of a message. Write the given message 
         * to the pipe connected to the input stream of this socket. */
        public void receiveMessage(String msg) {
            try {
                pipe.write((msg + System.lineSeparator()).getBytes());
                pipe.flush();
                pipe.close();
            } catch (IOException e) {
            }
        }

        @Override
        public void close() {
            try {
                super.close();
                input.close();
                output.close();
                pipe.close();
            } catch (IOException e) {
            }
        }
    }
}
