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
        gamePanel.player.inventory.useUtil(this);
    }

}
