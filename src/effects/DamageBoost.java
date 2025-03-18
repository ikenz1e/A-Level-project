package effects;

import item.Item;
import main.GamePanel;

public class DamageBoost extends Effect{

  private int originalDamage = gamePanel.player.currentWeapon.damage;
  private Item originalItem = gamePanel.player.currentWeapon;

  public DamageBoost(GamePanel gp) {
    super(gp);
    duration = 180;
  }

  public void useEffect(){
    duration--;
    if(gamePanel.player.currentWeapon != originalItem){
      originalItem = gamePanel.player.currentWeapon;
      originalDamage = originalItem.damage;
    }
    gamePanel.player.currentWeapon.damage = originalDamage * 2;

    if(duration == 0){
      gamePanel.player.removeEffect(this);
      gamePanel.player.currentWeapon.damage = originalDamage;
    }
  }
  
}
