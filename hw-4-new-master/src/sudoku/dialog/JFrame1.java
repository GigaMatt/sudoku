package sudoku.dialog;

import java.awt.event.KeyEvent;
import javax.swing.*;

public class JFrame1 {
	public static void main(String[] args) {

		JFrame jf = new JFrame();
		jf.setTitle("Sudoku");
		jf.setSize(500,500);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);
	    
	    JMenuBar jmb= new JMenuBar();
	    jf.setJMenuBar(jmb);
	    
	    
	    JMenu file = new JMenu("File");
	    file.setMnemonic(KeyEvent.VK_G);
	    file.getAccessibleContext().setAccessibleDescription("Game Menu");
	    jmb.add(file);
	    file.addSeparator();
		
		JMenuItem newGame = new JMenuItem("New Game");
		file.add(newGame);
		file.addSeparator();

		JMenuItem solve = new JMenuItem("Solve For Me");
		file.add(solve);
		file.addSeparator();
		
		JMenuItem isSolved = new JMenuItem("Is this solveable?");
		file.add(isSolved);
	}
}
