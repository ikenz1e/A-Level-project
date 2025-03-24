package UI.Inventory;

import item.Item;
import main.GamePanel;
import utils.ItemType;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Hashtable;

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
    public final int WEAPON_SLOT = 0;
    public final int SHIELD_SLOT = 1;
    public final int HELMET_SLOT = 2;
    public final int CHESTPLATE_SLOT = 3;
    public final int PANTS_SLOT = 4;

    Hashtable<ItemType, Integer> utilSlotIndexes = new Hashtable<ItemType, Integer>();

    // main array for storing all items in the inventory 2D array[column][row]
    private InventorySlot[][] invArray;
    public InventorySlot[] utilArray;
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

        currentSelectedSlotRow = 0;
        currentSelectedSlotCol = 0;
        selectedSlot = invArray[currentSelectedSlotCol][currentSelectedSlotRow];
        
        // assign all keys and values in the utilSlots hash table
        utilSlotIndexes.put(ItemType.WEAPON, WEAPON_SLOT);
        utilSlotIndexes.put(ItemType.SHIELD, SHIELD_SLOT);
        utilSlotIndexes.put(ItemType.HELMET, HELMET_SLOT);
        utilSlotIndexes.put(ItemType.CHESTPLATE, CHESTPLATE_SLOT);
        utilSlotIndexes.put(ItemType.PANTS, PANTS_SLOT);
    }

    // method to add an item to the inventory
    public int addInvItem(Item item, int amount){
        // initial variables in his section for how many are left over, which slot is empty and which can be added to
        int leftOver = 0;

        InventorySlot emptySlot = null;
        InventorySlot itemSlot = null;
        // if the item is not a weapon or it is a weapon and there is an item in the weapon slot
        
         /*
            *  -- how inventory slots work -- (repeat)
            * slot0: weapon slot
            * slot1: shield slot
            * slot2: helmet slot
            * slot3: chestplate slot
            * slot4: pants slot
        */
        int slotTracker = 0;
        boolean added = false;
        for(InventorySlot slot : utilArray){
            if(slotTracker == WEAPON_SLOT && item.itemType == ItemType.WEAPON && !slot.hasItem()){
                slot.addNewItem(item, 1);
                gamePanel.player.currentWeapon = item;
                added = true;
                break;
            }else if (slotTracker == SHIELD_SLOT && item.itemType == ItemType.SHIELD && !slot.hasItem()){
                slot.addNewItem(item, 1);
                gamePanel.player.updateEquipment();
                added = true;
                break;
            }else if (slotTracker == HELMET_SLOT && item.itemType == ItemType.HELMET && !slot.hasItem()){
                slot.addNewItem(item, 1);
                gamePanel.player.updateEquipment();
                added = true;
                break;
            }else if (slotTracker == CHESTPLATE_SLOT && item.itemType == ItemType.CHESTPLATE && !slot.hasItem()){
                slot.addNewItem(item, 1);
                gamePanel.player.updateEquipment();
                added = true;
                break;
            }else if (slotTracker == PANTS_SLOT && item.itemType == ItemType.PANTS && !slot.hasItem()){
                slot.addNewItem(item, 1);
                gamePanel.player.updateEquipment();
                added = true;
                break;
            }
            slotTracker++;
        }
        if(!added){
            // loop through all the slots in the inventory
            for(int i = 0; i < size; i++){
                for(InventorySlot slot : invArray[i]){
                    // if the slot doesnt have an item and an empty slot hasnt already been found, assign the emptySlot to this slot
                    if(!slot.hasItem() && emptySlot == null){
                        emptySlot = slot;
                    }
                    // if the item of this slot is the item being searched for, set the itemSlot variable to this slot
                    if(slot.hasItem() && slot.getItem().name == item.name){
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
        }

        
        // return the number of leftover items for use elsewhere 
        return leftOver;
    }

    public void removeItem(Item item){
        // linear search to find the required item
        for(int i = 0; i < size; i++){
            for (InventorySlot slot : invArray[i]){
                if(slot.hasItem() && slot.getItem() == item){
                    slot.removeItem();
                    return;
                }
            }
        }

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

    // method to select a new slot, takes an int row and int col as parametes
    public void selectSlot(int row, int col){
        
        // set the select slot col/row to the parameter col/row
        currentSelectedSlotCol = col;
        currentSelectedSlotRow = row;
        
        // if the column goes below 0, it has gone too far to the left, set it to the right most column
        if(currentSelectedSlotCol < 0){
            currentSelectedSlotCol = size-1;
        // vice versa for if it goes too far to the right
        }else if(currentSelectedSlotCol >= size){
            currentSelectedSlotCol = 0;
        }

        // if the row goes below zero, set it to the top row
        if(currentSelectedSlotRow < 0){
            currentSelectedSlotRow = size-1;
        // vice versa for if the row goes too high
        } else if(currentSelectedSlotRow >= size){
            currentSelectedSlotRow = 0;
        }
        // set the selected slot to the new slot in the inventory 
        selectedSlot = invArray[currentSelectedSlotCol][currentSelectedSlotRow];
    }

    public void useSelectedItem(){
        // if the slot has an item, use it 
        if(selectedSlot.hasItem()){
            // if the item is a weapon, set it to the current weapon
            if (selectedSlot.getItem().itemType == ItemType.WEAPON){
                Item weapon = selectedSlot.getItem();
                selectedSlot.addNewItem(gamePanel.player.currentWeapon, 1);
                gamePanel.player.currentWeapon = weapon;
                utilArray[WEAPON_SLOT].addNewItem(weapon, 1);
            // if it is a normal item, use it
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

    // will be called in the use() method of item class for utility items
    public void useUtil(Item armourPiece){
        ItemType type = armourPiece.itemType;
        removeItem(armourPiece);
        Item oldArmour = utilArray[utilSlotIndexes.get(type)].getItem();
        addInvItem(oldArmour, 1);
        utilArray[utilSlotIndexes.get(type)].removeItem();
        utilArray[utilSlotIndexes.get(type)].addNewItem(armourPiece, 1);
        gamePanel.player.updateEquipment();
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
        // initialise the first x and y coordinates of slots to be the x and y coordinates of the background
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
