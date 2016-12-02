package engine;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;

/**
 * Game object that renders an animated sprite
 */

public class AnimatedSprite extends GameObject {
	private BufferedImage[] mFrames;	// Array of frames
	private final int mFrameTime;		// Length of each frame in milliseconds
	private int mAnimationTime = 0;		// Time since start of animation in milliseconds
	private boolean mPaused = false;	// If true then the animation is paused
	private boolean mLoop = true;		// Should the animation loop

	// Load from animated GIF
	public AnimatedSprite(String filename, int frameTime) {
		mFrameTime = frameTime;
		try {
			ImageReader reader = ImageIO.getImageReadersByFormatName("gif").next();
			reader.setInput(ImageIO.createImageInputStream(new File(filename)));
			mFrames = new BufferedImage [reader.getNumImages(true)];
			for (int i = 0; i < mFrames.length; i++) mFrames[i] = reader.read(i);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Load from a series of still images
	public AnimatedSprite(String[] filenames, int frameTime) {
		mFrameTime = frameTime;
		mFrames = new BufferedImage [filenames.length];
		for (int i = 0; i < mFrames.length; i++) {
			try {
				mFrames[i] = ImageIO.read(new File(filenames[i]));
			} catch (IOException e) {
				e.printStackTrace();
				mFrames[i] = null;
			}
		}
	}
	
	// Load from a sprite sheet
	public AnimatedSprite(String filename, int rows, int cols, int frameTime) {
		mFrameTime = frameTime;
		mFrames = new BufferedImage [rows * cols];
		try {
			BufferedImage spriteSheet = ImageIO.read(new File(filename));
			
			// Get width and height of each frame
			int width = spriteSheet.getWidth() / cols;
			int height = spriteSheet.getHeight() / rows;
			
			// Now, load each frame into our array
			for (int i = 0; i < cols; i++) for (int j = 0; j < rows; j++) {
				mFrames[j * rows + i] = spriteSheet.getSubimage(width * i, height * j, width, height);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void play() {
		mPaused = false;
	}
	
	public void pause() {
		mPaused = true;
	}
	
	public boolean getPaused() {
		return mPaused;
	}
	
	// Go to a specific time in the animation
	public void goToTime(int ms) {
		mAnimationTime = ms;
	}
	
	public int getAnimationTime() {
		return mAnimationTime;
	}
	
	// Go to a specific frame in the animation
	public void goToFrame(int frameNo) {
		mAnimationTime = frameNo * mFrameTime;
	}
	
	public void setLoop(boolean loop) {
		mLoop = loop;
	}
	
	public boolean isLooping() {
		return mLoop;
	}
	
	public int getFrameTime() {
		return mFrameTime;
	}
	
	protected BufferedImage[] getFrames() {
		return mFrames;
	}
	
	protected void setFrames(BufferedImage[] frames) {
		mFrames = frames;
	}
	
	@Override
	public void update(int deltaTime) {
		// If the animation has stopped, don't do anything
		if (mPaused) return;
		
		mAnimationTime += deltaTime;
		
		// If the animation is beyond the end, then either loop or stop
		if (mAnimationTime >= mFrameTime * mFrames.length) {
			if (mLoop) {
				mAnimationTime %= mFrameTime * mFrames.length;
			} else {
				mAnimationTime = mFrameTime * mFrames.length - 1;
				mPaused = true;
			}
		}
	}

	// Get the index of the current frame to draw (or the last one if the animation time is too far)
	protected int getFrameIndex() {
		return Math.min(mFrames.length - 1, mAnimationTime / mFrameTime);
	}
	
	@Override
	public void render(Graphics2D g) {
		g.drawImage(mFrames[getFrameIndex()], getX(), getY(), null);
	}
}