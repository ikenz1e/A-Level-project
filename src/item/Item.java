package item;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.GamePanel;
import utils.Utils;

public class Item {
    
    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int worldX, worldY;
    public GamePanel gamePanel;
    public Utils utils;

    public Item(GamePanel gp){
        this.gamePanel = gp;
    }

    public void draw(Graphics2D g2){
        g2.drawImage(image, worldX, worldY, gamePanel.getTileSize(), gamePanel.getTileSize(), null);
    }
}
