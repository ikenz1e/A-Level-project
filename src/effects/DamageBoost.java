package effects;

import item.Item;
import main.GamePanel;

public class DamageBoost extends Effect{

  // needed to return to original values after the effect has run out of duration
  private int originalDamage = gamePanel.player.currentWeapon.damage;
  private Item originalItem = gamePanel.player.currentWeapon;

  // constructor to set the duration and gamePanel attributes
  public DamageBoost(GamePanel gp) {
    super(gp);
    duration = 180;
  }

  // useEffect() method run in the Player update method, will apply the effect to the player and remove it when necessary
  public void useEffect(){
    duration--;
    // used incase the player changes their current weapon
    if(gamePanel.player.currentWeapon != originalItem){
      // edit the original item and damage variables accordingly
      originalItem = gamePanel.player.currentWeapon;
      originalDamage = originalItem.damage;
    }
    // the player gets double damage
    gamePanel.player.currentWeapon.damage = originalDamage * 2;

    // when the effect runs out, remove the effect and set the damage back to the original
    if(duration == 0){
      gamePanel.player.removeEffect(this);
      gamePanel.player.currentWeapon.damage = originalDamage;
    }
  }
  
}
