package resources;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.joml.Vector2f;

import engine.GameApplication;
import engine.GameObject;

public class Player extends GameObject implements KeyListener, MouseMotionListener, MouseListener {
	private BufferedImage[] mImages;
	private int mColour;
	
	private float mSpeed = 0.3f;
	private float mDirection = 0.0f;
	private boolean[] mArrowKeys;
	private GameApplication mGameApplication;
	private int mHealth = 100;
	private boolean mAlive = true;
	private int mScore = 0;
	
	public int getHealth() {
		return mHealth;
	}
	
	private long lastDecrement = 0;
	
	public void decrementHealth(int x) {
		if (System.currentTimeMillis() - lastDecrement < 20) return;
		lastDecrement = System.currentTimeMillis();
		if (mHealth<x) mAlive=false;
		else mHealth-=x;
	}
	
	public void incrementHealth(int x){
		if (mHealth-100<x) mHealth=100;
		else mHealth+=x;
	}
	
	public Player(GameApplication gameApplication, int colour) {
		mImages = new BufferedImage [4];
		
		try {
			mImages[0] = ImageIO.read(new File("res/player_red.png"));
			mImages[1] = ImageIO.read(new File("res/player_green.png"));
			mImages[2] = ImageIO.read(new File("res/player_blue.png"));
			mImages[3] = ImageIO.read(new File("res/player_yellow.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		mColour = colour;
		mArrowKeys = new boolean [4];
		mGameApplication = gameApplication;
	}
	
	public void increaseScore() {
		mScore++;
	}
	
	public int getScore() {
		return mScore;
	}
	
	public int getWidth() {
		return mImages[0].getWidth();
	}
	
	public int getHeight() {
		return mImages[0].getHeight();
	}
	
	@Override
	public void update(int deltaTime) {
		int deltaX = 0, deltaY = 0;
		if (mArrowKeys[0]) deltaY = -1;
		if (mArrowKeys[1]) deltaX = -1;
		if (mArrowKeys[2]) deltaY = 1;
		if (mArrowKeys[3]) deltaX = 1;
		deltaX *= Math.round((float) deltaTime * mSpeed);
		deltaY *= Math.round((float) deltaTime * mSpeed);
		setX(getX() + deltaX);
		setY(getY() + deltaY);
		if (!mAlive) System.exit(0);
	}

	@Override
	public void render(Graphics2D g) {
		AffineTransform transform = new AffineTransform();
		transform.translate(getX(), getY());
		transform.rotate(mDirection, getWidth() / 2.0, getHeight() / 2.0);
		g.drawImage(mImages[mColour], transform, null);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_W:
			mArrowKeys[0] = true;
			break;
		case KeyEvent.VK_A:
			mArrowKeys[1] = true;
			break;
		case KeyEvent.VK_S:
			mArrowKeys[2] = true;
			break;
		case KeyEvent.VK_D:
			mArrowKeys[3] = true;
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_W:
			mArrowKeys[0] = false;
			break;
		case KeyEvent.VK_A:
			mArrowKeys[1] = false;
			break;
		case KeyEvent.VK_S:
			mArrowKeys[2] = false;
			break;
		case KeyEvent.VK_D:
			mArrowKeys[3] = false;
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseMoveImpl(e.getX(), e.getY());
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseMoveImpl(e.getX(), e.getY());
	}
	
	private void mouseMoveImpl(int x, int y) {
		Vector2f difference = new Vector2f(x, y).sub(new Vector2f(getX() + getWidth()/2, getY() + getHeight()/2)).normalize();
		mDirection = (float) Math.acos(difference.dot(new Vector2f(0.0f, -1.0f)));
		if (difference.x < 0.0f) mDirection = -mDirection;
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		Bullet bullet = new Bullet(mDirection);
		bullet.setX(getX() + getWidth()/2 - bullet.getWidth()/2);
		bullet.setY(getY() + getHeight()/2 - bullet.getHeight()/2);
		mGameApplication.spawnGameObject(bullet);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}
}