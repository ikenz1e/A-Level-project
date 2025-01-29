package utils;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Utils {

    public BufferedImage getImage(String type, String fileName){
        try {
            return ImageIO.read(getClass().getResourceAsStream("/"+type+"/"+fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
}
