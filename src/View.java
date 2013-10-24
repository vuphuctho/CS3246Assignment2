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
	private JPanel contentPane;
	private JPanel contentPane2;
	
	private int width = 300; // the size for each image result
	private int height = 300;
	
	

	private JButton addImage;
	private JButton search;
	private JButton resetData;
	
	private JLabel imgThumb;
	private JPanel searchResults;
	private JScrollPane searchScroll;

	private String imgPath;
	private Image img;
	private BufferedImage buffered;
	
	// constructor
	public View() {
		addImage = new JButton("Choose image");
		search = new JButton("Search now!");
		resetData = new JButton("Reset search history");
		
		imgThumb = new JLabel();
		searchResults = new JPanel();
		searchScroll = new JScrollPane(searchResults); 
		//, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER

		//distLabel.setFont(new Font("Serif", Font.BOLD, 20));
		
		//hist1 = new Histogram();
		//hist2 = new Histogram();
		//contentPane = (JPanel)this.getContentPane();
		contentPane = new JPanel();
		contentPane2 = (JPanel)this.getContentPane();
		
		setTitle("CS3246 Assignment 2");
		setBackground(new Color(50, 50, 50));
		setSize(800,600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		init();
		
		
	}
	
	public void init() {
		// set bound of panel
		//controlBar.setBounds(0, 300, 0, 0);
		
		// set bound of buttons
		//addImage.setBounds(50, 50, 10, 25);
		//search.setBounds(150, 50, 10, 25);
		//resetData.setBounds(250, 50, 10, 25);
		
		/*TODO: change layout to fit the 3 buttons in 1 row
		 * 
		 * 
		 * 
		 * 
		 * 
		 */
		/*GridLayout gridLayout = new GridLayout(3,4);  // grid layout 1 * 2
		//gridLayout.setColumns(3); 
		//gridLayout.setRows(4); 
		contentPane.setLayout(gridLayout);
		
		contentPane.add(addImage);
		contentPane.add(search);
		contentPane.add(resetData);
		contentPane.add(new JLabel("1"));
		contentPane.add(imgThumb);

		contentPane.add(new JLabel("2"));
		contentPane.add(new JLabel("3"));
		contentPane.add(new JLabel("4"));
		contentPane.add(new JLabel("5"));
		contentPane.add(new JLabel("6"));
		contentPane.add(new JLabel("7"));
		contentPane.add(new JLabel("8"));*/
		
		GridLayout gridLayout = new GridLayout();
		gridLayout.setColumns(2);
		searchResults.setLayout(gridLayout);
		for (int i=0;i<122;i++) {
			searchResults.add(new JLabel("Hi!"));
			searchResults.add(new JLabel("There\n"));			
		}
		contentPane.add(searchScroll);
		
		contentPane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;	
		
		//TOP BAR
        c.gridwidth = 2;
        c.gridheight = 1;
	    c.gridy = 0;
	    
        c.anchor = GridBagConstraints.NORTHEAST;
        c.gridx = 0;
	    contentPane.add(addImage, c);

	    c.anchor = GridBagConstraints.NORTH;
        c.gridx = 2;
	    contentPane.add(new JButton("Placeholder"), c);
	    
	    c.anchor = GridBagConstraints.NORTHWEST;
        c.gridx = 4;
	    contentPane.add(new JButton("Placeholder"), c);
		
		//SEARCH BUTTON
        c.gridwidth = 6;
        c.gridheight = 2;
	    c.gridy = 1;
        c.gridx = 0;
	    c.anchor = GridBagConstraints.NORTHEAST;
	    contentPane.add(new JButton("Placeholder"), c);
		
	    //IMAGE THUMBNAIL 
	    c.gridwidth = 2;
        c.gridheight = 6;
	    c.gridy = 3;
        c.gridx = 0;
	    c.anchor = GridBagConstraints.NORTHEAST;
	    contentPane.add(imgThumb, c);

	    //RESULTS PANE 
	    c.gridwidth = 4;
        c.gridheight = 6;
	    c.gridy = 3;
        c.gridx = 2;
	    c.anchor = GridBagConstraints.NORTHWEST;
	    contentPane.add(searchScroll, c);
	    
	    GridLayout gridLayout1 = new GridLayout();  // grid layout 1 * 2
		gridLayout.setColumns(2); 
		gridLayout.setRows(4); 
		contentPane2.setLayout(gridLayout1);
		contentPane2.add(new JButton("Placeholder"));
		contentPane2.add(new JButton("Placeholder"));
		contentPane2.add(new JButton("Placeholder"));
		contentPane2.add(new JButton("Placeholder"));
		contentPane2.add(new JButton("Placeholder"));
		contentPane2.add(new JButton("Placeholder"));
		contentPane2.add(new JButton("Placeholder"));
		contentPane2.add(new JButton("Placeholder"));
		contentPane2.setVisible(true);
		setVisible(true);
		repaint();
		
	    GridLayout gridLayout2 = new GridLayout();
	    gridLayout2.setColumns(3);
	    
	    
	    contentPane2.setLayout(gridLayout2);
	    contentPane2.add(new JButton("Placeholder"));
	    contentPane2.add(new JButton("Placeholder"));
	    contentPane2.add(new JButton("Placeholder"));
	    contentPane2.add(new JButton("Placeholder"));
	    contentPane2.add(new JButton("Placeholder"));
	    contentPane2.add(new JButton("Placeholder"));
	    contentPane2.add(new JButton("Placeholder"));
		contentPane2.add(imgThumb);
	    
		
       /* c.anchor = GridBagConstraints.NORTH;
		c.weightx = 0.5;	
		c.fill = GridBagConstraints.HORIZONTAL;
	    c.gridx = 0;
	    c.gridy = 0;
	    c.anchor = GridBagConstraints.PAGE_START;
	    contentPane.add(addImage, c);
		
	    //c.fill = GridBagConstraints.HORIZONTAL;
	    c.weightx = 0.5;
	    c.gridx = 1;
	    c.gridy = 0;
	    contentPane.add(new JButton("Placeholder"), c);
	 
	    //c.fill = GridBagConstraints.HORIZONTAL;
	    c.weightx = 0.5;
	    c.gridx = 2;
	    c.gridy = 0;
	    contentPane.add(resetData, c);
	 
	    //c.fill = GridBagConstraints.HORIZONTAL;
	    c.ipady = 40;      //make this component tall
	    c.weightx = 0.0;
	    c.gridwidth = 3;
	    c.gridx = 0;
	    c.gridy = 1;
	    contentPane.add(search, c);
	 
	    c.weightx = 0.0;
	    c.gridwidth = 5;
	    c.gridx = 0;
	    c.gridy = 2;
	    contentPane.add(imgThumb, c);*/
	    
	    /*//button = new JButton("5");
	    c.fill = GridBagConstraints.HORIZONTAL;
	    c.ipady = 0;       //reset to default
	    c.weighty = 1.0;   //request any extra vertical space
	    c.anchor = GridBagConstraints.PAGE_END; //bottom of space
	    c.insets = new Insets(10,0,0,0);  //top padding
	    c.gridx = 1;       //aligned with button 2
	    c.gridwidth = 2;   //2 columns wide
	    c.gridy = 2;       //third row
	    contentPane.add(addImage, c);
	    */
		    
		    
		    
		    
		    
		    
		    
		contentPane.setVisible(true);
		setVisible(true);
		repaint();
		
		// File chooser to open query image
		addImage.addActionListener(new ActionListener() {
			@SuppressWarnings("static-access")
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Please select a query image");
				int returnVal = fileChooser.showOpenDialog(View.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					imgPath = fileChooser.getSelectedFile().getAbsolutePath();
					img = null;
					try {
						img = ImageIO.read(new File(imgPath));
						buffered = ImageIO.read(new File(imgPath));
						img = img.getScaledInstance(width, -1, img.SCALE_DEFAULT);
					} catch (IOException e1) {}
					imgThumb.setIcon(new ImageIcon(img));
				}
			}
		});
		
		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*
				 * TODO: Insert search function here
				 * 
				 * 
				 * 
				 * 
				 * 
				 * 
				 * 
				 */
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
