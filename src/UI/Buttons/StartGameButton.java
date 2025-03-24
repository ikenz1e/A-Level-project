package UI.Buttons;

import java.awt.Color;

import main.GamePanel;
import utils.State;

// used for any button to change the state to the game state 
public class StartGameButton extends Button{

    // inherited constructor from Button class
    public StartGameButton(GamePanel gp, int x, int y, int width, int height, Color color, String text, int fontSize) {
        super(gp, x, y, width, height, color, text, fontSize);
    }

    // overriding the performClickAction() method from the Button class
    @Override
    public void performClickAction(){
        gamePanel.stateHandler.changeState(State.GAME);
    }
    
}
