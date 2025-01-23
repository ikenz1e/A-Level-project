package entities;

import main.GamePanel;

// inherits from entity
public class NPC_Wizard extends Entity {
    
    // constructure, passing in the gamepanel 
    public NPC_Wizard(GamePanel gp){
        // super class constructor to assign gamepanel
        super(gp);
        // load the images
        getImages();

        this.hitbox = new Hitbox(8, 16, 32, 32);
        this.onPath = true;

        // set the default direction and speed
        direction = "down";
        speed = 1;
    }

    // assign all the image attributes to their images, using the getEntityImage method inherited from Entity
    public void getImages() {
        up1 = getEntityImage("npc", "wizard_up_1.png");
        up2 = getEntityImage("npc", "wizard_up_2.png");
        down1 = getEntityImage("npc", "wizard_down_1.png");
        down2 = getEntityImage("npc", "wizard_down_2.png");
        left1 = getEntityImage("npc", "wizard_left_1.png");
        left2 = getEntityImage("npc", "wizard_left_2.png");
        right1 = getEntityImage("npc", "wizard_right_1.png");
        right2 = getEntityImage("npc", "wizard_right_2.png");
    }

    // setAction, inherited from Entity, overriding
    public void setAction(){
        if(onPath){
            int goalCol = gamePanel.player.getCol();
            int goalRow = gamePanel.player.getRow();

            searchPath(goalCol, goalRow);
        }
    }

    // update method
    public void update() {

        gamePanel.collisionHandler.checkTiles(this);
        
        setAction();
        if(!collision) {
            spriteCounter++;
			// every 10 frames a sprite cycle should be run
			if(spriteCounter > 10) {
				if(spriteNum == 1) {
					spriteNum = 2;
				}else {
					spriteNum = 1;
				}
				spriteCounter = 0;
			}
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
