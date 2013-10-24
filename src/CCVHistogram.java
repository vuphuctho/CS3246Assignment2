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
        int[][][] imgIntensity = new int[imWidth][imHeight][3];
        boolean[][] check = new boolean[imWidth][imHeight];
        int step = 256 / dim;
        Raster raster = image.getRaster();
        
        int thres = 15; //threshold on when we consider a number of pixels as coherent
        
        // calculate image intensity and initialize check values
        for (int i=0; i<imWidth; i++) {
            for (int j=0; j<imHeight; j++) {
                int r = raster.getSample(i,j,0);
                int g = raster.getSample(i,j,1);
                int b = raster.getSample(i,j,2);
                int y  = (int)( 0.299   * r + 0.587   * g + 0.114   * b);
                int cb = (int)(-0.16874 * r - 0.33126 * g + 0.50000 * b);
                int cr = (int)( 0.50000 * r - 0.41869 * g - 0.08131 * b);
                
                int ybin = y / step;
                int cbbin = cb / step;
                int crbin = cr / step;

                imgIntensity[i][j][0] =  ybin;
                imgIntensity[i][j][1] =  cbbin;
                imgIntensity[i][j][2] =  crbin;

                check[i][j] = false;
            }
        }

        // calculate CCV Hisogram
        // apply Depth-First Search
        
        //System.out.printf("%d %d\n", imWidth, imHeight);
        //System.out.println(check[686][0]);
        for (int i=0; i<imWidth; i++) {
            for (int j=0; j<imHeight; j++) {
                // perform depth first search with unchecked pixel
                if (!check[i][j]) {
                	if (checkAdjacent(imgIntensity, i, j, check)) {
                		check[i][j]=true;
                	} else {
                		int area = dfs(imgIntensity, imgIntensity[i][j], i, j, imWidth, imHeight,check, 1);
                		if (area > 5) {
                			alpha[imgIntensity[i][j][0]*dim*dim
                			      +imgIntensity[i][j][1]*dim
                			      +imgIntensity[i][j][2]*dim]++;
                		} else {
                			beta[imgIntensity[i][j][0]*dim*dim
                			     +imgIntensity[i][j][1]*dim
                			     +imgIntensity[i][j][2]*dim]++;
                        }
                	}
                }
            }
        }
        
        //get the overall weighted bins value
        double weight = 0.7;
        for (int i=0; i < bins.length; i++) {
        	bins[i] = weight * alpha[i] + (1-weight) * beta[i];
            //bins[i] = weight;
        }
        
		return bins;
		
	}

    private static int dfs(int[][][] img, int[] val, int x, int y, int width, int height, boolean[][] check, int currentArea) {
        // neglect if position is not validated
        if (x<0 || y<0 || x>=img.length || y>=img[0].length) {
            return 0;
        }

        // neglect pixels being checked before
        if (check[x][y]== true) {
            return 0;
        }

        // neglect picel having different value
        if (img[x][y][0] != val[0] ||
            img[x][y][1] != val[1] ||
            img[x][y][2] != val[2]) {
            return 0;
        }

        // mark this pixel as checked
        if (img[x][y][0] == val[0] &&
                img[x][y][1] == val[1] &&
                img[x][y][2] == val[2]) {
        	currentArea++;
        	check[x][y] = true;
            
        	//System.out.printf("%d %d %d %d %d %d\n", x, y, img[x][y][0], img[x][y][1], img[x][y][2], currentArea);

            // initialize bound for loop
            int area =  1;
            int lowerX = (x==0)? 0: x-1;
            int upperX = (x==width-1)? height-1: x+1;
            int lowerY = (y==0)? 0: y-1;
            int upperY = (y==height-1)? height-1: y+1;    

            
            for (int i=lowerX; i<=upperX; i++) {
                for (int j=lowerY; j<=upperY; j++) {
                	if (currentArea == 10) 
                    	return currentArea;
                    if (i!=x && j!=y) {
                        area += dfs(img, val, i, j, width, height, check, currentArea);
                    }
                    
                }
            }
            
            return area;
        }
        return 0;
    }
    
    private static boolean checkAdjacent(int[][][] img, int x, int y, boolean[][] check) {
    	boolean result = false;
    	
    	int width = check.length;
    	int height = check[0].length;
    	int lowerX = (x==0)? 0: x-1;
        int upperX = (x==width-1)? width-1: x+1;
        int lowerY = (y==0)? 0: y-1;
        int upperY = (y==height-1)? height-1: y+1; 
    	
        for (int i=lowerX; i<=upperX; i++) {
        	for (int j=lowerY; j<=upperY; j++) {
        		if (i!=x && j!=y) {
        			//System.out.printf("%d %d %d %d\n", i, j, width, height);
        			if (check[i][j]== true && 
        					img[i][j][0] == img[x][y][0] &&
        					img[i][j][1] == img[x][y][1] &&
        					img[i][j][2] == img[x][y][2]) {
        				return true;
        			}
        		}
        	}
        }
        
    	return result;
    }
}
