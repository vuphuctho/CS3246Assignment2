import java.awt.image.BufferedImage;
import java.util.Collections;

/*
 * Job of class Model
 * Load data (list of images)
 * Building index of all images
 * Perform search 
 * Index and save/update old queries to use as feedback relevance (not compulsory)
 * All Data Readers/Loaders, Histogram building are implemented here  
 */

public class Model {
	
	public static double[] getImageRank(BufferedImage q) {
		double[] rank = new double[20];
		double[] sim = getImageSim(q);
		
		for (int i=0; i<20; i++) {
			rank[i] = getMaxIndex(sim);
		}	
		return rank;
		
	}

	public static double[] getImageSim(BufferedImage q) {
		int N = 400;
		double[] sim = new double[N];
		
		for (int i=0; i<N; i++) {
			double[] ccvHistQuery = CCVHistogram.getCCVHistogram(q);
			double[] ccvHistIndex = FileIO.getCCVHist(i);		
			double[] texHistQuery = TextureHistogram.getTextureHistogram(q);
			double[] texHistIndex = FileIO.getTextureHist(i);
			
			double w = 0.3; //weight to texture Histogram
			double ccvDistance = calculateDistance(ccvHistQuery, ccvHistIndex);
			double texDistance = calculateDistance(texHistQuery, texHistIndex);
			
			sim[i] = 1 - texDistance * w + ccvDistance * (1-w);				
		}	
		return sim;		
	}
	
	public static double calculateDistance(double[] array1, double[] array2)
    {
		// Euclidean distance
        double Sum = 0.0;
        for(int i = 0; i < array1.length; i++) {
           Sum = Sum + Math.pow((array1[i]-array2[i]),2.0);
        }
        return Math.sqrt(Sum);
        
        
        // Bhattacharyya distance
        /*double h1 = 0.0;
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
        return dist;*/
    }
	
	private static double getMaxIndex(double[] sim) {
		double max = sim[0];
		int index = 0;
		for (int i=1;i<400;i++) {
			if (sim[i] > max) {
				max = sim[i];
				sim[i] = -1;
				index = i;
			}
		}
		return index;
	}
}
