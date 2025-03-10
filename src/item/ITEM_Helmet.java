package item;

import main.GamePanel;
import utils.ItemType;

public class ITEM_Helmet extends Item {

    public ITEM_Helmet(GamePanel gp) {
        super(gp);
        // assigning the name attribute
        name = "helmet";
        // assigning the image attribute for drawing
        image = gamePanel.utils.getImage("items", "helmet.png");
        itemType = ItemType.HELMET;
        damage = 10;
    }

    public void use(){
        Item item = gamePanel.player.inventory.utilArray[gamePanel.player.inventory.HELMET_SLOT].getItem();
        gamePanel.player.inventory.addInvItem(item, 1);
        gamePanel.player.inventory.utilArray[gamePanel.player.inventory.HELMET_SLOT].addNewItem(this, 1);
    }
    
}
