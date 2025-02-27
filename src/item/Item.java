package item;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import main.GamePanel;
import utils.ItemType;

public class Item {
    
    // image name and collision status of the item
    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int worldX, worldY;
    public GamePanel gamePanel;
    // hitbox using a rect
    public Rectangle hitbox = new Rectangle(0, 0, 48, 48);
    // default x and y coordinates of the hitbox
    public int hitboxDefaultX = 0;
    public int hitboxDefaultY = 0;
    // the range and damage of the item, mostly used for weapons
    public int range = 1;
    public int damage = 1;
    // itemType of type ItemType enum
    public ItemType itemType;
    public int maxStack;

    // constructor
    public Item(GamePanel gp){
        this.gamePanel = gp;
    }

    // draw method
    public void draw(Graphics2D g2){
        g2.drawImage(image, worldX, worldY, gamePanel.getTileSize(), gamePanel.getTileSize(), null);
    }

    // overloaded method draw, takes x and y coordinates as parameters
    public void draw(Graphics2D g2, int x, int y){
        g2.drawImage(image, x, y, gamePanel.getTileSize(), gamePanel.getTileSize(), null);
    }

    // getCol returns int of the column that the item is held in based on the centre x coordinate
    public int getCol(){
        return (worldX + gamePanel.getTileSize()/2) / gamePanel.getTileSize();
    }

    // getRow returns int of the row that the item is held in based on the centre y coordinate
    public int getRow(){
        return (worldY + gamePanel.getTileSize()/2) / gamePanel.getTileSize();
    }

    public void use(){
        System.out.println("used: " + name);
    }
}
