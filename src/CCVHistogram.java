import java.awt.image.BufferedImage;
import java.awt.image.Raster;


public class CCVHistogram {
	
	static int dim = 64;

	public static double[] getCCVHistogram(BufferedImage image) {
		int imHeight = image.getHeight();
        int imWidth = image.getWidth();
        double[] alpha = new double[dim*dim*dim]; //bins with coherent pixels
        double[] beta = new double[dim*dim*dim];  //bins with non-coherent pixels
        double[] bins = new double[dim*dim*dim]; //overall weighted value of alpha and beta bins
        int step = 256 / dim;
        Raster raster = image.getRaster();
        
        int thres = 15; //threshold on when we consider a number of pixels as coherent
        
        /*****************************************************************************
		//TODO: Use DFS or NFS or any other algorithm to find connected components
		* Maybe BFS better?
		* 
			int count = 0; //number of current coherent nodes
			
			for each unvisited node (i,j) do 
		
				//1. Read the color value from pixel
				int r = raster.getSample(i,j,0);
            	int g = raster.getSample(i,j,1);
            	int b = raster.getSample(i,j,2);
            	int y  = (int)( 0.299   * r + 0.587   * g + 0.114   * b);
        		int cb = (int)(-0.16874 * r - 0.33126 * g + 0.50000 * b);
        		int cr = (int)( 0.50000 * r - 0.41869 * g - 0.08131 * b);
        		
        		int ybin = y / step;
        		int cbbin = cb / step;
        		int crbin = cr / step;
        		
        		//2. Look for pixels with the same color (ybin, cbbin, crbin) then coninue search
        		  
        		//If no more pixels found (search done), stop search and look at count 
        		if (count > thres) 
        			alpha[ybin*dim*dim + cbbin*dim + crbin] += count;
        			//not sure if ybin*dim*dim+cbbin*dim+crbin*dim
        		else 
        			beta[ybin*dim*dim + cbbin*dim + crbin] += count;
        			//not sure if ybin*dim*dim+cbbin*dim+crbin*dim
        		
        		//3. Continue until all nodes are visited, when done just exit
        		
		*
		*
		*
		*******************************************************************************/
        
        //get the overall weighted bins value
        double weight = 0.7;
        for (int i=0; i < bins.length; i++) {
        	bins[i] = weight * alpha[i] + (1-weight) * beta[i];
        }
        
		return bins;
		
	}
}
