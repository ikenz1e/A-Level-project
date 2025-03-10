package item;

import main.GamePanel;
import utils.ItemType;

public class ITEM_Chestplate extends Item {

    public ITEM_Chestplate(GamePanel gp) {
        super(gp);
        // assigning the name attribute
        name = "chestplate";
        // assigning the image attribute for drawing
        image = gamePanel.utils.getImage("items", "chestplate.png");
        itemType = ItemType.CHESTPLATE;
        damage = 10;
    }
    
    public void use(){
        gamePanel.player.inventory.useUtil(this);
    }

}
