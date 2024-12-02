package entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import handlers.InputHandler;
import main.GamePanel;

public class Player extends Entity{

	// get instances of the gamePanel and inputHandler
	GamePanel gamePanel;
	InputHandler inputHandler;
	
	// constructor, passing in gamePanel and inputHandler
	public Player(GamePanel gp, InputHandler inputH) {
		// assigning gamePanel and inputHandler
		this.gamePanel = gp;
		this.inputHandler = inputH;
		
		this.hitbox = new Hitbox(8, 16, 32, 32);
		
		// methods to be run on object instantiation
		setDefaultValues();
		getPlayerImages();
	}
	
	// override the default setDefaultValues() method
	public void setDefaultValues() {
		worldX = 100;
		worldY = 100;
		speed = 4;
		direction = "down";
		health = 10;
		maxHealth = 10;
	}
	
	// assign all the images to their corresponding variables using the getEntityImage() method from parent class
	public void getPlayerImages() {
		up1 = getEntityImage("player", "player_up_1.png");
		up2 = getEntityImage("player", "player_up_2.png");
		down1 = getEntityImage("player", "player_down_1.png");
		down2 = getEntityImage("player", "player_down_2.png");
		left1 = getEntityImage("player", "player_left_1.png");
		left2 = getEntityImage("player", "player_left_2.png");
		right1 = getEntityImage("player", "player_right_1.png");
		right2 = getEntityImage("player", "player_right_2.png");
		
	}
	
	// update function to be run in the game loop
	public void update() {
		
		// if a movement key is pressed, cycle through the sprites
		if(inputHandler.wPressed || inputHandler.aPressed || inputHandler.sPressed || inputHandler.dPressed) {
			spriteCounter++;
			// every 10 frames a sprite cycle should be run
			if(spriteCounter > 10) {
				if(spriteNum == 1) {
					spriteNum = 2;
				}else {
					spriteNum = 1;
				}
				spriteCounter = 0;
			}
			
			// player movement based on user input, changing direction
			if(inputHandler.wPressed) {
				direction = "up";
			}
			else if(inputHandler.sPressed) {
				direction = "down";
			}
			else if(inputHandler.aPressed) {
				direction = "left";
			}
			else if(inputHandler.dPressed) {
				direction = "right";
			}	
			
			// check tile collisions
			collision = false;
			gamePanel.collisionHandler.checkTiles(this);
			
			// if there is no collision, the player can move
			if(!collision) {
				switch(direction) {
				case "up":
					worldY -= speed;
					break;
				case "down":
					worldY += speed;
					break;
				case "left":
					worldX -= speed;
					break;
				case "right":
					worldX += speed;
					break;
				default:
					break;
				}
			}
			
			
		}
		
		
		
		
		
	}
	
	// draw method to be called in the paintComponent() method of the gamePanel
	public void draw(Graphics2D g2) {
		
		// the current image to be drawn to the screen
		BufferedImage currentImage = null;
		
		// find which image based on the sprite cycle and the players direction
		switch(direction) {
		case "up":
			if (spriteNum == 1) {
				currentImage = up1;
			}else if (spriteNum == 2) {
				currentImage = up2;
			}
			break;
		case "down":
			if (spriteNum == 1) {
				currentImage = down1;
			}else if (spriteNum == 2) {
				currentImage = down2;
			}
			break;
		case "left":
			if (spriteNum == 1) {
				currentImage = left1;
			}else if (spriteNum == 2) {
				currentImage = left2;
			}
			break;
		case "right":
			if (spriteNum == 1) {
				currentImage = right1;
			}else if (spriteNum == 2) {
				currentImage = right2;
			}
			break;
		default:
			currentImage = down1;
			break;
		}
		
		// draw the image to the screen using the Graphics2D library
		g2.drawImage(currentImage, worldX, worldY, gamePanel.getTileSize(), gamePanel.getTileSize(), null);
		
	}
	
}
