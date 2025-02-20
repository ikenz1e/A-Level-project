package AI;

import entities.Entity;
import main.GamePanel;

public class EnemyAI {

    GamePanel gamePanel;
    Entity enemy;

    public EnemyAI(GamePanel gp, Entity enemy){
        
        this.gamePanel = gp;
        this.enemy = enemy;
    }

    public int getTargetCol(){
        int targetCol = 0;
        // depending on the range of the enemy
        // if it is 0, the enemy must collide with the player to attack them
        if(enemy.range == 0){
            // flank around behind the player
            switch (gamePanel.player.direction) {
                case "up":
                    targetCol = gamePanel.player.getCol();
                    break;
                case "down":
                    targetCol = gamePanel.player.getCol();
                    break;
                case "left":
                    targetCol = gamePanel.player.getCol() + 1;
                    break;
                case "right":
                    targetCol = gamePanel.player.getCol() - 1;
                    break;
                default:
                    break;
            }
        }

        return targetCol;
    }

    public int getTargetRow(){
        int targetRow = 0;
        // depending on the range of the enemy
        // if it is 0, the enemy must collide with the player to attack them
        if(enemy.range == 0){
            switch (gamePanel.player.direction) {
                case "up":
                    targetRow = gamePanel.player.getRow() + 1;
                    break;
                case "down":
                    targetRow = gamePanel.player.getRow() - 1;
                    break;
                case "left":
                    targetRow = gamePanel.player.getRow();
                    break;
                case "right":
                    targetRow = gamePanel.player.getRow();
                    break;
                default:
                    break;
            }
        }
        return targetRow;
    }

}
