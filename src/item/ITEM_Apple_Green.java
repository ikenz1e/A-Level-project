package item;

import main.GamePanel;
import utils.ItemType;

public class ITEM_Apple_Green extends Item{

  public ITEM_Apple_Green(GamePanel gp) {
    super(gp);

      name = "green apple";

      image = gamePanel.utils.getImage("items", "apple_green.png");
      itemType = ItemType.FOOD;
  }

  public void use(){
    gamePanel.player.addHealth(8);
    gamePanel.player.inventory.removeItem(this);
  }
  
}
