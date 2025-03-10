package AI;

import java.util.Random;

import entities.Entity;
import main.GamePanel;

public class EnemyAI {

    GamePanel gamePanel;
    Entity enemy;

    Random random = new Random();

    // constructor, takes gamepanel and the enemy
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

        // the score is the likeliness of the enemy attacking the player or not in a given situation
        float score;
        // calulate the health as a percentage
        float healthPercentage = ((float) enemy.health / (float) enemy.maxHealth);
        score = healthPercentage * enemy.aggressiveness;
        // if the enemy is within attack range of the enemy
        if (distToPlayer < enemy.range){
            score += score/4;
        // if the player is out of range of the enemy's attacks
        }else{
            score -= score/4;
        }
        // if the enemy deals more damage than the player
        if(gamePanel.player.currentWeapon == null || enemy.attackDamage + enemy.aggressiveness * 10 > gamePanel.player.currentWeapon.damage){
            score += score*2;
        // if the player deals more or the same damage
        }else{
            score -= score/2;
        }
        
        // set the score to be between 0 and 1
        if(score > 1){
            score = 1f;
        }else if(score < 0){
            score = 0f;
        }
        // set the state depending on the score calculated
        if(score > 0.4){
            return EnemyState.ATTACKING;
        }else{
            return EnemyState.RETREATING;
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
