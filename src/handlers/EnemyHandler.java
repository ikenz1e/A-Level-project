package handlers;

import entities.Enemy_orc;
import entities.Enemy_slime;
import entities.Entity;
import main.GamePanel;

public class EnemyHandler {
    
    public Entity[] enemies = new Entity[5];
    public Entity[] spawnedEnemies = new Entity[10];
    GamePanel gamePanel;

    public EnemyHandler(GamePanel gp){
        gamePanel = gp;
        setEnemies();
    }

    public void spawnEnemy(Entity newEnemy, int col, int row){
        for(int i = 0; i < spawnedEnemies.length; i++){
            if (spawnedEnemies[i] != null){
                spawnedEnemies[i] = newEnemy;
                spawnedEnemies[i].worldX = col * gamePanel.getTileSize();
                spawnedEnemies[i].worldY = row * gamePanel.getTileSize();
            }
        }
    }
    
    public void defeatEnemy(int index){
        if(spawnedEnemies[index] != null){
            // spawnedEnemies[index].defeat(); TODO: add
            spawnedEnemies[index] = null;
        }
    }

    private void setEnemies(){
        enemies[0] = new Enemy_slime(gamePanel);
        enemies[1] = new Enemy_orc(gamePanel);
    }

}
