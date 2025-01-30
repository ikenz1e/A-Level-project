package entities;

import java.awt.Rectangle;

public class Hitbox extends Rectangle{

	public int x, y;
	public int width, height;
	
	// constructor, passing in the x and y (relative the to entity), the width and the height
	public Hitbox(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public void setX(int newX){
		x = newX;
	}

	public void setY(int newY){
		y = newY;
	}
	
}
