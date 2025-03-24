package UI.Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import UI.Buttons.Button;
import UI.Buttons.RetryButton;
import UI.Buttons.StartGameButton;
import main.GamePanel;

public class GameUI {

    GamePanel gamePanel;

    BufferedImage heart_full;  
    BufferedImage heart_half;
    BufferedImage heart_empty;

    Button[] buttons = new Button[10];

    Font mainFont = new Font("SansSerif", Font.BOLD, 40);

    // constructor used to assign all attributes and get images.
    public GameUI(GamePanel gp){
        gamePanel = gp;
        getImages();
    }

    // using the getImage() method from the utility class, called in the constructor
    private void getImages(){
        heart_full = gamePanel.utils.getImage("UI", "heart_full.png"); 
        heart_half = gamePanel.utils.getImage("UI", "heart_half.png"); 
        heart_empty = gamePanel.utils.getImage("UI", "heart_empty.png"); 
    }

    // used to clear the buttons array, this should be called before loading any menu with buttons
    public void clearButtons(){
        for(int i = 0; i < buttons.length; i++){
            buttons[i] = null;
        }
    }
    
    // called in gamePanel for all states that include a menu that have a button
    public void updateButtons(){
        for(Button button : buttons){
            if(button != null){
                button.update();
            }
        }
    }

    // used for drawing just the health bar
    public void drawHealthBar(Graphics2D g2){
        // variables needed, the health and max health
        int health = gamePanel.player.health;
        int max = gamePanel.player.maxHealth;
        // coordinates of where to draw the images
        int startX = 10;
        int y = 10;
        // draw an empty heart for every heart there should be 
        for(int i=0; i < max; i+=2){
            g2.drawImage(heart_empty, startX + (gamePanel.getTileSize() * i/2), y, gamePanel.getTileSize(), gamePanel.getTileSize(), null);
        }

        // the number of full hearts required using integer division
        int numFull = health / 2;
        for(int i = 0; i < numFull; i++){
            g2.drawImage(heart_full, startX + (gamePanel.getTileSize() * i),y, gamePanel.getTileSize(), gamePanel.getTileSize(), null);
        }
        // if the health is an odd number, add a half heart on the end
        if(health % 2 != 0){
            g2.drawImage(heart_half, startX + (gamePanel.getTileSize() * numFull), y, gamePanel.getTileSize(), gamePanel.getTileSize(), null);
        }
    }

    // used to draw the main menu, called when the game starts
    public void drawMainMenu(Graphics2D g2){
        // load the correct buttons into the array
        loadMainMenu();

        // draw the background
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, gamePanel.getScreenWidth(), gamePanel.getScreenHeight());

        // draw the title, main menu
        g2.setFont(mainFont);
        String menuTitle = "Main Menu";

        int y = 50;
        FontMetrics fontMetrics = g2.getFontMetrics(mainFont);
        int x = gamePanel.getScreenWidth()/2 - fontMetrics.stringWidth(menuTitle)/2;
        g2.setColor(Color.BLUE);
        g2.drawString(menuTitle, x, y);

        // draw the buttons
        for(Button button : buttons){
            if(button != null){
                button.draw(g2);
            }
        }
    }

    private void loadMainMenu(){
        // empty buttons
        clearButtons();
        // add relevent buttons to the buttons array
        buttons[0] = new StartGameButton(gamePanel, 100, gamePanel.getScreenHeight()/2, 150, 50, Color.BLUE, "start", 25);
        buttons[0].x = gamePanel.getScreenWidth()/2 - buttons[0].width/2;
    }

    // used to draw/use the pause menu, called when the user presses the escape key
    public void drawPauseMenu(Graphics2D g2){
        // load the buttons into the buttons array
        loadPauseMenu();
        // draw the pause tite
        g2.setFont(mainFont);
        String text = "pause";
        FontMetrics metrics = g2.getFontMetrics(mainFont);
        int x = gamePanel.getScreenWidth()/2 - metrics.stringWidth(text)/2;
        int y = gamePanel.getScreenHeight()/2 - metrics.getHeight()/2;
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        // draw the buttons
        for(Button button : buttons){
            if(button != null){
                button.draw(g2);
            }
        }
    }

    // called in the drawPauseMenu() method 
    public void loadPauseMenu(){
        // empty buttons
        clearButtons();
        // add relevent buttons and set their positions
        buttons[0] = new StartGameButton(gamePanel, 100, gamePanel.getScreenHeight()/2, 150, 50, Color.WHITE, "play", 25);
        buttons[0].x = gamePanel.getScreenWidth()/2 - buttons[0].width/2;
        buttons[0].y = gamePanel.getScreenHeight()/2 - buttons[0].height + gamePanel.getTileSize();
    }
    
    // called when the player dies, in the death state set in the player class
    public void drawDeathMenu(Graphics2D g2){
        // load any relevant buttons
        loadDeathMenu();
        // draw the background
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, gamePanel.getScreenWidth(), gamePanel.getScreenHeight());
        // draw the text
        g2.setColor(Color.BLUE);
        String deathText = "YOU DIED";
        FontMetrics metrics = g2.getFontMetrics(mainFont);
        int x = gamePanel.getScreenWidth()/2 - metrics.stringWidth(deathText)/2;
        int y = gamePanel.getScreenHeight()/4;
        g2.setFont(mainFont);
        g2.drawString(deathText, x, y);

        // draw the buttons
        for(Button button : buttons){
            if(button != null){
                button.draw(g2);
            }   
        }
    }

    // store the required buttons in the buttons array
    public void loadDeathMenu(){
        // empty buttons
        clearButtons();

        // create an instance of a retry button and its position
        buttons[0] = new RetryButton(gamePanel, 0, 0, 150, 50, Color.BLUE, "restart", 22);
        buttons[0].x = gamePanel.getScreenWidth()/2 - buttons[0].width/2;
        buttons[0].y = gamePanel.getScreenHeight()*3/4;
    }
}
