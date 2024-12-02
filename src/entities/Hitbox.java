package entities;

public class Hitbox{

	private int x, y;
	private int width, height;
	
	// constructor, passing in the x and y (relative the to entity), the width and the height
	public Hitbox(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
}
