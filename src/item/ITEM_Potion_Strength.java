package item;

import effects.DamageBoost;
import main.GamePanel;
import utils.ItemType;

public class ITEM_Potion_Strength extends Item{

  public ITEM_Potion_Strength(GamePanel gp) {
    super(gp);

    name = "strength potion";

    image = gamePanel.utils.getImage("items", "potion_strenght.png");
    itemType = ItemType.CONSUMABLE;
  }

  public void use(){
    gamePanel.player.addEffect(new DamageBoost(gamePanel));
    gamePanel.player.inventory.removeItem(this);
  }
  
}
