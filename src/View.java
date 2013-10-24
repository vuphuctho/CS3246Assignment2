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
	private JPanel controlBar;
	private JButton addImage;
	private JButton search;
	private JButton resetData;

	private String imgPath;
	private Image img;
	private BufferedImage buffered;
	private JLabel imgThumb;

	private JPanel searchResults;
	private JScrollPane searchScroll;
	private JLabel[] resThumb;

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

		// Initialize variables
		// controlBar = (JPanel)this.getContentPane();
		controlBar = new JPanel();
		addImage = new JButton("Add Image");
		search = new JButton("Search now!");
		resetData = new JButton("Reset Search History");

		imgPath = "";
		img = null;
		buffered = null;
		imgThumb = new JLabel();

		searchResults = new JPanel();
		searchScroll = new JScrollPane(searchResults,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		resThumb = new JLabel[20];
		for (int i = 0; i < 20; i++) {
			resThumb[i] = new JLabel();
		}

		// Set bound of panel
		searchScroll.setBounds(30, 260, 740, 300);

		// Set bound of buttons
		addImage.setBounds(50, 50, 10, 25);
		search.setBounds(150, 50, 10, 25);
		resetData.setBounds(250, 50, 10, 25);
		imgThumb.setBounds(250, 40, 300, 200);

		// Adding search results
		GridLayout gridLayout = new GridLayout();
		gridLayout.setColumns(2);
		gridLayout.setRows(20);
		searchResults.setLayout(gridLayout);

		for (int i = 0; i < 20; i++) {
			searchResults.add(new JLabel("Rank " + (int) (i + 1)));
			searchResults.add(resThumb[i]);
		}

		// Adding components
		controlBar.add(addImage);
		controlBar.add(search);
		controlBar.add(resetData);
		controlBar.setVisible(true);
		// searchResults.add(imgThumb);
		searchResults.setVisible(true);
		imgThumb.setVisible(true);
		add(imgThumb);
		add(searchScroll);
		add(controlBar);

		// Attaching image button
		addImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Please select a query image");
				int returnVal = fileChooser.showOpenDialog(View.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					imgPath = fileChooser.getSelectedFile().getAbsolutePath();
					try {
						img = ImageIO.read(new File(imgPath));
						buffered = ImageIO.read(new File(imgPath));
						img = img.getScaledInstance(width, -1,
								img.SCALE_DEFAULT);
					} catch (IOException e1) {
					}
					imgThumb.setIcon(new ImageIcon(img));
				}
			}
		});

		// Perform search
		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getResults(buffered);
				imgThumb.setIcon(new ImageIcon(img));

			}
		});
	}

	protected void getResults(BufferedImage buffered) {
		String[] results = new String[20];
		
		double[] dist = new double[400];
		
		for (int i = 0; i < 20; i++) {
			File imgFile = new File(results[i]);
			Image resultImg = null;
			try {
				resultImg = ImageIO.read(imgFile);
			} catch (IOException e) {
				System.out.println("Failed to load iamge!");
			}

			resultImg = resultImg.getScaledInstance(width, -1,
					img.SCALE_DEFAULT);
			resThumb[i].setIcon(new ImageIcon(resultImg));
		}
	}

	// main class
	// only for testing view
	public static void main(String[] args) {
		View view = new View();
		view.setVisible(true);
	}
}