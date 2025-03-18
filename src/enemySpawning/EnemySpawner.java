package enemySpawning;

import java.util.Random;

import main.GamePanel;

public class EnemySpawner {

    GamePanel gamePanel;
    // array of all the potential enemies that can be spawned by this specific spawner
    Enemy[] spawnableEnemies = new Enemy[5];
    // use of random from the Random library
    Random random = new Random();
    // spawner strength decides what enemies can be spawned
    public int spawnerStrength;
    // the cooldown for how often enemies can be spawned
    private int maxSpawnCooldown = 240;
    private int spawnCooldown = maxSpawnCooldown;

    // the position of the spawner in terms of columns and rows
    public int col, row;

    // constructor, assings the instance of the gamepanel and strength attribute
    public EnemySpawner(GamePanel gp, int strength){
        gamePanel = gp;
        spawnerStrength = strength;
        setupSpawner();
    }

    // attempts to spawn an enemy, if unable to spawn one, decrements the counter, other conditions handled in EnemyHandler class
    // called in the main update() method in the GamePanel
    public void attemptSpawn(){
        if (spawnCooldown == 0){
            int num = random.nextInt(spawnerStrength);
            gamePanel.enemyHandler.spawnEnemy(spawnableEnemies[num], col, row);
            spawnCooldown = maxSpawnCooldown;
        }else{
            spawnCooldown--;
        }
       
    }

    // method used to setup the spawner, called in the constructor when an object is instantiated
    private void setupSpawner(){
        for(int i = 0; i < spawnerStrength; i++){
            spawnableEnemies[i] = gamePanel.enemyHandler.enemies[i];
        }
    }



}
