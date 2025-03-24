package UI.Buttons;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;

import main.GamePanel;

public class Button {
    
    public int x, y, width, height;
    Color colour;
    String text;
    Font font;

    GamePanel gamePanel;
    
    // constructor assigns all attributes and creates font
    public Button(GamePanel gp, int x, int y, int width, int height, Color color, String text, int fontSize){
        gamePanel = gp;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = text;
        this.colour = color;
        
        font = new Font("SansSerif", Font.PLAIN, fontSize);
    }

    public void draw(Graphics2D g2){
        g2.setColor(colour);
        g2.drawRect(x, y, width, height);
        g2.setFont(font);
        int fontX, fontY;
        FontMetrics metrics = g2.getFontMetrics(font);
        // align the text in the center of the button
        fontX = x + (width/2 - metrics.stringWidth(text)/2);
        fontY = y + metrics.getHeight();

        g2.drawString(text, fontX, fontY);
    }

    // check for mouse click and on mouse click call the click action method
    public void update(){
        
        if(gamePanel.inputHandler.mouseClick){
            if(mouseIsInButton()){
                performClickAction();
            }
            gamePanel.inputHandler.mouseClick = false;
        }
    }

    // any functionality for when the button is clicked should go in here
    public void performClickAction(){
        System.out.println("click");
    }

    // used to calculate if the mouse cursor is within the button
    public boolean mouseIsInButton(){
        int mouseX, mouseY;
        Point mousePoint = MouseInfo.getPointerInfo().getLocation();
        mouseX = (int) mousePoint.getX() - (int) gamePanel.getLocationOnScreen().getX();
        mouseY = (int) mousePoint.getY() - (int) gamePanel.getLocationOnScreen().getY();
        
        
        
        return ((mouseX > x && mouseX < x + width) && (mouseY > y && mouseY < y + height));
    }

}
