import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 * Job of color Histogram
 * Index image into histogram using color
 * with CCV (Color Coherent Vector) 
 */

public class ColorHistogram extends JFrame {

	private JButton imageField1; // button to select the image 1 
	private JButton imageField2; // button to select the image 2
	
	private JLabel imageLabel1;
	private JLabel imageLabel2;
	private String imagePath1;
	private String imagePath2;
	private Image img1;
	private Image img2;
	private JLabel distLabel;
	
	private JPanel contentPane;
	private JPanel imagePanel;
	private JPanel histPanel;
	
	private JButton computeSimilarityButton;
	private String basePath;
	private int width = 300; // the size for each image result
	private int height = 300;
	
	private int dim = 64;
	
	private BufferedImage buffered1;
	private BufferedImage buffered2;
	private Histogram hist1;
	private Histogram hist2;
	
	public ColorHistogram() {
		basePath = "E:\\workspace\\ImageSearchFramework\\"; // change it when necessary
		
		imageField1 = new JButton("Choose image 1");
		imageField2 = new JButton("Choose image 2");
		
		imageLabel1 = new JLabel();
		imageLabel2 = new JLabel();
		
		computeSimilarityButton = new JButton("Compute similarity");
		distLabel = new JLabel("", JLabel.CENTER);
		distLabel.setFont(new Font("Serif", Font.BOLD, 20));
		
		imagePanel = new JPanel();

		hist1 = new Histogram();
		hist2 = new Histogram();
		contentPane = (JPanel)this.getContentPane();
		
		setSize(1200,600);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		init();
	}
	
	public void init() {
		
/*		imagePanel.setLayout(new GridLayout(3,2));
		
		imagePanel.add(imageField1);
		imagePanel.add(imageLabel1);
		
		//imagePanel.add(hist1);
		imagePanel.add(imageField2);
		imagePanel.add(imageLabel2);
		//imagePanel.add(hist2);

		imagePanel.add(computeSimilarityButton);
		imagePanel.add(distLabel);
		
		imagePanel.setVisible(true);
		hist1.setVisible(true);
		hist2.setVisible(true);
		
		
		GridLayout gridLayout = new GridLayout();  // grid layout 1 * 2
		gridLayout.setColumns(1); 
		gridLayout.setRows(3); 
		contentPane.setLayout(gridLayout); 
		
		contentPane.add(imagePanel);
		contentPane.add(hist1);
		contentPane.add(hist2);
		contentPane.setVisible(true);
		setVisible(true);
		repaint();
*/		
		
		GridLayout gridLayout = new GridLayout();  // grid layout 1 * 2
		gridLayout.setColumns(2); 
		gridLayout.setRows(4); 
		contentPane.setLayout(gridLayout);
		contentPane.add(imageField1);
		contentPane.add(imageField2);
		contentPane.add(imageLabel1);
		contentPane.add(imageLabel2);
		contentPane.add(hist1);
		contentPane.add(hist2);
		contentPane.add(computeSimilarityButton);
		contentPane.add(distLabel);
		contentPane.setVisible(true);
		setVisible(true);
		repaint();
		
		imageField1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Please select a sample image");
				String path = "";
				int returnVal =  fileChooser.showOpenDialog(ColorHistogram.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					imagePath1 = fileChooser.getSelectedFile().getAbsolutePath();
					img1 = null;
					try {
						img1 = ImageIO.read(new File(imagePath1));
						buffered1 = ImageIO.read(new File(imagePath1));
						hist1.load(imagePath1); // paint histogram
						hist1.repaint();
						img1 = img1.getScaledInstance(width, -1, img1.SCALE_DEFAULT);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					imageLabel1.setIcon(new ImageIcon(img1));
				}
			}
		});
	
		imageField2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Please select a sample image");
				String path = "";
				int returnVal =  fileChooser.showOpenDialog(ColorHistogram.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					imagePath2 = fileChooser.getSelectedFile().getAbsolutePath();
					img2 = null;
					try {
						img2 = ImageIO.read(new File(imagePath2));
						buffered2 = ImageIO.read(new File(imagePath2));
						hist2.load(imagePath2);
						hist2.repaint();
						img2 = img2.getScaledInstance(width, -1, img2.SCALE_DEFAULT);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					imageLabel2.setIcon(new ImageIcon(img2));
					
				}
			}
		});
		
		computeSimilarityButton.addActionListener(new ActionListener() {
			/*
			 * replace and write your own code here
			 */
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				double sim = computeSimilarity();
				distLabel.setText("Similarity is " + Double.toString(sim));
			}
		});
		
		repaint();
		
	}
	
	public double computeSimilarity() {
		
		double[] hist1 = getHist('a',buffered1);
		double[] hist2 = getHist('a',buffered2);
		
		double distance = calculateDistance(hist1, hist2);
		return 1-distance;
	}
	
	public double[] getHist(char type, BufferedImage image) {
		int imHeight = image.getHeight();
        int imWidth = image.getWidth();
        double[] bins = new double[dim*dim*dim];
        double[] binsCCV = new double[dim*dim*dim];
        double[] alpha = new double[dim*dim*dim];
        double[] beta = new double[dim*dim*dim];
        int step = 256 / dim;
        Raster raster = image.getRaster();
        for(int i = 0; i < imWidth; i++)
        {
            for(int j = 0; j < imHeight; j++)
            {
            	// rgb->ycrcb
            	int r = raster.getSample(i,j,0);
            	int g = raster.getSample(i,j,1);
            	int b = raster.getSample(i,j,2);
            	int y  = (int)( 0.299   * r + 0.587   * g + 0.114   * b);
        		int cb = (int)(-0.16874 * r - 0.33126 * g + 0.50000 * b);
        		int cr = (int)( 0.50000 * r - 0.41869 * g - 0.08131 * b);
        		
        		int ybin = y / step;
        		int cbbin = cb / step;
        		int crbin = cr / step;

                bins[ ybin*dim*dim+cbbin*dim+crbin*dim ] ++;
                if (isCoherent(raster,i,j,ybin,cbbin,crbin) == true)
                	alpha[ ybin*dim*dim+cbbin*dim+crbin*dim ] ++;
                else 
                	beta[ ybin*dim*dim+cbbin*dim+crbin*dim ] ++;
                
            }
        }
        
        //normalize
        for(int i = 0; i < 3*dim; i++) {
        	bins[i] = bins[i]/(imHeight*imWidth);
        	alpha[i] = alpha[i]/(imHeight*imWidth);
        	beta[i] = beta[i]/(imHeight*imWidth);
        }
        if (type == 'a')
        	return alpha;
        else if (type == 'b')
        	return beta;
        return bins;
	}
	
	private boolean isCoherent(Raster raster, int _i, int _j, int _ybin, int _cbbin, int _crbin) {
		/*int count = 0; //number of coherent pixels
		int step = 256 / dim;
		for (int i = _i-1; i < _i+2; i++) {
			for (int j = _j-1; j < _j+2; j++) {
				if ((i != _i) && (j != _j)) {
					int r = raster.getSample(i,j,0);
		        	int g = raster.getSample(i,j,1);
		        	int b = raster.getSample(i,j,2);
		        	int y  = (int)( 0.299   * r + 0.587   * g + 0.114   * b);
		    		int cb = (int)(-0.16874 * r - 0.33126 * g + 0.50000 * b);
		    		int cr = (int)( 0.50000 * r - 0.41869 * g - 0.08131 * b);
		    		
		    		int ybin = y / step;
	        		int cbbin = cb / step;
	        		int crbin = cr / step;

		    		if ((ybin == _ybin) && (cbbin == _cbbin) && (crbin == _crbin)) {
		    			count++;
		    			return true;
		    		}
				}
			}*/
		
		//}

		return false;
	}

	public double calculateDistance(double[] array1, double[] array2)
    {
		// Euclidean distance
        /*double Sum = 0.0;
        for(int i = 0; i < array1.length; i++) {
           Sum = Sum + Math.pow((array1[i]-array2[i]),2.0);
        }
        return Math.sqrt(Sum);
        */
        
        // Bhattacharyya distance
		double h1 = 0.0;
		double h2 = 0.0;
		int N = array1.length;
        for(int i = 0; i < N; i++) {
        	h1 = h1 + array1[i];
        	h2 = h2 + array2[i];
        }

        double Sum = 0.0;
        for(int i = 0; i < N; i++) {
           Sum = Sum + Math.sqrt(array1[i]*array2[i]);
        }
        double dist = Math.sqrt( 1 - Sum / Math.sqrt(h1*h2));
        return dist;
    }
	
    
	public static void main(String[] args) {
		ColorHistogram example = new ColorHistogram();
	}
}
