package entities;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;

// parent class for all entities, including items, the player and NPCs
public class Entity {

	GamePanel gamePanel;

	// current entity state
	public int worldX, worldY;
	public int speed;
	public String direction;
	int health;
	int maxHealth;
	public boolean onPath = false;

	public Rectangle hitbox;
	public int hitboxDefaultX;
	public int hitboxDefaultY;
	public boolean collision;
	
	// entity images
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
	
	// variables for cycling through images
	public int spriteCounter = 0;
	public int spriteNum = 1;
	
	public Entity(GamePanel gp){
		this.gamePanel = gp;
		setDefaultValues();
	}
	
	// default values in case the setDefaultValues method is not set for a class
	public void setDefaultValues() {
		worldX = 100;
		worldY = 100;
		direction = "down";
		health = 1;
		maxHealth = 1;
	}
	
	public int getWorldX() {
		return this.worldX;
	}

	public int getCol(){
		// the x coordinate + half a tile length gives the centre x of the entity dividing by the length of a tile to find the column
		return (this.worldX + gamePanel.getTileSize()/2)/ gamePanel.getTileSize();
	}
	
	public int getRow(){
		// the y coordinate + half a tile height gives the centre y coordinate of the entity, dividing by the height of a tile to find the row
		return (this.worldY + gamePanel.getTileSize()/2) / gamePanel.getTileSize();
	}

	public int getWorldY() {
		return this.worldY;
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

	public void checkCollision(){
		collision = false;
		gamePanel.collisionHandler.checkTiles(this);
	}

	public void searchPath(int goalCol, int goalRow){
		int startCol = (worldX + (int)hitbox.getX()) / gamePanel.getTileSize();
		int startRow = (worldY + (int) hitbox.getY()) / gamePanel.getTileSize();

		gamePanel.pathFinder.setNodes(startCol, startRow, goalCol, goalRow);


		if(gamePanel.pathFinder.search()){
			// next world X and world Y

			int nextX = gamePanel.pathFinder.pathList.get(0).col * gamePanel.getTileSize();
			int nextY = gamePanel.pathFinder.pathList.get(0).row * gamePanel.getTileSize();

			// hitbox position
			int leftX = worldX + (int)hitbox.getX();
			int rightX = worldX + (int)hitbox.getWidth() + (int)hitbox.getX();
			int topY = worldY + (int)hitbox.getY();
			int bottomY = worldY + (int)hitbox.getHeight() + (int)hitbox.getY();

			if(topY > nextY && leftX >= nextX && rightX < leftX + gamePanel.getTileSize()){
				direction = "up";
			}
			else if(topY < nextY && leftX >= nextX && rightX < leftX + gamePanel.getTileSize()){
				direction = "down";
			}
			else if(topY >= nextY && bottomY < nextY + gamePanel.getTileSize()){
				// can go left or right
				if(leftX > nextX){
					direction = "left";
				}
				if (leftX < nextX){
					direction = "right";
				}
			}
			else if (topY < nextY && leftX > nextX){
				// up or left
				direction = "up";
				checkCollision();
				if (collision){
					direction = "left";
				}
			}
			else if (topY > nextY && rightX < nextX){
				// up or right
				direction = "up";
				checkCollision();
				if(collision){
					direction = "right";
				}
			}
			else if (bottomY > nextY && leftX > nextX){
				// down or left
				direction = "down";
				checkCollision();
				if(collision){
					direction = "left";
				}
			}
			else if (bottomY < nextY && rightX < nextX){
				// down or right
				direction = "down";
				checkCollision();
				if(collision){
					direction = "right";
				}
			}
		}
	}

	public void setAction(){

	}

	public void update(){
		setAction();

		// if there is no collision, the entity can move
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
