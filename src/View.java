import javax.imageio.ImageIO;
import javax.swing.*; 

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/* Job of class View:
 * initialize UI 
 * display chosen image after picking image by clicking button "Add Image"
 * display list of results by clicking "Search now!"
 * ask to update feedback by clicking "Reset search history"
 * View will communicate with Controller to connect with Model  
 */
public class View extends JFrame {
    // variables
	private JPanel controlBar;
	private JPanel searchResults;
    
    private JButton addImage;
    private JButton search;
    private JButton resetData;

    private String imgPath;
	private Image img;
	private BufferedImage buffered;
	private JLabel imgThumb;

	private final int width = 300;
    
    // constructor
    public View() {          
    	init();
		setTitle("CS3246 Assignment2");
		setSize(800, 600);
		setBackground(new Color(50, 50, 50));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);        
    }
    
    public void init() {
    	
    	//Initialize variables 
    	//controlBar = (JPanel)this.getContentPane();
    	controlBar = new JPanel();
    	searchResults = new JPanel();
    	
    	addImage = new JButton("Add Image");
    	search = new JButton("Search now!");
    	resetData = new JButton("Reset Search History");
    	
    	imgPath = "";
    	img = null;
    	buffered = null;
    	imgThumb = new JLabel();
    	
    	//Set bound of panel
        controlBar.setBounds(0, 200, 800, 100);
    	searchResults.setBackground(new Color(255, 255, 255));
        searchResults.setBounds(30, 250, 740, 300);
        
        //Set bound of buttons
        addImage.setBounds(50, 50, 10, 25);
        search.setBounds(150, 50, 10, 25);
        resetData.setBounds(250, 50, 10, 25);
        imgThumb.setBounds(250, 30, 300, 200);
        
        
    	//Adding components
    	controlBar.add(addImage);
        controlBar.add(search);
        controlBar.add(resetData);
        controlBar.setVisible(true);
        //searchResults.add(imgThumb);
        searchResults.setVisible(true);
        imgThumb.setVisible(true);
        add(imgThumb);
        add(searchResults);
        add(controlBar);

    	//Attaching image button
    	addImage.addActionListener(new ActionListener() {
			@SuppressWarnings("static-access")
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Please select a query image");
				int returnVal = fileChooser.showOpenDialog(View.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					imgPath = fileChooser.getSelectedFile().getAbsolutePath();
					try {
						img = ImageIO.read(new File(imgPath));
						buffered = ImageIO.read(new File(imgPath));
						img = img.getScaledInstance(width, -1, img.SCALE_DEFAULT);
					} catch (IOException e1) {}
					imgThumb.setIcon(new ImageIcon(img));
				}
			}
		});
    }
    
    // main class
    // only for testing view
    public static void main(String[] args) {
            View view = new View();
            view.setVisible(true);
    }
}
