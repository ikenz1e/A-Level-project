package effects;

import main.GamePanel;

public class SpeedBoost extends Effect{

  // used to return values to their original state
  private int originalSpeed = gamePanel.player.speed;

  // constructor, using inherited constructor from Effect class for gp and setting the duration
  public SpeedBoost(GamePanel gp) {
    super(gp);
    duration = 180;
  }

  public void useEffect(){
    duration--;
    // the player's boosted speed is 6 (original is 4)
    gamePanel.player.speed = 6;
    // when the duration runs out, remove the effect
    if(duration == 0){
      gamePanel.player.removeEffect(this);
      gamePanel.player.speed = originalSpeed;
    }
  }
  
}
