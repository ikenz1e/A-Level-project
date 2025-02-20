package entities;

import java.awt.Rectangle;

import AI.EnemyAI;
import main.GamePanel;

public class Enemy_slime extends Entity {

    // cooldown for selecting a random direction
    private EnemyAI brain = new EnemyAI(gamePanel, this);

    // constructor, use inherited Entity constructor
    public Enemy_slime(GamePanel gp) {
        super(gp);
    }

    // set the defaults for all necessary attributes and get images
    public void setDefaultValues(){
        this.hitbox = new Rectangle(8, 16, 32, 32);
        this.onPath = true;
        speed = 1;
        health = 10;
        maxHealth = 10;
        attackDamage = 5;
        screenX = worldX;
        screenY = worldY;
        direction = "down";
        getImages();
    }

    // load all images into attributes
    public void getImages(){
        up1 = gamePanel.utils.getImage("enemies", "slime_down_1.png");
        up2 = gamePanel.utils.getImage("enemies", "slime_down_2.png");
        down1 = gamePanel.utils.getImage("enemies", "slime_down_1.png");
        down2 = gamePanel.utils.getImage("enemies", "slime_down_2.png");
        left1 = gamePanel.utils.getImage("enemies", "slime_down_1.png");
        left2 = gamePanel.utils.getImage("enemies", "slime_down_2.png");
        right1 = gamePanel.utils.getImage("enemies", "slime_down_1.png");
        right2 = gamePanel.utils.getImage("enemies", "slime_down_2.png");
    }

    // setAction used to change direction
    public void setAction(){
        if(onPath){
            int goalCol = brain.getTargetCol();
            int goalRow = brain.getTargetRow();

            searchPath(goalCol, goalRow);
        }
        
    }

    public void update(){
        collision = false;
        // check collisions with tiles
        gamePanel.collisionHandler.checkTiles(this);
        if(gamePanel.collisionHandler.checkPlayer(this)){
            gamePanel.player.takeDamage(attackDamage);
        }

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
            screenX = worldX;
            screenY = worldY;
        }
    }

}
