package entities;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

// parent class for all entities, including items, the player and NPCs
public class Entity {

	// current entity state
	int worldX, worldY;
	public int speed;
	public String direction;
	int health;
	int maxHealth;
	
	public Hitbox hitbox;
	public boolean collision;
	
	// entity images
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
	
	// variables for cycling through images
	public int spriteCounter = 0;
	public int spriteNum = 1;
	
	// method to load images from a file into a variable
	public BufferedImage getEntityImage(String entityName, String imagePath) {
		try {
			return ImageIO.read(getClass().getResourceAsStream("/" + entityName + "/" + imagePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
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
	
	public int getWorldY() {
		return this.worldY;
	}
	
}
