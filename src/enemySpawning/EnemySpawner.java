package enemySpawning;

import java.util.Random;

import entities.Entity;
import main.GamePanel;

public class EnemySpawner {

    GamePanel gamePanel;
    Entity[] spawnableEnemies = new Entity[5];
    Random random = new Random();
    public int spawnerStrength;

    public int col, row;

    public EnemySpawner(GamePanel gp, int strength){
        gamePanel = gp;
        spawnerStrength = strength;
        setupSpawner();
    }

    public void spawn(){
        int num = random.nextInt(spawnerStrength);
        gamePanel.enemyHandler.spawnEnemy(spawnableEnemies[num], col, row);
    }

    private void setupSpawner(){
        for(int i = 0; i < spawnerStrength; i++){
            spawnableEnemies[i] = gamePanel.enemyHandler.enemies[i];
        }
    }



}
