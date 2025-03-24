package main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JPanel;

import AI.Pathfinder;
import UI.Game.GameUI;
import enemySpawning.EnemySpawner;
import entities.Entity;
import entities.Player;
import handlers.AssetHandler;
import handlers.CollisionHandler;
import handlers.EnemyHandler;
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
	public EnemyHandler enemyHandler = new EnemyHandler(this);

	public GameUI gameUI = new GameUI(this);

	Thread gameThread;

	// instantiate the player class
	public Player player = new Player(this);
	// objects and NPCs
	public Entity[] npcs = new Entity[10];
	public Item[] items = new Item[15];
	public EnemySpawner[] spawners = new EnemySpawner[5];


	// constructor
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight)); // set the size of the JPanel (the screen)
		this.setBackground(Color.black);
		this.setDoubleBuffered(true); // all drawing will be done in an off screen buffer to avoid any visual bugs and improving performance
		this.addKeyListener(inputHandler);
		this.addMouseListener(inputHandler);
		this.setFocusable(true); // the game panel can be "focused" to receive all key inputs
	}

	public void setupGame() {
		// assetHandler.setNPC();
		assetHandler.setItem();
		assetHandler.setSpawner();
	}

	
	public void startGameThread() {
		gameThread = new Thread(this); // instantiate a new thread
		gameThread.start(); // start the thread, automatically calls the run method
	}
	
	// used to restart the game, resets all the required attributes back to their original
	public void restart(){
		player = new Player(this);
		npcs = new Entity[10];
		enemyHandler.spawnedEnemies = new Entity[10];
		items = new Item[15];
		spawners = new EnemySpawner[5];

		setupGame();
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

				// attempt to spawn from spawners
				for(EnemySpawner spawner: spawners){
					if(spawner != null){
						spawner.attemptSpawn();
					}
				}

				// update enemyHandler.spawnedEnemies
				for(Entity enemy : enemyHandler.spawnedEnemies){
					if(enemy != null){
						enemy.update();
					}
				}

				if(inputHandler.ePressed){
					inputHandler.ePressed = false;
					stateHandler.changeState(State.INVENTORY);
				}

				if(inputHandler.escPressed){
					inputHandler.escPressed = false;
					stateHandler.changeState(State.PAUSE);
				}

				if(inputHandler.lPressed){
					player.takeDamage(100);
				}

				break;
			case INVENTORY:
				if(inputHandler.escPressed){
					inputHandler.escPressed = false;
					stateHandler.changeState(State.GAME);
				}

				player.inventory.update();
				break;
			case MAIN_MENU:
				gameUI.updateButtons();

				if(inputHandler.enterPressed){
					stateHandler.changeState(State.GAME);
				}
				break;
			case PAUSE:
				if(inputHandler.enterPressed || inputHandler.escPressed){
					inputHandler.enterPressed = false;
					inputHandler.escPressed = false;
					stateHandler.changeState(State.GAME);
				}

				gameUI.updateButtons();
				break;
			case DEATH:
				gameUI.updateButtons();
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

				for(Entity enemy : enemyHandler.spawnedEnemies){
					if(enemy != null){
						enemy.draw(g2);
					}
				}


				for(Item item: items){
					if(item != null){
						item.draw(g2);
					}
				}

				gameUI.drawHealthBar(g2);

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

				// draw enemyHandler.spawnedEnemies
				for(Entity enemy : enemyHandler.spawnedEnemies){
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
			case MAIN_MENU:
				gameUI.drawMainMenu(g2);
				break;
			case PAUSE:
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

				// draw enemyHandler.spawnedEnemies
				for(Entity enemy : enemyHandler.spawnedEnemies){
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

				gameUI.drawHealthBar(g2);

				g2.setColor(new Color(53, 53, 53,100));
				g2.fillRect(0, 0, screenWidth, screenHeight);
				gameUI.drawPauseMenu(g2);
				break;
			case DEATH:
				gameUI.drawDeathMenu(g2);
				break;
		}
		
		// disposes the graphics content and release system resources, preventing issues such as memory leaks
		g2.dispose();
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
