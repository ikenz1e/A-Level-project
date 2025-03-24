package UI.Buttons;

import java.awt.Color;

import main.GamePanel;
import utils.State;

// used for any button to restart the game from the beginning
public class RetryButton extends Button{

    // inherited constructor from the Button class
    public RetryButton(GamePanel gp, int x, int y, int width, int height, Color color, String text, int fontSize) {
        super(gp, x, y, width, height, color, text, fontSize);
    }

    // overridden method from the button class, called when the user clicks the button
    @Override
    public void performClickAction(){
        gamePanel.restart();
        gamePanel.stateHandler.changeState(State.GAME);
    }
    
}
