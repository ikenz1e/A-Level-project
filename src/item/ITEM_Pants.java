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
        damage = 10;
    }

    public void use(){
        Item item = gamePanel.player.inventory.utilArray[gamePanel.player.inventory.PANTS_SLOT].getItem();
        gamePanel.player.inventory.addInvItem(item, 1);
        gamePanel.player.inventory.utilArray[gamePanel.player.inventory.PANTS_SLOT].addNewItem(this, 1);
    }
    
}
