import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Vector;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;


public class FileIO {
	final static int N = 400; // max number of files
	final static File data = new File("D:/Workplace/CS3246Assignment2/data/Dataset");

	//public static Vector<double[]> imgTextureHist = new Vector<double[]>();
	
	public static void readImageData() {
				
		for (final File fileEntry :data.listFiles()) {
			if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry);
	        } else {
	        	String fileName = fileEntry.getName();
	        	int pos = fileName.lastIndexOf(".");
	        	if (pos>0) {
	        		fileName = fileName.substring(0, pos);
	        	}
	           
	        	BufferedImage img = null;

				try {
					img = ImageIO.read(fileEntry);
					if (img != null) {
						double[] imgCCVHist = null;
						double[] imgTextureHist = null;
			        	System.out.println(fileEntry.getName());
						TextureHistogram th = new TextureHistogram();
		        		imgCCVHist = CCVHistogram.getCCVHistogram(img);
						imgTextureHist = th.getTextureHistogram(img);		        		
		        		FileWriter fw = new FileWriter("D:/Workplace/CS3246Assignment2/index/" + fileName + ".txt");
		        		BufferedWriter writer = new BufferedWriter(fw);
		        		
		        		writer.write(imgTextureHist.length+ "\n");
		                for (int i=0; i<imgTextureHist.length; i++) {
		                	writer.write(imgTextureHist[i] + "\n");
		                }
		                writer.write(imgCCVHist.length + "\n");
		                for (int i=0; i<imgCCVHist.length; i++) {
		                	writer.write(imgCCVHist[i]+ "\n");
		                }
		        		writer.close();
		        		fw.close();
		        	    img.flush();
		        	    img = null;
		        	    th = null;	
		        	    imgTextureHist = null;
		            }
				} catch (IOException e) {}
	        }
		}	
	}

	public static void getHist(double[][] CCVHist, double[][] TextureHist) {
		for (final File fileEntry :data.listFiles()) {
			if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry);
	        } else {
	        	FileInputStream fis;
				try {
					// get image number
					String fileName = fileEntry.getName();
		        	int pos = fileName.lastIndexOf(".");
		        	if (pos>0) {
		        		fileName = fileName.substring(0, pos);
		        	}
		        	
		        	int imgNo = Integer.parseInt(fileName);
		        	
					fis = new FileInputStream(fileEntry);
					DataInputStream in = new DataInputStream(fis);
		            BufferedReader br = new BufferedReader(new InputStreamReader(in));
		            
		            int TextureHistLength = Integer.parseInt(br.readLine());
		            for (int i=0; i<TextureHistLength; i++) {
		            	TextureHist[imgNo][i] = Double.parseDouble(br.readLine());
		            }
		            int CCVHistLength = Integer.parseInt(br.readLine());
		            for (int i=0; i<CCVHistLength; i++) {
		            	CCVHist[imgNo][i] = Double.parseDouble(br.readLine());
		            }
		            
		            in.close();
		            fis.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
	        }	
		}
	}
	
	public static void listFilesForFolder(final File folder) {
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry);
	        } else {
	            System.out.println(fileEntry.getName());
	        }
	    }
	}
	
	public static void main(String[] args) {
		readImageData();
	}
}
