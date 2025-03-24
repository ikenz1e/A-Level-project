package entities;

import java.awt.Rectangle;

import AI.EnemyAI;
import AI.EnemyState;
import main.GamePanel;

public class Enemy_bat extends Entity{


    // cooldown for selecting a random direction
    private int moveCountdown = 60;

    // creating an instance of the EnemyAI class
    private EnemyAI brain = new EnemyAI(gamePanel, this); 

    public Enemy_bat(GamePanel gp) {
        super(gp);
    }

    // set the defaults for all necessary attributes and get images
    public void setDefaultValues(){
        this.hitbox = new Rectangle(8, 16, 32, 32);
        this.onPath = true;
        speed = 4;
        health = 5;
        maxHealth = 5;
        attackDamage = 2;
        screenX = worldX;
        screenY = worldY;
        direction = "down";
        viewDistance = 2;
        aggressiveness = 1.5f;
        range = 1;
        getImages();
    }

    // load all images into attributes
    public void getImages(){
        up1 = gamePanel.utils.getImage("enemies", "bat_down_1.png");
        up2 = gamePanel.utils.getImage("enemies", "bat_down_2.png");
        down1 = gamePanel.utils.getImage("enemies", "bat_down_1.png");
        down2 = gamePanel.utils.getImage("enemies", "bat_down_2.png");
        left1 = gamePanel.utils.getImage("enemies", "bat_down_1.png");
        left2 = gamePanel.utils.getImage("enemies", "bat_down_2.png");
        right1 = gamePanel.utils.getImage("enemies", "bat_down_1.png");
        right2 = gamePanel.utils.getImage("enemies", "bat_down_2.png");
    }

    // setAction, called first in update() method
    public void setAction(){
        // calculate state
        currentState = brain.getCurrentState();
        // if pathfinding
        if(onPath){
            // if not idle, calulate a path
            if(currentState != EnemyState.IDLE){
                int goalCol = brain.getTargetCol();
                int goalRow = brain.getTargetRow();

                searchPath(goalCol, goalRow);
            // if idle, move randomly
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
