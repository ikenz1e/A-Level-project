package UI.Inventory;

import item.Item;
import main.GamePanel;
import utils.ItemType;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import entities.Entity;

public class Inventory {
    /*
        *  -- how inventory slots work --
        * slot0: weapon slot
        * slot1: shield slot
        * slot2: helmet slot
        * slot3: chestplate slot
        * slot4: pants slot
    */
    // creating constants for the utility slots
    private final int WEAPON_SLOT = 0;
    private final int SHIELD_SLOT = 1;
    private final int HELMET_SLOT = 2;
    private final int CHESTPLATE_SLOT = 3;
    private final int PANTS_SLOT = 4;

    // main array for storing all items in the inventory 2D array[column][row]
    private InventorySlot[][] invArray;
    private InventorySlot[] utilArray;
    // the size of the grid will be 5x5
    private int size = 5;
    private int utilSize = 5;
    // this is the size of the actual inventory UI, the size of each slot muktipled by the number of slots
    private int width = InventorySlot.size * size;
    private int height = InventorySlot.size * size;

    private int xCoord;
    private int yCoord;
    private int currentSelectedSlotRow;
    private int currentSelectedSlotCol;

    InventorySlot selectedSlot;

    Entity owner;

    private Color backgroudColour = new Color(10, 10, 10, 225);
    
    GamePanel gamePanel;

    // constructor, used to initliase the array and all the elements within it
    public Inventory(GamePanel gp, Entity owner){
        this.owner = owner;
        invArray = new InventorySlot[size][size];
        utilArray = new InventorySlot[utilSize];

        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                invArray[i][j] = new InventorySlot();
            }
        }

        for (int i = 0; i < utilSize; i ++){
            utilArray[i] = new InventorySlot();
        }

        gamePanel = gp;

        selectedSlot = invArray[0][0];
        currentSelectedSlotRow = 0;
        currentSelectedSlotCol = 0;
    }

    // method to add an item to the inventory
    public int addInvItem(Item item, int amount){
        // initial variables in his section for how many are left over, which slot is empty and which can be added to
        int leftOver = 0;

        InventorySlot emptySlot = null;
        InventorySlot itemSlot = null;
        if(item.itemType != ItemType.WEAPON || (item.itemType == ItemType.WEAPON && utilArray[WEAPON_SLOT].hasItem())){
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
        }else{
             /*
            *  -- how inventory slots work -- (repeat)
            * slot0: weapon slot
            * slot1: shield slot
            * slot2: helmet slot
            * slot3: chestplate slot
            * slot4: pants slot
            */
            int slotTracker = 0;
            for(InventorySlot slot : utilArray){
                if(slotTracker == WEAPON_SLOT && item.itemType == ItemType.WEAPON){
                    slot.addNewItem(item, 1);
                    gamePanel.player.currentWeapon = item;
                    break;
                }
                slotTracker++;
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

    public void selectSlot(int row, int col){
        
        currentSelectedSlotCol = col;
        currentSelectedSlotRow = row;
        
        if(currentSelectedSlotCol < 0){
            currentSelectedSlotCol = size-1;
        }else if(currentSelectedSlotCol >= size){
            currentSelectedSlotCol = 0;
        }

        if(currentSelectedSlotRow < 0){
            currentSelectedSlotRow = size-1;
        } else if(currentSelectedSlotRow >= size){
            currentSelectedSlotRow = 0;
        }
        selectedSlot = invArray[currentSelectedSlotCol][currentSelectedSlotRow];
    }

    public void useSelectedItem(){
        if(selectedSlot.hasItem()){
            if (selectedSlot.getItem().itemType == ItemType.WEAPON){
                Item weapon = selectedSlot.getItem();
                selectedSlot.addNewItem(gamePanel.player.currentWeapon, 1);
                gamePanel.player.currentWeapon = weapon;
                utilArray[WEAPON_SLOT].addNewItem(weapon, 1);
            }else{
                selectedSlot.getItem().use();
            }
        }
    }

    public void update(){
        if(gamePanel.inputHandler.upPressed && !gamePanel.inputHandler.keyPressed){
            selectSlot(currentSelectedSlotRow, currentSelectedSlotCol-1);
            gamePanel.inputHandler.keyPressed = true;
        }
        if(gamePanel.inputHandler.downPressed && !gamePanel.inputHandler.keyPressed){
            selectSlot(currentSelectedSlotRow, currentSelectedSlotCol+1);
            gamePanel.inputHandler.keyPressed = true;
        }
        if(gamePanel.inputHandler.leftPressed && !gamePanel.inputHandler.keyPressed){
            selectSlot(currentSelectedSlotRow-1, currentSelectedSlotCol);
            gamePanel.inputHandler.keyPressed = true;
        }
        if(gamePanel.inputHandler.rightPressed && !gamePanel.inputHandler.keyPressed){
            selectSlot(currentSelectedSlotRow+1, currentSelectedSlotCol);
            gamePanel.inputHandler.keyPressed = true;
        }
        if(gamePanel.inputHandler.ePressed && !gamePanel.inputHandler.keyPressed){
            useSelectedItem();
            gamePanel.inputHandler.keyPressed = true;

        }
    }

    public void draw(Graphics2D g2){
        // draw background, solid black square on the right side of the screen
        // 10 pixels of padding from the edge of the window
        int paddingX = 10;
        // the x coordinate should be the width of the screen minus the width of the background minus the padding 
        xCoord = gamePanel.getScreenWidth() - width - paddingX;
        // the y coordinate should be the difference between the height of the screen and the height of the background divided by 2
        yCoord = (gamePanel.getScreenHeight() - height)/2;
        
        // inventory
        drawInv(g2);
        
        // utility section
        drawUtilSection(g2);

        Rectangle selectedSlotRect = new Rectangle(selectedSlot.xCoord, selectedSlot.yCoord, InventorySlot.size, InventorySlot.size);
        g2.setColor(new Color(240, 240, 240, 100));
        g2.fill(selectedSlotRect);
    }

    public void drawInv(Graphics2D g2){
        Rectangle invBackground = new Rectangle(xCoord, yCoord, width, height);
        g2.setColor(backgroudColour);
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

    private void drawUtilSection(Graphics2D g2){
        // setting up variables for util section transform
        int paddingX = 10;
        int utilHeight = InventorySlot.size * utilSize;
        int utilWidth = InventorySlot.size;
        int utilX = paddingX;
        int utilY = (gamePanel.getScreenHeight() - utilHeight) / 2;
        int slotX = utilX;
        int slotY = utilY;
        // draw the background
        Rectangle utilBackground = new Rectangle(utilX, utilY, utilWidth, utilHeight);
        g2.setColor(backgroudColour);
        g2.fill(utilBackground);

        // draw the slots
        for(InventorySlot slot : utilArray){
            slot.draw(g2, slotX, slotY);
            slotY += InventorySlot.size;
        }
    }

}
