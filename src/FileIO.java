import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Vector;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.activation.MimetypesFileTypeMap;
import javax.imageio.ImageIO;


public class FileIO {
	final static File data = new File("data/Dataset");

	
	public static Vector<Image> readImageData() {
		Vector<Image> imgData = new Vector<Image>();
		
		for (final File fileEntry :data.listFiles()) {
			if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry);
	        } else {
	        	//String fileName = fileEntry.getName();
	        	//int pos = fileName.lastIndexOf(".");
	        	//if (pos>0) {
	        	//	fileName = fileName.substring(0, pos);
	        	//}
	        	//System.out.println(fileName + " " + fileEntry.getName());
	           
	        	Image img = null;

				try {
					img = ImageIO.read(fileEntry);
					//System.out.println(fileEntry.getName());
				} catch (IOException e) {}
				
				
				
	        	if (img != null) {
	            	
	        		System.out.println(imgData.size());
	            	imgData.add(img);
	                //System.out.println("It's an image");
	            } else {
	                //System.out.println("It's NOT an image");
	            }
	        }
		}
		
		
		
		
		
		
		
		return imgData;
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
