package item;

import main.GamePanel;
import utils.ItemType;

public class ITEM_Chestplate_Gold extends Item{

    public ITEM_Chestplate_Gold(GamePanel gp) {
        super(gp);
        // assigning the name attribute
        name = "gold chestplate";
        // assigning the image attribute for drawing
        image = gamePanel.utils.getImage("items", "chestplate_gold.png");
        itemType = ItemType.CHESTPLATE;
        damage = 20;
    }

    public void use(){
        Item item = gamePanel.player.inventory.utilArray[gamePanel.player.inventory.CHESTPLATE_SLOT].getItem();
        gamePanel.player.inventory.addInvItem(item, 1);
        gamePanel.player.inventory.utilArray[gamePanel.player.inventory.CHESTPLATE_SLOT].addNewItem(this, 1);
    }

}
