package item;

import effects.SpeedBoost;
import main.GamePanel;
import utils.ItemType;

public class ITEM_Potion_Speed extends Item{
  
public ITEM_Potion_Speed(GamePanel gp) {
    super(gp);

    name = "speed potion";

    image = gamePanel.utils.getImage("items", "potion_speed.png");
    itemType = ItemType.CONSUMABLE;
  }

  public void use(){
    gamePanel.player.addEffect(new SpeedBoost(gamePanel));
    gamePanel.player.inventory.removeItem(this);
  }

}
