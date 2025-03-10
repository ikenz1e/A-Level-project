package handlers;

import entities.Enemy_orc;
import entities.Enemy_slime;
import entities.NPC_Wizard;
import item.ITEM_Apple;
import item.ITEM_Chestplate;
import item.ITEM_Chestplate_Gold;
import item.ITEM_Helmet;
import item.ITEM_Helmet_Gold;
import item.ITEM_Pants;
import item.ITEM_Pants_Gold;
import item.ITEM_Sword;
import main.GamePanel;

public class AssetHandler {
    
    // gamepanel as an attribute
    GamePanel gamePanel;

    // constructor - takes the gamepanel as a parameter
    public AssetHandler(GamePanel gp) {
        // assign the gamepanel attribute
        this.gamePanel = gp;
    }

    // setting the NPCs on the map
    public void setNPC() {
        gamePanel.npcs[0] = new NPC_Wizard(gamePanel);
        gamePanel.npcs[0].worldX = gamePanel.getTileSize() * 4;
        gamePanel.npcs[0].worldY = gamePanel.getTileSize() * 5;
    }

    // setting the enemies on the map
    public void setEnemy() {
        // slime enemy
        gamePanel.enemies[0] = new Enemy_slime(gamePanel);
        gamePanel.enemies[0].worldX = gamePanel.getTileSize() * 7;
        gamePanel.enemies[0].worldY = gamePanel.getTileSize() * 7;

        // orc enemy
        gamePanel.enemies[1] = new Enemy_orc(gamePanel);
        gamePanel.enemies[1].worldX = gamePanel.getTileSize() * 5;
        gamePanel.enemies[1].worldY = gamePanel.getTileSize() * 6;
    }

    // setting the items on the map
    public void setItem(){
        // sword item
        gamePanel.items[0] = new ITEM_Sword(gamePanel);
        gamePanel.items[0].worldX = gamePanel.getTileSize() * 6;
        gamePanel.items[0].worldY = gamePanel.getTileSize() * 7;

        gamePanel.items[1] = new ITEM_Apple(gamePanel);
        gamePanel.items[1].worldX = gamePanel.getTileSize() * 7;
        gamePanel.items[1].worldY = gamePanel.getTileSize() * 7;

        gamePanel.items[2] = new ITEM_Helmet(gamePanel);
        gamePanel.items[2].worldX = gamePanel.getTileSize() * 8;
        gamePanel.items[2].worldY = gamePanel.getTileSize() * 7;

        gamePanel.items[3] = new ITEM_Chestplate(gamePanel);
        gamePanel.items[3].worldX = gamePanel.getTileSize() * 9;
        gamePanel.items[3].worldY = gamePanel.getTileSize() * 7;

        gamePanel.items[4] = new ITEM_Pants(gamePanel);
        gamePanel.items[4].worldX = gamePanel.getTileSize() * 10;
        gamePanel.items[4].worldY = gamePanel.getTileSize() * 7;

        gamePanel.items[5] = new ITEM_Helmet_Gold(gamePanel);
        gamePanel.items[5].worldX = gamePanel.getTileSize() * 8;
        gamePanel.items[5].worldY = gamePanel.getTileSize() * 8;

        gamePanel.items[6] = new ITEM_Chestplate_Gold(gamePanel);
        gamePanel.items[6].worldX = gamePanel.getTileSize() * 9;
        gamePanel.items[6].worldY = gamePanel.getTileSize() * 8;

        gamePanel.items[7] = new ITEM_Pants_Gold(gamePanel);
        gamePanel.items[7].worldX = gamePanel.getTileSize() * 10;
        gamePanel.items[7].worldY = gamePanel.getTileSize() * 8;
    }

}
