package UI.Inventory;

import item.Item;
import main.GamePanel;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Inventory {
    
    // main array for storing all items in the inventory 2D array[column][row]
    private InventorySlot[][] invArray;
    // the size of the grid will be 5x5
    private int size = 5;
    // this is the size of the actual inventory UI, each slot will be 48pixels
    private int width = 48 * size;
    private int height = 48 * size;
    
    GamePanel gamePanel;

    // constructor, used to initliase the array and all the elements within it
    public Inventory(GamePanel gp){
        invArray = new InventorySlot[size][size];

        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                invArray[i][j] = new InventorySlot();
            }
        }

        gamePanel = gp;
    }

    // method to add an item to the inventory
    public int addInvItem(Item item, int amount){
        // initial variables in his section for how many are left over, which slot is empty and which can be added to
        int leftOver = 0;

        InventorySlot emptySlot = null;
        InventorySlot itemSlot = null;

        // loop through all the slots in the inventory
        for(int i = 0; i < size; i++){
            for(InventorySlot slot : invArray[i]){
                // if the slot doesnt have an item and an empty slot hasnt already been found, assign the emptySlot to this slot
                if(!slot.hasItem() && emptySlot == null){
                    emptySlot = slot;
                }
                // if the item of this slot is the item being searched for, set the itemSlot variable to this slot
                if(slot.getItem() == item){
                    itemSlot = slot;
                }
            }
        }

        // if a slot with an item in it has been found, add this item to the slot
        if(itemSlot != null){
            leftOver = itemSlot.addItem(amount);
        // if a slot with the item was not found, add the item to the empty slot
        }else if(emptySlot != null){
            emptySlot.addNewItem(item, amount);
        }

        // return the number of leftover items for use elsewhere 
        return leftOver;
    }

    // used for testing , will print out all elements in the inventory array 
    public void printInventory(){
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                if(invArray[i][j].hasItem()){
                    System.out.println(invArray[i][j].getItem().name);
                }else{
                    System.out.println("empty");
                }
            }
        }
    }

    public void draw(Graphics2D g2){
        // draw background, solid black square on the right side of the screen
        // 10 pixels of padding from the edge of the window
        int paddingX = 10;
        // the x coordinate should be the width of the screen minus the width of the background minus the padding 
        int xCoord = gamePanel.getScreenWidth() - width - paddingX;
        // the y coordinate should be the difference between the height of the screen and the height of the background divided by 2
        int yCoord = (gamePanel.getScreenHeight() - height)/2;
        Rectangle invBackground = new Rectangle(xCoord, yCoord, width, height);
        g2.setColor(new Color(10, 10, 10, 225));
        g2.fill(invBackground);
        
        // initialise the firsst x and y coordinates of slots to be the x and y coordinates of the background
        int slotX = xCoord;
        int slotY = yCoord;
        // draw the slots - 48x48 squares with white border
        for(int i=0; i < size; i++){
            // loop through all the slots
            for(InventorySlot slot : invArray[i]){
                // draw the slot and the x and y coordinate
                slot.draw(g2, slotX, slotY);
                // increase the x coordinate
                slotX += gamePanel.getTileSize();
            }
            // go back to the start of the x
            slotX = xCoord;
            // increase the y coordinate to go to the next row
            slotY += gamePanel.getTileSize();
        }
    }

}
