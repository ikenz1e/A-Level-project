package handlers;

import entities.Entity;
import main.GamePanel;

public class CollisionHandler {

	GamePanel gamePanel;
	
	
	// constructor, initilise the game panel attribute
	public CollisionHandler (GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}
	
	// check tiles method, takes the entity that collisions are being checked for
	public void checkTiles(Entity entity) {
		/// get the world coordinates of each corner of the hitbox
		int leftX = entity.getWorldX() + entity.hitbox.getX();
		int rightX = entity.getWorldX() + entity.hitbox.getX() + entity.hitbox.getWidth();
		int topY = entity.getWorldY() + entity.hitbox.getY();
		int bottomY = entity.getWorldY() + entity.hitbox.getY() + entity.hitbox.getHeight();
		
		// turns the coordinates into rows and columns
		int leftCol = leftX / gamePanel.getTileSize();
		int rightCol = rightX / gamePanel.getTileSize();
		int topRow = topY / gamePanel.getTileSize();
		int bottomRow = bottomY / gamePanel.getTileSize();
		
		// will only need to check up to 2 tiles per check
		int tileNum1, tileNum2;
		// switch on the current direction of the entity
		switch(entity.direction) {
		case "up":
			// get the top row, as the row the player will be in next frame
			topRow = (topY - entity.speed) / gamePanel.getTileSize();
			// assign the 2 tile numbers to the 2 tiles above the player
			tileNum1 = gamePanel.tileManager.mapTileNum[leftCol][topRow];
			tileNum2 = gamePanel.tileManager.mapTileNum[rightCol][topRow];
			break;
		case "down":
			// get the bottom row, as the row the player will be in next frame
			bottomRow = (bottomY + entity.speed) / gamePanel.getTileSize();
			// assign the 2 tile numbers to the 2 tiles below the player
			tileNum1 = gamePanel.tileManager.mapTileNum[leftCol][bottomRow];
			tileNum2 = gamePanel.tileManager.mapTileNum[rightCol][bottomRow];
			break;
		case "left":
			// get the left column, as the column the player will be in next frame
			leftCol = (leftX - entity.speed) / gamePanel.getTileSize();
			// assign the 2 tile numbers to the 2 tiles on the left of the player
			tileNum1 = gamePanel.tileManager.mapTileNum[leftCol][topRow];
			tileNum2 = gamePanel.tileManager.mapTileNum[leftCol][bottomRow];
			break;
		case "right":
			// get the right column, as the column the player will be in next frame
			rightCol = (rightX + entity.speed) / gamePanel.getTileSize();
			// assign the 2 tile numbers to the 2 tiles on the right of the player
			tileNum1 = gamePanel.tileManager.mapTileNum[rightCol][topRow];
			tileNum2 = gamePanel.tileManager.mapTileNum[rightCol][bottomRow];
			break;
		default:
			tileNum1 = 0;
			tileNum2 = 0;
			break;
		}
		// if any of the tiles in index tileNum1 or tileNum2 are collidable, set the collision attribute of the entity to true
		if(gamePanel.tileManager.tileArray[tileNum1].collidable || gamePanel.tileManager.tileArray[tileNum2].collidable) {
			entity.collision = true;
		}
	}
	
}
