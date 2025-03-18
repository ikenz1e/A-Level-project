package entities;

import java.awt.Rectangle;

import UI.Inventory.Inventory;
import UI.Inventory.InventorySlot;
import effects.Effect;
import handlers.InputHandler;
import item.Item;
import main.GamePanel;
import utils.ItemType;

public class Player extends Entity{

	// get instances of the gamePanel and inputHandler
	InputHandler inputHandler;
	
	public Item currentWeapon;
	public int attackCooldown = 0;
	public Rectangle attackHitbox;
	public int damageCooldown;
	public int maxDamageCooldown;
	public int attackTimer;
	public float defence;

	public Inventory inventory;
	public Effect[] effects = new Effect[5];

	// constructor, passing in gamePanel and inputHandler
	public Player(GamePanel gp) {
		// assigning gamePanel and inputHandler
		super(gp);
		this.inputHandler = gamePanel.inputHandler;
		
		inventory = new Inventory(gamePanel, this);

		this.hitbox = new Rectangle(8, 16, 32, 32);
		hitboxDefaultX = (int)this.hitbox.getX();
		hitboxDefaultY = (int)this.hitbox.getY();
		
		// methods to be run on object instantiation
		setDefaultValues();
		getPlayerImages();
	}
	
	// override the default setDefaultValues() method
	public void setDefaultValues() {
		worldX = 100;
		worldY = 100;
		screenX = worldX;
		screenY = worldY;
		speed = 4;
		direction = "down";
		health = 20;
		maxHealth = 20;
		maxDamageCooldown = 30;
		damageCooldown = maxDamageCooldown;
	}
	
	// assign all the images to their corresponding variables using the getEntityImage() method from parent class
	public void getPlayerImages() {
		up1 = gamePanel.utils.getImage("player", "player_up_1.png");
		up2 = gamePanel.utils.getImage("player", "player_up_2.png");
		down1 = gamePanel.utils.getImage("player", "player_down_1.png");
		down2 = gamePanel.utils.getImage("player", "player_down_2.png");
		left1 = gamePanel.utils.getImage("player", "player_left_1.png");
		left2 = gamePanel.utils.getImage("player", "player_left_2.png");
		right1 = gamePanel.utils.getImage("player", "player_right_1.png");
		right2 = gamePanel.utils.getImage("player", "player_right_2.png");
		
		attackUp1 = gamePanel.utils.getImage("player", "player_attack_up_1.png");
		attackUp2 = gamePanel.utils.getImage("player", "player_attack_up_2.png");
		attackDown1 = gamePanel.utils.getImage("player", "player_attack_down_1.png");
		attackDown2 = gamePanel.utils.getImage("player", "player_attack_down_2.png");
		attackLeft1 = gamePanel.utils.getImage("player", "player_attack_left_1.png");
		attackLeft2 = gamePanel.utils.getImage("player", "player_attack_left_2.png");
		attackRight1 = gamePanel.utils.getImage("player", "player_attack_right_1.png");
		attackRight2 = gamePanel.utils.getImage("player", "player_attack_right_2.png");
	}

	// overwritten method from Entity class for taking damage
	public boolean takeDamage(int amount){
		// damage cooldown to prevent the player taking damage every frame
		if(damageCooldown == 0){
			// reset the cooldown
			damageCooldown = maxDamageCooldown;
			// including defence
			amount *= 1 - defence;
			// calculate if the player has died or not, returning true/false depending on this
			if((health-amount) <= 0){
				health = 0;
				return true;
			}else{
				health -= amount;
				return false;
			}
		// if the cooldown has not finished, the player cannot take damage and therefore cannot die so return false
		}else{
			return false;
		}
		
	}
	
	// method to handle picking up items, passing in the index in the gamePanel.items array
	public void pickUpItem(int itemIndex){
		// 999 is the default index with no items
		if(itemIndex != 999){
			inventory.addInvItem(gamePanel.items[itemIndex], 1);

			// set the space in the current items index to null to remove it
			gamePanel.items[itemIndex] = null;
		}
	}

	// method to attack
	public void attack(){
		// if the player has a weapon and the cooldown has cooled down
		if(currentWeapon != null && attackCooldown == 0){
			attacking = true;
			attackHitbox = new Rectangle(worldX, worldY, 1, 1);
			switch(direction){
				case "up":
					attackHitbox.height = currentWeapon.range * gamePanel.getTileSize();
					attackHitbox.y -= attackHitbox.height;
					attackHitbox.width = gamePanel.getTileSize();
					break;
				case "down":
					attackHitbox.height = currentWeapon.range * gamePanel.getTileSize();
					attackHitbox.y += gamePanel.getTileSize();
					attackHitbox.width = gamePanel.getTileSize();
					break;
				case "left":
					attackHitbox.width = currentWeapon.range * gamePanel.getTileSize();
					attackHitbox.x -= attackHitbox.width;
					attackHitbox.height = gamePanel.getTileSize();
					break;
				case "right":
					attackHitbox.width = currentWeapon.range * gamePanel.getTileSize();
					attackHitbox.x += gamePanel.getTileSize();
					attackHitbox.height = gamePanel.getTileSize();
					break;
			}
			// get the index of the enemy that was attacked
			int enemyIndex = gamePanel.collisionHandler.checkAttackCollision(attackHitbox);
			// validate that the index returned is less than the length of the enemies array
			if(enemyIndex < gamePanel.enemyHandler.spawnedEnemies.length){
				// run the takeDamage method and check if it returns true, the enemy dies
				if(gamePanel.enemyHandler.spawnedEnemies[enemyIndex].takeDamage(currentWeapon.damage)){
					gamePanel.enemyHandler.defeatEnemy(enemyIndex);
				}
			}
			attackCooldown = 30;
		}
	}

	public void updateEquipment(){
		// start at 0
		defence = 0;
		// iterate through the utility array
		for(InventorySlot slot : inventory.utilArray){
			// if the item isnt a weapon, add its damage (defence amount) to the defence
			if(slot.hasItem() && slot.getItem().itemType != ItemType.WEAPON){
				defence += slot.getItem().damage;
			}
		}

		// divide the defence by 100 to create a percentage
		defence /= 100;
	}

	// update function to be run in the game loop
	public void update() {
		if(damageCooldown > 0){
			damageCooldown--;
		}

		for(Effect effect : effects){
			if(effect != null){
				effect.useEffect();
			}
		}

		if(attacking){
			attackTimer++;
			if(attackTimer > 10){
				spriteNum = 2;
			}else{
				spriteNum = 1;
			}
		}else{
			attackTimer = 0;
		}

		// if a movement key is pressed, cycle through the sprites
		if(inputHandler.wPressed || inputHandler.aPressed || inputHandler.sPressed || inputHandler.dPressed) {
			spriteCounter++;
			// every 10 frames a sprite cycle should be run
			if(!attacking){
				if(spriteCounter > 10) {
					if(spriteNum == 1) {
						spriteNum = 2;
					}else {
						spriteNum = 1;
					}
					spriteCounter = 0;
				}
			}
			
			
			// player movement based on user input, changing direction
			if(inputHandler.wPressed) {
				direction = "up";
			}
			else if(inputHandler.sPressed) {
				direction = "down";
			}
			else if(inputHandler.aPressed) {
				direction = "left";
			}
			else if(inputHandler.dPressed) {
				direction = "right";
			}	
			
			// check tile collisions
			checkCollision();
			// check item collision
			int collidedItemIndex = gamePanel.collisionHandler.checkItem(this, true);
			pickUpItem(collidedItemIndex);

			// if there is no collision, the player can move
			if(!collision) {
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
			}
			screenX = worldX;
			screenY = worldY;
		}	

		// set the attack cooldown to be 0 if attackCooldown - 1 is less than 0
		attackCooldown = Math.max(0, attackCooldown - 1);
		if(attackCooldown == 0){
			attacking=false;
		}

		// if the player presses the attack key, attack
		if(gamePanel.inputHandler.qPressed){
			attack();
		}

	}

	public void addHealth(int amount){
		// set the health to either the health + amount of the maximum health if health + amount > maxHealth
		health = Math.min(health + amount, maxHealth);
	}

	public void addEffect(Effect effect){
		for(int i = 0; i < effects.length; i++){
			if(effects[i] == null){
				effects[i] = effect;
				break;
			}
		}
	}

	public void removeEffect(Effect removedEffect){
		for(int i = 0; i < effects.length; i++){
			if(effects[i] == removedEffect){
				effects[i] = null;
			}
		}
	}
	
}
