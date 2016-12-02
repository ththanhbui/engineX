package resources;

import java.awt.Graphics2D;
import org.joml.Vector2f;
import engine.StaticSprite;

public class Enemy extends StaticSprite {
	private float mSpeed = 0.1f;
	private int mHealth=10;
	private boolean mAlive=true;
	private Player mPlayer;
	
	public Enemy(Player player) {
		super("res/enemy.png");
		mPlayer = player;
	}
	
	public int getHealth() {
		return mHealth;
	}
	
	public Vector2f getDirection() {
		Vector2f playerPosition = new Vector2f(mPlayer.getX(), mPlayer.getY());
		Vector2f enemyPosition = new Vector2f(getX(), getY());
		Vector2f direction = playerPosition.sub(enemyPosition);
		return direction;
	}
	
	@Override
	public void update(int deltaTime) {
		if (!mAlive){
			setX((int) (Math.random() * 4000 - 2000));
			setY((int) (Math.random() * 4000 - 2000));
			mPlayer.increaseScore();
			mAlive = true;
			mHealth = 10;
		}
		Vector2f direction = getDirection();
		int deltaX = 0, deltaY = 0;
		if (direction.y<0) deltaY = -1;
		if (direction.x<0) deltaX = -1;
		if (direction.y>0) deltaY = 1;
		if (direction.x>0) deltaX = 1;
		deltaX *= Math.round((float) deltaTime * mSpeed);
		deltaY *= Math.round((float) deltaTime * mSpeed);
		setX(getX() + deltaX);
		setY(getY() + deltaY);
		playerHit();
	}
	
	@Override
	public void render(Graphics2D g) {
		g.drawImage(getImage(), getX(), getY(), null);
	}
	
	private void playerHit() {
		Vector2f direction = getDirection();
		if (direction.length()<Math.sqrt(Math.pow(mPlayer.getWidth(),2) + Math.pow(mPlayer.getHeight(),2)))
			mPlayer.decrementHealth(1);
	}
	
	public void decrementHealth(int x) {
		if (mHealth<x) mAlive=false;
		else mHealth -= x;
	}
}