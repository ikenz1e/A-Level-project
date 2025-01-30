package item;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import main.GamePanel;
import utils.ItemType;
import utils.Utils;

public class Item {
    
    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int worldX, worldY;
    public GamePanel gamePanel;
    public Utils utils;
    public Rectangle hitbox = new Rectangle(0, 0, 48, 48);
    public int hitboxDefaultX = 0;
    public int hitboxDefaultY = 0;
    public ItemType itemType;

    public Item(GamePanel gp){
        this.gamePanel = gp;
    }

    public void draw(Graphics2D g2){
        g2.drawImage(image, worldX, worldY, gamePanel.getTileSize(), gamePanel.getTileSize(), null);
    }

    public int getCol(){
        return (worldX + gamePanel.getTileSize()/2) / gamePanel.getTileSize();
    }

    public int getRow(){
        return (worldY + gamePanel.getTileSize()/2) / gamePanel.getTileSize();
    }
}
