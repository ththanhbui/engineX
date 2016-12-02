package engine;

import java.awt.*;
import java.util.*;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.joml.Vector2f;

import resources.Bullet;
import resources.Enemy;
import resources.Player;

/**
 * Represents a running game including the game window and all the game objects.
 * Manages updates and draw calls.
 */

public class GameApplication extends JFrame {
	private static final long serialVersionUID = 1L;

	// The panel inside the game window where the drawing gets done
	private JPanel mGamePanel;

	// List of game objects
	private ArrayList<GameObject> mGameObjects;

	@SuppressWarnings("serial")
	public GameApplication(String title, int width, int height) {
		super(title);

		// Setup game window
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setSize(width, height);

		mGameObjects = new ArrayList<>();

		// Setup game panel
		mGamePanel = new JPanel() {
			{ setBackground(Color.WHITE); }

			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g;

				// Turn on antialiasing
				g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
				
				// Render each game object
				mGameObjects.forEach(o -> o.render(g2));
			}
		};
		add(mGamePanel);

		// Show window
		setVisible(true);
	}

	// Constructor that takes an icon
	public GameApplication(String title, int width, int height, Image icon) {
		this(title, width, height);
		setIconImage(icon);
	}

	// Constructor that takes a path to an icon
	public GameApplication(String title, int width, int height, String iconFile) {
		this(title, width, height, Toolkit.getDefaultToolkit().getImage(iconFile));
	}

	// Gets if the window is running
	public boolean isRunning() {
		return isShowing();
	}

	// Return a shallow copy of the list of game objects
	@SuppressWarnings("unchecked")
	public ArrayList<GameObject> getGameObjects() {
		return (ArrayList<GameObject>) mGameObjects.clone();
	}

	// Adds a game object to the list
	public void spawnGameObject(GameObject object) {
		mGameObjects.add(object);
	}

	// Remove a game object
	public void removeGameObject(GameObject object) {
		Iterator<GameObject> it = mGameObjects.iterator();
		while (it.hasNext()) {
			// Note: this removes the game object that points to the same place
			// in memory as 'object'
			GameObject next = it.next();
			if (next == object) it.remove();
		}
	}

	// Used for when changing level, etc.
	public void removeAllGameObjects() {
		mGameObjects.clear();
	}

	// Update each game object individually
	public void update(int deltaTime) {
		mGameObjects.forEach(o -> o.update(deltaTime));
	}

	// Render all game objects
	public void render() {
		mGamePanel.repaint();

		// Wait a millisecond so that the next deltaTime is at least 1
		try { Thread.sleep(10); } catch (InterruptedException e) { e.printStackTrace(); }
	}
	
	public static void main(String[] args) {
		GameApplication game = new GameApplication("Game Window", 800, 600);
		Player player = new Player(game);
		player.setX(400);
		player.setY(300);
		
		TextObject healthDisplay = new TextObject("", new Font("Comic Sans MS", Font.PLAIN, 30), Color.BLACK);
		TextObject scoreDisplay = new TextObject("", new Font("Comic Sans MS", Font.PLAIN, 30), Color.BLACK);
		TextObject enemyDisplay = new TextObject("", new Font("Comic Sans MS", Font.PLAIN, 30), Color.BLACK);
		healthDisplay.setY(60);
		scoreDisplay.setY(500);
		enemyDisplay.setY(250);
		game.spawnGameObject(healthDisplay);
		game.spawnGameObject(scoreDisplay);
		game.spawnGameObject(enemyDisplay);
		
		game.spawnGameObject(player);
		game.addKeyListener(player);
		game.addMouseMotionListener(player);
		game.addMouseListener(player);
		
		for (int i = 0; i < 20; i++) {
			Enemy enemy = new Enemy(player);
			enemy.setX((int) (Math.random() * 500));
			enemy.setY((int) (Math.random() * 500));
			game.spawnGameObject(enemy);
		}
		
		long t = System.currentTimeMillis();
		while (game.isRunning()) {
			int deltaTime = (int) (System.currentTimeMillis() - t);
			t = System.currentTimeMillis();
			
			game.update(deltaTime);
			healthDisplay.setString("Health: " + player.getHealth());
			scoreDisplay.setString("Score: " + player.getScore());
			
			for(GameObject bullet : game.getGameObjects()) if (bullet instanceof Bullet) {
				for (GameObject g : game.getGameObjects()) if (g instanceof Enemy) {
					Enemy enemy = (Enemy) g;
					Vector2f bulletPosition = new Vector2f(bullet.getX(), bullet.getY());
					Vector2f enemyPosition = new Vector2f(enemy.getX(), enemy.getY());
					Vector2f direction = bulletPosition.sub(enemyPosition);
					if (direction.length()<Math.sqrt(Math.pow(((Bullet) bullet).getWidth(),2) + Math.pow(((Bullet) bullet).getHeight(),2))) {
						enemy.decrementHealth(5);
					}
				}
			}
			
			game.render();
		}
	}
}