package item;

import main.GamePanel;
import utils.ItemType;

public class ITEM_Pants_Gold extends Item{

    public ITEM_Pants_Gold(GamePanel gp) {
        super(gp);
        name = "gold pants";
        image = gamePanel.utils.getImage("items", "pants_gold.png");
        itemType = ItemType.PANTS;
        damage = 20;
    }
    
    public void use(){
        Item item = gamePanel.player.inventory.utilArray[gamePanel.player.inventory.PANTS_SLOT].getItem();
        gamePanel.player.inventory.addInvItem(item, 1);
        gamePanel.player.inventory.utilArray[gamePanel.player.inventory.PANTS_SLOT].addNewItem(this, 1);
    }

}
