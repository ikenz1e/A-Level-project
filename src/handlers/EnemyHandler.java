package handlers;

import enemySpawning.Enemy;
import entities.Enemy_bat;
import entities.Enemy_orc;
import entities.Enemy_slime;
import entities.Entity;
import main.GamePanel;

public class EnemyHandler {
    
    // all the potential enemies that can be spawned
    public Enemy[] enemies = new Enemy[5];
    // all the current enemies that have been spawned
    public Entity[] spawnedEnemies = new Entity[10];
    GamePanel gamePanel;

    private int nextFree = 0;

    // constructor sets the instance of the gamepanel and calls setEnemies to set up the handler.
    public EnemyHandler(GamePanel gp){
        gamePanel = gp;
        setEnemies();
    }

    // method used to handle spawning an enemy, called when the attemptSpawn() method is run in the EnemySpawner class
    public void spawnEnemy(Enemy enemyToSpawn, int col, int row){
        // if there is no free space, do not add an enemy
        if(nextFree != -1){
            // calculate which enemy should be added
            Entity newEnemy = null;
            switch (enemyToSpawn) {
                case SLIME:
                    newEnemy = new Enemy_slime(gamePanel);
                    break;
                case BAT:
                    newEnemy = new Enemy_bat(gamePanel);
                    break;
                case ORC:
                    newEnemy = new Enemy_orc(gamePanel);
                    break;
                default:
                    break;
            }
            // add the enemy at the correct position
            spawnedEnemies[nextFree] = newEnemy;
            spawnedEnemies[nextFree].worldX = col * gamePanel.getTileSize();
            spawnedEnemies[nextFree].worldY = row * gamePanel.getTileSize();
        }
        calcNextFree();
    }
    
    // method used to handle defeating an enemy
    public void defeatEnemy(int index){
        if(spawnedEnemies[index] != null){
            // spawnedEnemies[index].defeat(); TODO: add
            spawnedEnemies[index] = null;
        }
    }

    // method used to set all the enemies in the enemies array.
    private void setEnemies(){
        enemies[0] = Enemy.SLIME;
        enemies[1] = Enemy.BAT;
        enemies[2] = Enemy.ORC;
    }

    // method to calculate the next free position in the spawnedEnemies array
    private void calcNextFree(){
        for(int i = 0; i < spawnedEnemies.length; i++){
            if (spawnedEnemies[i] == null){
                nextFree = i;
                return;
            }
        }
        // if no slot is found, the nextFree variable is set to -1 to indicate no free space
        nextFree = -1;
    }

}
