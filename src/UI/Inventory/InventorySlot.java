package UI.Inventory;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import item.Item;

public class InventorySlot {

    // the size of a slot on the screen in px
    public static int size = 48;

    // attributes that each slot will have, each will have an item, the number of items and the maximum number of items.
    private Item item;
    private int count;
    private int maxCount;

    public int xCoord, yCoord;

    public Color borderColor = new Color(255, 255, 255);

    // method to see if a slot is holding an item
    public boolean hasItem(){
        return item != null;
    }

    // method to return the item being held
    public Item getItem(){
        return item;
    }

    // method to add a new item to an empty slot
    public void addNewItem(Item newItem, int amount){
        item = newItem;
        count = amount;
        maxCount = item.maxStack;
    }

    // method to add an item to a slot that already has an item in
    public int addItem(int amount){
        int leftOver = 0;
        count += amount;
        if(count > maxCount){
            leftOver = count - maxCount;
            count = maxCount;
        }
        return leftOver;
    }

    // method to get the number of items in a slot
    public int getCount(){
        return count;
    }

    // method to draw a slot, takes the x and y coordinates and the instance of Graphics2D
    public void draw(Graphics2D g2, int x, int y){
        // the width and height of the slot
        int width = 48;
        int height = 48;
        xCoord = x;
        yCoord = y;

        // creating a rectangle to be drawn
        Rectangle shape = new Rectangle(xCoord, yCoord, width, height);
        // setting the colour of the outline
        g2.setColor(borderColor);
        // drawing the rectangle
        g2.draw(shape);
        // drawing the item within the slot
        if(hasItem()){
            item.draw(g2, x, y);
            // draw the number of items in this slot
            // create the string to be drawn
            String itemCountString = Integer.toString(count);
            // create the font to draw it in
            Font itemCountFont = new Font("SansSerif", Font.BOLD, 22);
            // set the font
            g2.setFont(itemCountFont);
            // draw the string
            g2.drawString(itemCountString, x + width - 12, y + height);
        }
    }

}
