package item;

import main.GamePanel;
import utils.ItemType;

public class ITEM_Sword_Green extends Item{

    public ITEM_Sword_Green(GamePanel gp) {
        // inherited constructor, passing in the gamePanel
        super(gp);
        // assigning the name attribute
        name = "green sword";
        // assigning the image attribute for drawing
        image = gamePanel.utils.getImage("items", "sword_green.png");
        itemType = ItemType.WEAPON;
        damage = 10;
    }

    public void use(){
        Item item = gamePanel.player.inventory.utilArray[gamePanel.player.inventory.WEAPON_SLOT].getItem();
        gamePanel.player.inventory.addInvItem(item, 1);
        gamePanel.player.inventory.utilArray[gamePanel.player.inventory.WEAPON_SLOT].addNewItem(this, 1);
    }
    
}
