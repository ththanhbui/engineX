package engine;

public class Vector2f implements Cloneable {
	private final float mX;
	private final float mY;
	
	public Vector2f() {
		mX = mY = 0.0f;
	}
	
	public Vector2f(float x, float y) {
		mX = x;
		mY = y;
	}
	
	public float getX() {
		return mX;
	}
	
	public float getY() {
		return mY;
	}
}