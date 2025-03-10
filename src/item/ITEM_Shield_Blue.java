package item;

import main.GamePanel;
import utils.ItemType;

public class ITEM_Shield_Blue extends Item{

  public ITEM_Shield_Blue(GamePanel gp) {
    super(gp);
    name = "blue shield";
    image = gamePanel.utils.getImage("items", "shield_blue.png");
    itemType = ItemType.SHIELD;
    damage = 8;
  }
  
  public void use() {
    gamePanel.player.inventory.useUtil(this);
  }

}
