package engine;

import java.awt.Graphics2D;

/**
 * Represents a drawable game object.
 */

public abstract class GameObject {
	private int mX;
	private int mY;

	public GameObject() {
		mX = mY = 0;
	}

	public int getX() {
		return mX;
	}

	public void setX(int x) {
		mX = x;
	}

	public int getY() {
		return mY;
	}

	public void setY(int y) {
		mY = y;
	}

	public abstract void update(int deltaTime);
	public abstract void render(Graphics2D g);
}