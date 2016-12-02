package engine;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Simple game object that renders a still image
 */

public class StaticSprite extends GameObject {
	private BufferedImage mImage;

	public StaticSprite(BufferedImage image) {
		mImage = image;
	}
	
	public StaticSprite(String filename) {
		try {
			mImage = ImageIO.read(new File(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected BufferedImage getImage() {
		return mImage;
	}
	
	protected void setImage(BufferedImage image) {
		mImage = image;
	}
	
	public int getWidth() {
		return mImage.getWidth();
	}
	
	public int getHeight() {
		return mImage.getHeight();
	}
	
	@Override
	public void update(int deltaTime) {}

	@Override
	public void render(Graphics2D g) {
		g.drawImage(mImage, getX(), getY(), null);
	}
}