package resources;

import engine.StaticSprite;

public class Bullet extends StaticSprite {
	private float mSpeed = 2.0f;
	private float mDirection;
	private int mColour;
	
	public Bullet(float direction, int colour) {
		super(colour == 0 ? "res/bullet_red.png" :
			  colour == 1 ? "res/bullet_green.png" :
			  colour == 2 ? "res/bullet_blue.png" :
				            "res/bullet_yellow.png" );
		mColour = colour;
		mDirection = direction;
	}
	
	public int getColour() {
		return mColour;
	}
	
	public Bullet(float direction, int colour, float speed) {
		this(direction, colour);
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