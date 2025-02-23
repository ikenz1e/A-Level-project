package main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JPanel;

import AI.Pathfinder;
import entities.Entity;
import entities.Player;
import handlers.AssetHandler;
import handlers.CollisionHandler;
import handlers.InputHandler;
import handlers.StateHandler;
import item.Item;
import tile.TileManager;
import utils.State;
import utils.Utils;

public class GamePanel extends JPanel implements Runnable {

	// screen settings
	final int originalTileSize = 16; // 16x16 tiles
	final int tileScaler = 3; // scale the tiles to be 48x48
	
	final int tileSize = originalTileSize * tileScaler; // the final 48x48 tile size
	final int maxScreenCol = 16; // 16 tiles in the x (16 columnns)
	final int maxScreenRow = 12; // 12 tiles in the y (12 rows)
	final int screenWidth = tileSize * maxScreenCol; // the screen width in pixels, calculated using max X tiles and tile size (768 pixels)
	final int screenHeight = tileSize * maxScreenRow; // the screen height in pixels, calculated using max Y tiles and tile size (576 pixels)
	
	// FPS
	int FPS = 60;
	
	// instantiate any handlers/managers/utility classes
	public Utils utils = new Utils();
	public InputHandler inputHandler = new InputHandler();
	public CollisionHandler collisionHandler = new CollisionHandler(this);
	public TileManager tileManager = new TileManager(this);
	public AssetHandler assetHandler = new AssetHandler(this);
	public Pathfinder pathFinder = new Pathfinder(this);
	public StateHandler stateHandler = new StateHandler();

	Thread gameThread;

	// instantiate the player class
	public Player player = new Player(this);
	// objects and NPCs
	public Entity[] npcs = new Entity[10];
	public Entity[] enemies = new Entity[10];
	public Item[] items = new Item[10];


	// constructor
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight)); // set the size of the JPanel (the screen)
		this.setBackground(Color.black);
		this.setDoubleBuffered(true); // all drawing will be done in an off screen buffer to avoid any visual bugs and improving performance
		this.addKeyListener(inputHandler);
		this.setFocusable(true); // the game panel can be "focused" to receive all key inputs
	}

	public void setupGame() {
		assetHandler.setNPC();
		assetHandler.setItem();
		assetHandler.setEnemy();
	}

	
	public void startGameThread() {
		gameThread = new Thread(this); // instantiate a new thread
		gameThread.start(); // start the thread, automatically calls the run method
	}
	
	// automatically implemented when using the Runnable interface, used for Threads
	@Override
	public void run() {
		
		double drawInterval = 1000000000/FPS; // draw interval is 1 second (in nano seconds) divided by FPS, how often the program should draw to the screen
		double delta = 0; // the change in time
		long lastTime = System.nanoTime();
		long currentTime;
		
		// main game loop, 2 main functions: update information, draw the new information to the screen
		while (gameThread != null) {
			
			// get the current time in nano seconds
			currentTime = System.nanoTime();
			// divide the time that has passed by the draw interval, and add to the delta (change)
			delta += (currentTime - lastTime) / drawInterval;
			// set the last time to the current time
			lastTime = currentTime;
			
			// if delta >= 1, at least 1 draw interval has passed so continue with the game loop
			if(delta >= 1) {
				// update 
				update();
				// draw
				// a standard java method that calls the paintComponent method but also clears the screen to prevent smearing
				repaint();
				// set the change in time back to 0
				delta = 0;
			}
		}
		
	}
	
	public void update() {
		
		switch(stateHandler.getCurrentState()){
			case GAME:
				// update the player
				player.update();

				// update NPCs
				for(Entity NPC : npcs){
					if(NPC != null){
						NPC.update();
					}
				}

				// update enemies
				for(Entity enemy : enemies){
					if(enemy != null){
						enemy.update();
					}
				}

				if(inputHandler.ePressed){
					inputHandler.ePressed = false;
					stateHandler.changeState(State.INVENTORY);
				}
				break;
			case INVENTORY:
				if(inputHandler.escPressed){
					stateHandler.changeState(State.GAME);
				}

				player.inventory.update();

				break;
		}
		
	}
	
	// standard java method for drawing to a JPanel, graphics class is also a standard java class for graphical functions
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		// the standard java class for drawing 2D graphics
		Graphics2D g2 = (Graphics2D) g;
		
		switch (stateHandler.getCurrentState()) {
			case GAME:
				//draw the map
				tileManager.draw(g2);
		
				// draw the player
				player.draw(g2);

				for(Entity entity : npcs) {
					if(entity != null){
						entity.draw(g2);
					}
				}

				for(Entity enemy : enemies){
					if(enemy != null){
						enemy.draw(g2);
					}
				}


				for(Item item: items){
					if(item != null){
						item.draw(g2);
					}
				}
				break;
			case INVENTORY:
				//draw the map
				tileManager.draw(g2);
		
				// draw the player
				player.draw(g2);

				// draw npcs
				for(Entity entity : npcs) {
					if(entity != null){
						entity.draw(g2);
					}
				}

				// draw enemies
				for(Entity enemy : enemies){
					if(enemy != null){
						enemy.draw(g2);
					}
				}
				// draw items
				for(Item item: items){
					if(item != null){
						item.draw(g2);
					}
				}
				
				// apply a tint over the screen
				g2.setColor(new Color(53, 53, 53,100));
				g2.fill(new Rectangle(0, 0, screenWidth, screenHeight));
				player.inventory.draw(g2);
				break;
			default:
				break;
		}
		
		// disposes the graphics content and release system resources, preventing issues such as memory leaks
		g2.dispose();
	}
	
	// method to defeat an enemy, takes the index of the enemy in the enemies array
	public void defeatEnemy(int index){
		// make sure the enemy exists
		if(enemies[index] != null){
			// remove the enemy 
			enemies[index] = null;
		}
	}

	// method for getting the tile size in another class
	public int getTileSize() {
		return tileSize;
	}
	// method for getting max screen x
	public int getMaxScreenCol() {
		return maxScreenCol;
	}
	// method for getting max screen y
	public int getMaxScreenRow() {
		return maxScreenRow;
	}

	public int getScreenWidth(){
		return screenWidth;
	}

	public int getScreenHeight(){
		return screenHeight;
	}
	
}
