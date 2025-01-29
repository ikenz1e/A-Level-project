package handlers;

import entities.NPC_Wizard;
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
        gamePanel.npc[0] = new NPC_Wizard(gamePanel);
        gamePanel.npc[0].worldX = gamePanel.getTileSize() * 4;
        gamePanel.npc[0].worldY = gamePanel.getTileSize() * 5;
    }

    // setting the items on the map
    public void setItem(){
        gamePanel.items[0] = new ITEM_Sword(gamePanel);
        gamePanel.items[0].worldX = gamePanel.getTileSize() * 6;
        gamePanel.items[0].worldY = gamePanel.getTileSize() * 7;
    }

}
