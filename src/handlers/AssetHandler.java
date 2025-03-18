package handlers;

import enemySpawning.EnemySpawner;
import entities.NPC_Wizard;
import item.ITEM_Apple;
import item.ITEM_Apple_Green;
import item.ITEM_Chestplate;
import item.ITEM_Chestplate_Gold;
import item.ITEM_Helmet;
import item.ITEM_Helmet_Gold;
import item.ITEM_Pants;
import item.ITEM_Pants_Gold;
import item.ITEM_Shield;
import item.ITEM_Shield_Blue;
import item.ITEM_Sword;
import item.ITEM_Sword_Green;
import item.Item;
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

    public void setSpawner() {
        gamePanel.spawners[0] = new EnemySpawner(gamePanel, 2);
        gamePanel.spawners[0].col = 4;
        gamePanel.spawners[0].row = 4;

        gamePanel.spawners[1] = new EnemySpawner(gamePanel, 3);
        gamePanel.spawners[1].col = 9;
        gamePanel.spawners[1].row = 7;
    }

    // setting the items on the map
    public void setItem(){

        gamePanel.items[0] = new ITEM_Sword(gamePanel);
        gamePanel.items[0].worldX = gamePanel.getTileSize() * 2;
        gamePanel.items[0].worldY = gamePanel.getTileSize() * 3;

        gamePanel.items[1] = new ITEM_Sword_Green(gamePanel);
        gamePanel.items[1].worldX = gamePanel.getTileSize() * 12;
        gamePanel.items[1].worldY = gamePanel.getTileSize() * 9;

        gamePanel.items[2] = new ITEM_Apple(gamePanel);
        gamePanel.items[2].worldX = gamePanel.getTileSize() * 6;
        gamePanel.items[2].worldY = gamePanel.getTileSize() * 4;

        gamePanel.items[3] = new ITEM_Apple_Green(gamePanel);
        gamePanel.items[3].worldX = gamePanel.getTileSize() * 12;
        gamePanel.items[3].worldY = gamePanel.getTileSize() * 10;

        gamePanel.items[4] = new ITEM_Helmet(gamePanel);
        gamePanel.items[4].worldX = gamePanel.getTileSize() * 3;
        gamePanel.items[4].worldY = gamePanel.getTileSize() * 6;

        gamePanel.items[5] = new ITEM_Chestplate(gamePanel);
        gamePanel.items[5].worldX = gamePanel.getTileSize() * 3;
        gamePanel.items[5].worldY = gamePanel.getTileSize() * 7;

        gamePanel.items[6] = new ITEM_Pants(gamePanel);
        gamePanel.items[6].worldX = gamePanel.getTileSize() * 5;
        gamePanel.items[6].worldY = gamePanel.getTileSize() * 7;

        gamePanel.items[7] = new ITEM_Helmet_Gold(gamePanel);
        gamePanel.items[7].worldX = gamePanel.getTileSize() * 8;
        gamePanel.items[7].worldY = gamePanel.getTileSize() * 10;

        gamePanel.items[8] = new ITEM_Chestplate_Gold(gamePanel);
        gamePanel.items[8].worldX = gamePanel.getTileSize() * 9;
        gamePanel.items[8].worldY = gamePanel.getTileSize() * 10;

        gamePanel.items[9] = new ITEM_Pants_Gold(gamePanel);
        gamePanel.items[9].worldX = gamePanel.getTileSize() * 10;
        gamePanel.items[9].worldY = gamePanel.getTileSize() * 10;

        gamePanel.items[10] = new ITEM_Shield(gamePanel);
        gamePanel.items[10].worldX = gamePanel.getTileSize() * 4;
        gamePanel.items[10].worldY = gamePanel.getTileSize() * 4;

        gamePanel.items[11] = new ITEM_Shield_Blue(gamePanel);
        gamePanel.items[11].worldX = gamePanel.getTileSize() * 13;
        gamePanel.items[11].worldY = gamePanel.getTileSize() * 4;
    }


    // used whenever adding an item to the map from another class
    public void addItem(Item item, int col, int row){
        for(int i = 0; i < gamePanel.items.length; i++){
            if(gamePanel.items[i] == null){
                gamePanel.items[i] = item;
                gamePanel.items[i].worldX = col * gamePanel.getTileSize();
                gamePanel.items[i].worldY = row * gamePanel.getTileSize();
                return;
            }
        }
    }

}
