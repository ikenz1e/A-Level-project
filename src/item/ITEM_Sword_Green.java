package item;

import main.GamePanel;
import utils.ItemType;

public class ITEM_Sword_Green extends Item{

    public ITEM_Sword_Green(GamePanel gp) {
        super(gp);
        name = "green sword";
        image = gamePanel.utils.getImage("items", "sword_green.png");
        itemType = ItemType.WEAPON;
        damage = 10;
    }

    
    
}
