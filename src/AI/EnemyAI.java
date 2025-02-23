package AI;

import java.util.Random;

import entities.Entity;
import main.GamePanel;

public class EnemyAI {

    GamePanel gamePanel;
    Entity enemy;

    Random random = new Random();

    public EnemyAI(GamePanel gp, Entity enemy){
        
        this.gamePanel = gp;
        this.enemy = enemy;
    }

    public EnemyState getCurrentState(){
        int distToPlayer = getPythagoreanDistanceToPlayer();
        // if the player is out of view range of the enemy
        if(distToPlayer > enemy.viewDistance){
            return EnemyState.IDLE;
        }
        // if the player is in range, calculate a state

        // calculate the enemies health as a percentage
        int healthPercentage = (int) (((float) enemy.health / (float) enemy.maxHealth) * 100);
        // if the enemy is within attack range of the enemy
        if (distToPlayer < enemy.range){
            // if the health is above 25%, continue attacking
            if(healthPercentage >= 25){
                return EnemyState.ATTACKING;
            // if the health is below 25%, retreat
            }else{
                return EnemyState.RETREATING;
            }
        // if the player is out of range of the enemy's attacks
        }else{
            // if the health is above 50%, continue attacking
            if(healthPercentage >= 50){
                return EnemyState.ATTACKING;
            // if the health is below 50%, retreat
            }else{
                return EnemyState.RETREATING;
            }
        }
        
    }

    public int getTargetCol(){
        // if the enemy is attacking the player, set its target to be the player
        if(enemy.currentState == EnemyState.ATTACKING){
            return gamePanel.player.getCol();
        }else if(enemy.currentState == EnemyState.RETREATING){
            int col = 0;
            // find a column away from the player
            // split the where the player is to find a section with the player 
            // and one with the enemy
            if(enemy.getCol() == gamePanel.player.getCol()){
                // if they are in the same tile, select a random tile 
                col = random.nextInt(gamePanel.getMaxScreenCol());
            }
            if(enemy.getCol() < gamePanel.player.getCol()){
                // select a random column to the left of the player
                col = random.nextInt(gamePanel.player.getCol());
            }else{
                // select a random tile to the right of the player
                int diff = gamePanel.getMaxScreenCol() - gamePanel.player.getCol();
                col = gamePanel.player.getCol() + random.nextInt(diff);
            }
            return col;
        }else{
            return 0;
        }   
    }

    public int getTargetRow(){
        // if the enemy is attacking the player, set its target to be the player
        if(enemy.currentState == EnemyState.ATTACKING){
            return gamePanel.player.getRow();
        }else if(enemy.currentState == EnemyState.RETREATING){
            int row = 0;
            // find a row away from the player
            // depending on if the enemy is above, below or in the same row as the player
            if(enemy.getRow() == gamePanel.player.getRow()){
                row = random.nextInt(gamePanel.getMaxScreenRow());
            }else if (enemy.getRow() < gamePanel.player.getRow()){
                row = random.nextInt(gamePanel.player.getRow());
            }else{
                int diff = gamePanel.getMaxScreenRow() - gamePanel.player.getRow();
                row = gamePanel.player.getRow() + random.nextInt(diff);
            }
            return row;
        }else{
            return 0;
        }
    }

    private int getPythagoreanDistanceToPlayer(){
        // get the distance in x and y (cols and rows)
        int xDistance = enemy.getCol() - gamePanel.player.getCol();
        int yDistance = enemy.getRow() - gamePanel.player.getRow();
        // calulate the distance using pythagoras
        int totalDistance = xDistance * xDistance + yDistance * yDistance;
        totalDistance = (int) Math.sqrt(totalDistance);
        return totalDistance;
    }

}
