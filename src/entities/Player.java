package entities;

import java.awt.Rectangle;

import handlers.InputHandler;
import item.Item;
import main.GamePanel;
import utils.ItemType;

public class Player extends Entity{

	// get instances of the gamePanel and inputHandler
	InputHandler inputHandler;
	
	public Item currentWeapon;

	// constructor, passing in gamePanel and inputHandler
	public Player(GamePanel gp) {
		// assigning gamePanel and inputHandler
		super(gp);
		this.inputHandler = gamePanel.inputHandler;
		
		this.hitbox = new Rectangle(8, 16, 32, 32);
		hitboxDefaultX = (int)this.hitbox.getX();
		hitboxDefaultY = (int)this.hitbox.getY();
		
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
		currentWeapon = null;
	}
	
	// assign all the images to their corresponding variables using the getEntityImage() method from parent class
	public void getPlayerImages() {
		up1 = gamePanel.utils.getImage("player", "player_up_1.png");
		up2 = gamePanel.utils.getImage("player", "player_up_2.png");
		down1 = gamePanel.utils.getImage("player", "player_down_1.png");
		down2 = gamePanel.utils.getImage("player", "player_down_2.png");
		left1 = gamePanel.utils.getImage("player", "player_left_1.png");
		left2 = gamePanel.utils.getImage("player", "player_left_2.png");
		right1 = gamePanel.utils.getImage("player", "player_right_1.png");
		right2 = gamePanel.utils.getImage("player", "player_right_2.png");
		
	}
	
	// method to handle picking up items, passing in the index in the gamePanel.items array
	public void pickUpItem(int itemIndex){
		// 999 is the default index with no items
		if(itemIndex != 999){
			// get the type of item
			ItemType type = gamePanel.items[itemIndex].itemType;
			switch (type) {
				// if the item is a weapon, set the current weapon to the picked up item
				case WEAPON:
					currentWeapon = gamePanel.items[itemIndex];
					break;
				default:
					break;
			}
			// set the space in the current items index to null to remove it
			gamePanel.items[itemIndex] = null;
		}
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
			checkCollision();
			int collidedItemIndex = gamePanel.collisionHandler.checkItem(this, true);
			pickUpItem(collidedItemIndex);
			
			if(currentWeapon != null){
				System.out.println(currentWeapon.name);
			}

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
	
}
