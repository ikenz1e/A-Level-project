package item;

import main.GamePanel;
import utils.ItemType;

public class ITEM_Apple extends Item {

    public ITEM_Apple(GamePanel gp) {
        super(gp);

        name = "apple";

        image = gamePanel.utils.getImage("items", "apple.png");
        itemType = ItemType.CONSUMABLE;
    }
    
    public void use(){
        gamePanel.player.addHealth(4);
        gamePanel.player.inventory.removeItem(this);
    }

}
