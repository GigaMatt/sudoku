package sudoku.dialog;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class JFrame1 {
	public static void main(String[] args) {
		JFrame jf = new JFrame();
		jf.setTitle("Sudoku");
		jf.setSize(500,500);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);
		
		JMenuBar jmbar = new JMenuBar();
		jf.setJMenuBar(jmbar);
		
		JMenu file = new JMenu("File");	//create File menu named "File"
		jmbar.add(file);
		file.addSeparator();	//line separator in the menu bar
		
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
