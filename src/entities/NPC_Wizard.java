package entities;

import main.GamePanel;

public class NPC_Wizard extends Entity {
    
    public NPC_Wizard(GamePanel gp){
        super(gp);

        getImages();
        
        direction = "down";
        speed = 1;
    }

    public void getImages() {
        up1 = getEntityImage("npc", "wizard_up_1.png");
        up2 = getEntityImage("npc", "wizard_up_2.png");
        down1 = getEntityImage("npc", "wizard_down_2.png");
        down2 = getEntityImage("npc", "wizard_down_2.png");
        left1 = getEntityImage("npc", "wizard_left_1.png");
        left2 = getEntityImage("npc", "wizard_left_2.png");
        right1 = getEntityImage("npc", "wizard_right_1.png");
        right2 = getEntityImage("npc", "wizard_right_2.png");
    }

    public void update() {
        gamePanel.collisionHandler.checkTiles(this);
    }

}
