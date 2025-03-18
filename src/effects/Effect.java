package effects;

import main.GamePanel;

public class Effect {
  
  public int duration = 0;

  GamePanel gamePanel;

  public Effect(GamePanel gp){
    gamePanel = gp;
  }

  public void useEffect(){
    System.out.println("used effect");
  }

}
