package main;
import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		
		// setup the JFrame window
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("game");
		
		// create an instance of the GamePanel
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);
		
		window.pack(); // changes the size of the window to fit its sub components (the game panel)
		
		// window will always open in the middle of the screen
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		// start the game thread, essentially starts the game
		gamePanel.startGameThread();
		
	}

}
