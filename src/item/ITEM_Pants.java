package item;

import main.GamePanel;
import utils.ItemType;

public class ITEM_Pants extends Item {

    public ITEM_Pants(GamePanel gp) {
        super(gp);
        // assigning the name attribute
        name = "pants";
        // assigning the image attribute for drawing
        image = gamePanel.utils.getImage("items", "pants.png");
        itemType = ItemType.PANTS;
        damage = 7;
    }

    public void use(){
        gamePanel.player.inventory.useUtil(this);
    }
    
}
