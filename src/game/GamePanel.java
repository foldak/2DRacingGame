package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
	// Fields
	public static int WIDTH = 600;
	public static int HEIGHT = 400;

	private Thread thread;
	private boolean running;
	
	private BufferedImage image;
	private Graphics2D g;

	private int FPS = 30;
	private double averageFPS;

	private Player player;

	// Constructor
	public GamePanel() {
		super();
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);
		requestFocus();

	}

	// Functions
	public void addNotify() {
		super.addNotify();
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
		// addKeyListener(this);
	}

	public void run() {

		running = true;

		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		player = new Player();

		long startTime;
		long URDTimeMillis;
		long waitTime;
		long totalTime = 0;

		int frameCount = 0;
		int maxFrameCount = 30;
		long targetTime = 1000 / FPS;

		// GAME LOOP
		while (running) {
			startTime = System.nanoTime();

			gameUpdate();
			gameRender();
			gameDraw();
			URDTimeMillis = (System.nanoTime() - startTime) / 1000000;
			waitTime = targetTime - URDTimeMillis;
			try {
				Thread.sleep(waitTime);
			} catch (Exception e) {

			}
			totalTime += System.nanoTime() - startTime;
			frameCount++;
			if (frameCount == maxFrameCount) {
				averageFPS = 1000.0 / ((totalTime / frameCount) / 1000000);
				frameCount = 0;
				totalTime = 0;
			}
		}
		g.setColor(new Color(0, 100, 255));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		String s = null;
		s = "Y O U   W O N";
		int length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
		g.drawString(s, (WIDTH - length) / 2, HEIGHT / 2);
		gameDraw();

	}

	private void gameUpdate() {

	}

	private void gameRender() {
		// draw background
		g.setColor(new Color(0, 100, 255));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.setColor(Color.BLACK);
		Integer avg = (int)averageFPS;
		g.drawString((avg.toString()), 10,30);
		player.draw(g);

	}

	private void gameDraw() {
		Graphics2D g2 = (Graphics2D) this.getGraphics();
		g2.drawImage(image, 0, 0, null);
		
		g2.dispose();
	}

	/*
	 * @Override public void keyTyped(KeyEvent e) { // TODO Auto-generated
	 * method stub
	 * 
	 * }
	 * 
	 * @Override public void keyPressed(KeyEvent e) { int keyCode =
	 * e.getKeyCode(); if (keyCode == KeyEvent.VK_LEFT) { player.setLeft(true);
	 * 
	 * } if (keyCode == KeyEvent.VK_RIGHT) { player.setRight(true); } if
	 * (keyCode == KeyEvent.VK_UP) { player.setUp(true); } if (keyCode ==
	 * KeyEvent.VK_DOWN) { player.setDown(true); } if (keyCode == KeyEvent.VK_Z)
	 * { player.setFiring(true); }
	 * 
	 * }
	 * 
	 * @Override public void keyReleased(KeyEvent e) { int keyCode =
	 * e.getKeyCode(); if (keyCode == KeyEvent.VK_LEFT) { player.setLeft(false);
	 * 
	 * } if (keyCode == KeyEvent.VK_RIGHT) { player.setRight(false); } if
	 * (keyCode == KeyEvent.VK_UP) { player.setUp(false); } if (keyCode ==
	 * KeyEvent.VK_DOWN) { player.setDown(false); }
	 * 
	 * if (keyCode == KeyEvent.VK_Z) { player.setFiring(false); }
	 * 
	 * }
	 */
}
