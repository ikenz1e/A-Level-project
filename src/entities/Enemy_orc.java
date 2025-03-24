package entities;

import java.awt.Rectangle;

import AI.EnemyAI;
import AI.EnemyState;
import main.GamePanel;

public class Enemy_orc extends Entity{

    // cooldown for selecting a random direction
    private EnemyAI brain = new EnemyAI(gamePanel, this);
    
    private int moveCountdown = 60;

    public Enemy_orc(GamePanel gp) {
        super(gp);
    }

    public void setDefaultValues(){
        this.hitbox = new Rectangle(8, 16, 32, 32);
        this.onPath = true;
        speed = 2;
        health = 150;
        maxHealth = 150;
        attackDamage = 7;
        screenX = worldX;
        screenY = worldY;
        direction = "down";
        viewDistance = 4;
        range = 2;
        aggressiveness = 0.8f;
        getImages();
    }

    public void getImages(){
        up1 = gamePanel.utils.getImage("enemies", "orc_up_1.png");
        up2 = gamePanel.utils.getImage("enemies", "orc_up_2.png");
        down1 = gamePanel.utils.getImage("enemies", "orc_down_1.png");
        down2 = gamePanel.utils.getImage("enemies", "orc_down_2.png");
        left1 = gamePanel.utils.getImage("enemies", "orc_left_1.png");
        left2 = gamePanel.utils.getImage("enemies", "orc_left_2.png");
        right1 = gamePanel.utils.getImage("enemies", "orc_right_1.png");
        right2 = gamePanel.utils.getImage("enemies", "orc_right_2.png");
    }

    // setAction used to change direction
    public void setAction(){
        currentState = brain.getCurrentState();
        if(onPath){
            if(currentState != EnemyState.IDLE){
                int goalCol = brain.getTargetCol();
                int goalRow = brain.getTargetRow();

                searchPath(goalCol, goalRow);
            }else{
                if(moveCountdown == 0){
                    moveCountdown = 60;
                    int num = (int) (Math.random() * 100);
                    if(num > 75){
                        direction = "up";
                    }else if(num > 50){
                        direction = "down";
                    }else if (num > 25){
                        direction = "left";
                    }else{
                        direction = "right";
                    }
                }else{
                    moveCountdown--;
                }
            }

            
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
