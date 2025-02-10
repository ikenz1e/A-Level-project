package handlers;

import java.awt.Rectangle;

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
		int leftX = (int)(entity.getWorldX() + entity.hitbox.getX());
		int rightX = (int)(entity.getWorldX() + entity.hitbox.getX() + entity.hitbox.getWidth());
		int topY = (int)(entity.getWorldY() + entity.hitbox.getY());
		int bottomY = (int)(entity.getWorldY() + entity.hitbox.getY() + entity.hitbox.getHeight());
		
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

	// method for checking item collisions, takes the entity checking for collisions and if it is a player or not
	public int checkItem(Entity entity, boolean isPlayer){
		
		// initalise the index to be 999
		int index = 999;
		
		// loop through all the items in the items array
		for (int i = 0; i < gamePanel.items.length; i++){
			if(gamePanel.items[i] != null){
				// get entities hitbox pos
				entity.hitbox.x += entity.worldX;
				entity.hitbox.y += entity.worldY;
				// get the items hitbox pos
				gamePanel.items[i].hitbox.x += gamePanel.items[i].worldX;
				gamePanel.items[i].hitbox.y += gamePanel.items[i].worldY;

				switch(entity.direction){
					case "up":
						entity.hitbox.y -= entity.speed;
						break;
					case "down":
						entity.hitbox.y += entity.speed;
						break;
					case "left":
						entity.hitbox.x -= entity.speed;
						break;
					case "right":
						entity.hitbox.x += entity.speed;
						break;
				}

				if(entity.hitbox.intersects(gamePanel.items[i].hitbox) && isPlayer){
					index = i;
				}

				entity.hitbox.x = entity.hitboxDefaultX;
				entity.hitbox.y = entity.hitboxDefaultY;
				gamePanel.items[i].hitbox.x = gamePanel.items[i].hitboxDefaultX;
				gamePanel.items[i].hitbox.y = gamePanel.items[i].hitboxDefaultY;

				}
		}

		return index;
	}

	// method for checking player collisions
	public boolean checkPlayer(Entity entity){
		// create a temporary hitbox for the entity that is collision detecting
		Rectangle tempHitbox = new Rectangle(entity.worldX + entity.hitbox.x, entity.worldY + entity.hitbox.y, entity.hitbox.width, entity.hitbox.height);
		// create a temporary hitbox for the player with updated coordinates
		Rectangle playerHitbox = new Rectangle(gamePanel.player.worldX + gamePanel.player.hitbox.x, gamePanel.player.worldY + gamePanel.player.hitbox.y, gamePanel.player.hitbox.width, gamePanel.player.hitbox.height);		
		// edit the temporary hitbox based on which direction the entity is moving
		switch(entity.direction){
			case "up":
				tempHitbox.y -= entity.speed;
				break;
			case "down":
				tempHitbox.y += entity.speed;
				break;
			case "left":
				tempHitbox.x -= entity.speed;
				break;
			case "right":
				tempHitbox.x += entity.speed;
				break;
			default:
				break;
		}
		// if the hitboxes intersect, a collision has been detected therefore return true, if not return false
		if (tempHitbox.intersects(playerHitbox)){
			return true;
		}else{
			return false;
		}
	}

	public int checkAttackCollision(Rectangle attackHitbox){
		int index = 999;

		for (int i = 0; i < gamePanel.enemies.length; i++){
			if(gamePanel.enemies[i] != null){
				gamePanel.enemies[i].hitbox.x += gamePanel.enemies[i].worldX;
				gamePanel.enemies[i].hitbox.y += gamePanel.enemies[i].worldY;
				if(gamePanel.enemies[i].hitbox.intersects(attackHitbox)){
					index = i;
				}
				gamePanel.enemies[i].hitbox.x = gamePanel.enemies[i].hitboxDefaultX;
				gamePanel.enemies[i].hitbox.y = gamePanel.enemies[i].hitboxDefaultY;
			}
		}

		return index;
	}
	
}
