package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;

// class used to manage all the tiles on the map
public class TileManager {

	// get an instance of the gamePanel
	GamePanel gamePanel;
	// array that will store all the tiles that can be used
	public Tile[] tileArray;
	// the number of each tile at each coordinate, stored in a 2d array
	public int mapTileNum[][];
	
	// constructor, passing in gamePanel
	public TileManager(GamePanel gp) {
		// assign gamePanel
		this.gamePanel = gp;
	
		// instantiate the tileArray with a size of 10
		tileArray = new Tile[10];
		// instantiate the mapTileNum array with size of max screen columns and max screen rows
		mapTileNum = new int[gamePanel.getMaxScreenCol()][gamePanel.getMaxScreenRow()];
	
		// initial methods to run
		getTileImages();
		loadMap("/map/testmap.txt");
	}
	
	// storing each tile in the tile array
	public void getTileImages() {
		tileArray[0] = new Tile();
		tileArray[0].image = loadTileImage("grass");
		
		tileArray[1] = new Tile();
		tileArray[1].image = loadTileImage("wall");
		tileArray[1].collidable = true;
		
		tileArray[2] = new Tile();
		tileArray[2].image = loadTileImage("water");
		tileArray[2].collidable = true;
		
		tileArray[3] = new Tile();
		tileArray[3].image = loadTileImage("tree");
		tileArray[3].collidable = true;
	}
	
	// method to get the image from the file path, passing in the name of the tile
	private BufferedImage loadTileImage(String tileName) {
		try {
			return ImageIO.read(getClass().getResourceAsStream("/tiles/"+tileName+".png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// method to load the map, passing in the path to the map txt file
	public void loadMap(String mapPath) {
		try {
			// an input stream of the map data from the txt file
			InputStream is = getClass().getResourceAsStream(mapPath);
			// reader to read the txt file data
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			// the current row and column
			int col = 0;
			int row = 0;
			// loop through the whole map
			while (col < gamePanel.getMaxScreenCol() && row < gamePanel.getMaxScreenRow()) {
				// read the current line
				String line = reader.readLine();
				
				// go through each column on that row
				while(col < gamePanel.getMaxScreenCol()) {
					// split the previously read line at each space
					String numbers[] = line.split(" ");
					
					// parse the number from a string to an integer
					int num = Integer.parseInt(numbers[col]);
					
					// assign the element at position [col][row] in the mapTileNum array to be the number
					mapTileNum[col][row] = num;
					// increment col
					col++;
				}
				// if reached the end of the row
				if(col == gamePanel.getMaxScreenCol()){
					// go back to the first column
					col = 0;
					// go to the next row
					row++;
				}
			}
			// close the reader
			reader.close();
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// method to draw the map
	public void draw(Graphics2D g2) {
		// position variables
		int col = 0;
		int row = 0;
		int x = 0;
		int y = 0;
		
		// loop through the whole map
		while(col < gamePanel.getMaxScreenCol() && row < gamePanel.getMaxScreenRow()) {
			// get the index of the tile at the current coordinates from the mapTileNum array
			int currentTileNum = mapTileNum[col][row];
			
			// daw the image using the index in the tileArray
			g2.drawImage(tileArray[currentTileNum].image, x, y, gamePanel.getTileSize(), gamePanel.getTileSize(), null);
			// move to the next column
			col++;
			x += gamePanel.getTileSize();
			// if it has reached the end of the row, go back to the start of the next row
			if(col == gamePanel.getMaxScreenCol()) {
				col = 0;
				row++;
				x = 0;
				y += gamePanel.getTileSize();
			}
		}
	}
	
}
