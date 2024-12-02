package handlers;

import entities.NPC_Wizard;
import main.GamePanel;

public class AssetHandler {
    
    GamePanel gamePanel;

    public AssetHandler(GamePanel gp) {
        this.gamePanel = gp;
    }

    public void setNPC() {
        gamePanel.npc[0] = new NPC_Wizard(gamePanel);
        gamePanel.npc[0].worldX = gamePanel.getTileSize() * 4;
        gamePanel.npc[0].worldY = gamePanel.getTileSize() * 5;
    }

}
