package resources;

import engine.StaticSprite;

public class Bullet extends StaticSprite {
	private float mSpeed = 1.0f;
	private float mDirection;
	
	public Bullet(float direction) {
		super("res/bullet.png");
		mDirection = direction;
	}
	
	public Bullet(float direction, float speed) {
		this(direction);
		mSpeed = speed;
	}
	
	@Override
	public void update(int deltaTime) {
		int deltaX = (int) Math.round((float) deltaTime * mSpeed * Math.sin(mDirection));
		int deltaY = (int) Math.round((float) deltaTime * mSpeed * -Math.cos(mDirection));
		setX(getX() + deltaX);
		setY(getY() + deltaY);
	}
}
