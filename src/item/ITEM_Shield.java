package item;

import main.GamePanel;
import utils.ItemType;

public class ITEM_Shield extends Item{

  public ITEM_Shield(GamePanel gp) {
    super(gp);
    name = "shield";
    image = gamePanel.utils.getImage("items", "shield_wood.png");
    itemType = ItemType.SHIELD;
    damage = 4;
  }
  
  public void use() {
    gamePanel.player.inventory.useUtil(this);
  }

}
