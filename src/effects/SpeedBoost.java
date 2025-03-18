package effects;

import main.GamePanel;

public class SpeedBoost extends Effect{

  private int originalSpeed = gamePanel.player.speed;

  public SpeedBoost(GamePanel gp) {
    super(gp);
    duration = 180;
  }

  public void useEffect(){
    duration--;

    gamePanel.player.speed = 5;
    if(duration == 0){
      gamePanel.player.removeEffect(this);
      gamePanel.player.speed = originalSpeed;
    }
  }
  
}
