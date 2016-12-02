package engine;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 * Represents a string that can be drawn to the screen
 */

public class TextObject extends GameObject {
	private String mString;
	private Font mFont;
	private Color mColour;

	public TextObject(String string, Font font, Color colour) {
		mString = string;
		mFont = font;
		mColour = colour;
	}
	
	protected String getString() {
		return mString;
	}

	public void setString(String string) {
		mString = string;
	}

	public Font getFont() {
		return mFont;
	}

	public void setFont(Font font) {
		mFont = font;
	}

	public Color getColour() {
		return mColour;
	}

	public void setColour(Color colour) {
		mColour = colour;
	}
	
	@Override
	public void update(int deltaTime) {}

	@Override
	public void render(Graphics2D g) {
		g.setFont(mFont);
		g.setColor(mColour);
		g.drawString(mString, getX(), getY());
	}
}
