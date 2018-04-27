package edu.utep.cs.cs3331.sudoku;

/* Name: Anthony Moran
 * Course: CS 3331
 * HW 1a
 * T/Th 1:30-2:50
 */

import java.io.*;
import org.json.*;
import java.net.*;

public class Board {
	private int size;
	private int sqrt;
	private int[][] contents;
	private int[][] set;
	
	public Board() {
		this.size = 4;
		this.sqrt = (int)Math.sqrt(size);
		contents = new int[size][size];
		set = new int[size][size];
	}
	
	public Board(int size) {
		this.size = size;
		this.sqrt = (int)Math.sqrt(size);
		contents = new int[size][size];
		set = new int[size][size];
	}
	
	public boolean isSolved() {
		for(int i=0;i<size;i++){
			if(!solvedRow(i) || !solvedColumn(i)) return false;
		}
		for(int i=0;i<size;i+=sqrt) {
			for(int j=0;j<size;j+=sqrt) {
				if(!solvedSquare(i,j)) return false;
			}
		}
		return true;
	}
	
	public boolean createPartial(int n) throws IOException, JSONException {
		HttpURLConnection con = null;
		JSONObject str = null;
		String urlString = "http://www.cs.utep.edu/cheon/ws/sudoku/new/?size=" + size + "&level=" + n;
        try {
            URL url = new URL(urlString);
            con = (HttpURLConnection) url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            str = new JSONObject(response.toString());
        } catch (IOException e) {return false;}
        finally {
        	if (con != null) {
        		con.disconnect();
        	}
        } 
        try {
        	if(str.getBoolean("response")) {
        		JSONArray array = str.getJSONArray("squares");
        		for(int i=0;i<array.length();i++) {
        			JSONObject test = array.getJSONObject(i);
        			int x,y,value;
        			x = test.getInt("x");
        			y = test.getInt("y");
        			set[x][y] = 1;      
        			value = test.getInt("value");
        			setEntry(x+1,y+1,value);
        		}
        	}
       } catch(Exception e) {return false;}
        return true;
	}
	
	private boolean solvedRow(int x) {
		int sum = 0;
		for(int i=0;i<size;i++) sum+=contents[x][i];
		int total = size*(size+1)/2;
		if(sum == total) return true;
		return false;
	}
	
	private boolean solvedColumn(int y) {
		int sum = 0;
		for(int i=0;i<size;i++) sum+=contents[i][y];
		int total = size*(size+1)/2;
		if(sum == total) return true;
		return false;
	}
	
	private boolean solvedSquare(int x, int y) {
		int startx = ((x-1)/sqrt) * sqrt; //finds starting index for the square
		int starty = ((y-1)/sqrt) * sqrt; //that the (x,y) coordinates are given
		int sum = 0;
		int total = size*(size+1)/2;
		for(int i=0;i<sqrt;i++) {
			for(int j=0;j<sqrt;j++) {
				sum += contents[startx + i][starty + j];
			}
		}
		if(sum == total) return true;
		return false;
	}
	
	public int size() {
		return size;
	}
	
	public boolean change(int x, int y, int n) {
		if(!isValid(x,y,n) || set[x-1][y-1] == 1) return false;
		else contents[x-1][y-1] = n;
		return true;
	}
	
	public int showEntry(int x, int y) {
		return contents[x-1][y-1];
	}
	
	public boolean isValid(int x, int y, int n) {
		if(x<1 || x>size || y<1 || y>size || n<0 || n>size) return false;
		if(inSameSquare(x,y,n) || inSameRC(x,y,n)) return false;
		return true;
	}
	
	private boolean inSameRC(int x, int y, int n) {
		for(int i=0;i<size;i++) {
			if(contents[i][y-1] == n && n != 0) return true;
			if(contents[x-1][i] == n && n != 0) return true;
		}
		return false;
	}

	private boolean inSameSquare(int x, int y, int n) {
		int startx = ((x-1)/sqrt) * sqrt;
		int starty = ((y-1)/sqrt) * sqrt;
		for(int i=0;i<sqrt;i++) {
			for(int j=0;j<sqrt;j++) {
				if(contents[startx+i][starty+j] == n && n != 0) return true;
			}
		}
		return false;
	}

	public void setEntry(int x, int y, int n) {
		contents[x-1][y-1] = n;
	}
}
