package item;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class ITEM_Sword extends Item {
    
    public ITEM_Sword(GamePanel gp){

        super(gp);

        name = "normal sword";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/items/sword_normal.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

}
