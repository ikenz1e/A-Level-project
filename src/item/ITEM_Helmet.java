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
        damage = 5;
    }

    public void use(){
        gamePanel.player.inventory.useUtil(this);
    }
    
}
