package item;

import main.GamePanel;
import utils.ItemType;

public class ITEM_Helmet_Gold extends Item {

    public ITEM_Helmet_Gold(GamePanel gp) {
        super(gp);
        name = "gold helmet";
        image = gamePanel.utils.getImage("items", "helmet_gold.png");
        itemType = ItemType.HELMET;
        damage = 20;
    }

    public void use(){
        Item item = gamePanel.player.inventory.utilArray[gamePanel.player.inventory.HELMET_SLOT].getItem();
        gamePanel.player.inventory.addInvItem(item, 1);
        gamePanel.player.inventory.utilArray[gamePanel.player.inventory.HELMET_SLOT].addNewItem(this, 1);
    }
    
}
