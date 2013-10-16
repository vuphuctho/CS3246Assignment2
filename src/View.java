import javax.swing.*; 
import java.awt.*;

public class View extends JFrame {
	// variables
	public JPanel controlBar = new JPanel();
	
	public JButton addImage = new JButton("Add Image");
	public JButton search = new JButton("Search now!");
	public JButton resetData = new JButton("Reset search history");
	
	// constructor
	public View() {
		// set bound of panel
		controlBar.setBounds(0, 300, 0, 0);
		
		// set bound of buttons
		addImage.setBounds(50, 50, 10, 25);
		search.setBounds(150, 50, 10, 25);
		resetData.setBounds(250, 50, 10, 25);
		
		setTitle("CS3246 Assignment2");
		setSize(600, 400);
		setBackground(new Color(53, 56, 64));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		controlBar.add(addImage);
		controlBar.add(search);
		controlBar.add(resetData);
		add(controlBar);
	}
	
	// main class
	// only for testing view
	public static void main(String[] args) {
		View view = new View();
		view.setVisible(true);
	}
}
