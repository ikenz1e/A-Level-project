package handlers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class InputHandler implements KeyListener, MouseListener{

	public boolean wPressed, sPressed, dPressed, aPressed, qPressed, ePressed, escPressed,  upPressed, downPressed, leftPressed, rightPressed;
	
	public int lastKeyPress;

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	// when the user presses a key down
	@Override
	public void keyPressed(KeyEvent e) {
		
		// the key code returned by the event
		int code = e.getKeyCode();
		
		// switch on the code to find if it is a key used in the game
		switch(code) {
		case KeyEvent.VK_W:
			wPressed = true;
			break;
		case KeyEvent.VK_A:
			aPressed = true;
			break;
		case KeyEvent.VK_S:
			sPressed = true;
			break;
		case KeyEvent.VK_D:
			dPressed = true;
			break;
		case KeyEvent.VK_Q:
			qPressed = true;
			break;
		case KeyEvent.VK_E:
			ePressed = true;
			break;
		case KeyEvent.VK_ESCAPE:
			escPressed = true;
			break;
		case KeyEvent.VK_UP:
			upPressed = true;
			break;
		case KeyEvent.VK_DOWN:
			downPressed = true;
			break;
		case KeyEvent.VK_LEFT:
			leftPressed = true;
			break;
		case KeyEvent.VK_RIGHT:
			rightPressed = true;
			break;
		default:
			break;
		}
		
		lastKeyPress = code;

	}

	// the method called when the user releases a key
	@Override
	public void keyReleased(KeyEvent e) {
		// get the keycode returned by the event
		int code = e.getKeyCode();
		
		// switch the key code to see if it is a relevant key
		switch(code) {
		case KeyEvent.VK_W:
			wPressed = false;
			break;
		case KeyEvent.VK_A:
			aPressed = false;
			break;
		case KeyEvent.VK_S:
			sPressed = false;
			break;
		case KeyEvent.VK_D:
			dPressed = false;
			break;
		case KeyEvent.VK_Q:
			qPressed = false;
			break;
		case KeyEvent.VK_E:
			ePressed = false;
			break;
		case KeyEvent.VK_ESCAPE:
			escPressed = false;
			break;
		case KeyEvent.VK_UP:
			upPressed = false;
			break;
		case KeyEvent.VK_DOWN:
			downPressed = false;
			break;
		case KeyEvent.VK_LEFT:
			leftPressed = false;
			break;
		case KeyEvent.VK_RIGHT:
			rightPressed = false;
			break;
		default:
			break;
		}

		lastKeyPress = -1;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

}
