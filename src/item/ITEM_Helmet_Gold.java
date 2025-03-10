package item;

import main.GamePanel;
import utils.ItemType;

public class ITEM_Helmet_Gold extends Item {

    public ITEM_Helmet_Gold(GamePanel gp) {
        super(gp);
        name = "gold helmet";
        image = gamePanel.utils.getImage("items", "helmet_gold.png");
        itemType = ItemType.HELMET;
        damage = 15;
    }

    public void use(){
        gamePanel.player.inventory.useUtil(this);
    }
    
}
