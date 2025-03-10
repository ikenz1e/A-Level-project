package item;

import main.GamePanel;
import utils.ItemType;

public class ITEM_Pants_Gold extends Item{

    public ITEM_Pants_Gold(GamePanel gp) {
        super(gp);
        name = "gold pants";
        image = gamePanel.utils.getImage("items", "pants_gold.png");
        itemType = ItemType.PANTS;
        damage = 17;
    }
    
    public void use(){
        gamePanel.player.inventory.useUtil(this);
    }

}
