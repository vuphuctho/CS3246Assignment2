/*
 * Job of class Texture Histogram
 * Index image into histogram using texture
 * with edge direction histogram technique
 */
public class TextureHistogram {

	public int[][] filterMatrix(int[][] input, int filter_type) {		
		int[][] output = new int[input.length][input[0].length];
		int[][] filter0 = { //F0: 0 degrees
				{1,1,-2},
				{2,2,-4},
				{1,1,-2}
		};
		//Change later when working
		int[][] filter1 = { //F0: 0 degrees
				{1,1,-2},
				{2,2,-4},
				{1,1,-2}
		};
		int[][] filter2 = { //F0: 0 degrees
				{1,1,-2},
				{2,2,-4},
				{1,1,-2}
		};
		int[][] filter3 = { //F0: 0 degrees
				{1,1,-2},
				{2,2,-4},
				{1,1,-2}
		};
		int[][] filter4 = { //F0: 0 degrees
				{1,1,-2},
				{2,2,-4},
				{1,1,-2}
		};
		int[][] filter5 = { //F0: 0 degrees
				{1,1,-2},
				{2,2,-4},
				{1,1,-2}
		};
		int[][] filter6 = { //F0: 0 degrees
				{1,1,-2},
				{2,2,-4},
				{1,1,-2}
		};
		int[][] filter7 = { //F0: 0 degrees
				{1,1,-2},
				{2,2,-4},
				{1,1,-2}
		};
		
		int[][] filter = filter1; //based on filter_type, a different filter number will be assigned
		
		for (int i=0; i<input.length; i+= 3) {
			for (int j=0; i<input[0].length; j+=3) {
				//check if filter is applied outside matrix
				if ((i >= input.length) || (j >= input[0].length)) {
				    break;
				} else { //if not then calculate multiplication
					/*output[i][j] =     input[i][j] * filter[0][0]    + input[i][j+1] * filter[1][0]   + input[i][j+2] * filter[2][0];
					output[i+1][j] =   input[i+1][j] * filter[0][0]  + input[i+1][j+1] * filter[1][0] + input[i+1][j+2] * filter[2][0];
					output[i+2][j] =   input[i+2][j] * filter[0][0]  + input[i+2][j+1] * filter[1][0] + input[i+2][j+2] * filter[2][0];
					output[i][j+1] =   input[i][j] * filter[0][1]    + input[i][j+1] * filter[1][1]   + input[i][j+2] * filter[2][1];
					output[i+1][j+1] = input[i+1][j] * filter[0][1]  + input[i+1][j+1] * filter[1][1] + input[i+1][j+2] * filter[2][1];
					output[i+2][j+1] = input[i+2][j] * filter[0][1]  + input[i+2][j+1] * filter[1][1] + input[i+2][j+2] * filter[2][1];
					output[i][j+2] =   input[i][j] * filter[0][2]    + input[i][j+1] * filter[1][2]   + input[i][j+2] * filter[2][2];
					output[i+1][j+2] = input[i+1][j] * filter[0][2]  + input[i+1][j+1] * filter[1][2] + input[i+1][j+2] * filter[2][2];
					output[i+2][j+2] = input[i+2][j] * filter[0][2]  + input[i+2][j+1] * filter[1][2] + input[i+2][j+2] * filter[2][2];*/
					for(int u=0; u<3; u++){
					    for(int t=0; t<3; t++){
					    	for(int v=0; v<3; v++){
					    		output[u][t]+=input[u][v]*filter[v][t];
					    	}
					    }
					}

				}
			}
		}
		
		
		
		return output;
		
	}
	
	public static void main(String[] args) {
		int[][] test = {
				{1,2,3},
				{4,5,6},
				{7,8,9}
		};
		TextureHistogram tx = new TextureHistogram();
		int[][] result = tx.filterMatrix(test,0);
		for (int i=0; i<result.length; i++) {
			for (int j=0; j<result[0].length; j++) {
				System.out.println(i + " " + j + " : " + result[i][j]);
			}
		}
	}
}