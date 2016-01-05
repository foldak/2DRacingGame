package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable, KeyListener,
		MouseListener {
	// Fields
	public static int WIDTH = 600;
	public static int HEIGHT = 400;

	private Thread thread;
	private boolean running;
	private static long raceTime;
	private long start;

	private BufferedImage image;
	private Graphics2D g;

	private int FPS = 30;
	private double averageFPS;

	private static Player player;

	private ArrayList<RoadLine> roadLines;
	private ArrayList<Opponent> opponents;

	public enum STATE {
		MENU, GAME, RESULT
	};

	private static STATE state = STATE.MENU;
	private Menu menu;
	private Result result;

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
		addKeyListener(this);
		addMouseListener(this);
	}

	public void run() {

		running = true;

		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		setVar();

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
		DecimalFormat t = new DecimalFormat("##.##");
		double d = raceTime / 1000000000;
		String s = Long.toString(raceTime / 10000000);
		d = Double.parseDouble(s) / 100;
		t.format(d);

		s = "Your Time : " + d + " seconds";
		int length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
		g.drawString(s, (WIDTH - length) / 2, HEIGHT / 2);
		gameDraw();

	}

	private void gameUpdate() {
		if (state == STATE.GAME) {
			player.update();
			if (player.getDistance() <= 300)
				start = System.nanoTime();

			if (player.getDistance() <= 15000 && player.getDistance() >= 300) {

				raceTime = System.nanoTime() - start;
			}
			if (player.getDistance() > 15000) {
				state = STATE.RESULT;
				setVar();
				
			}
			// opponents update

			for (int i = 0; i < opponents.size(); i++) {
				opponents.get(i).update();

			}
			// road lines update

			for (int i = 0; i < roadLines.size(); i++) {
				roadLines.get(i).update();
				if (roadLines.get(i).getx() < player.getX() - 250)
					roadLines.get(i).setX(player.getX() + 820);

			}
			// player - opponent collision
			
			for (int i = 0; i < opponents.size(); i++) {
				Opponent o = opponents.get(i);

				if ((player.getX() < o.getX() + 120
						&& player.getX() + 120 > o.getX()
						&& player.getY() < o.getY() + 65 && player.getY() + 65 > o
						.getY())) {

					if (player.getY() - o.getY() < Math.abs(65)
							&& player.getX() - o.getX() < Math.abs(120)) {

						if (player.getY() > o.getY()
								&& player.getX() - o.getX() < 120
								&& player.getX() - o.getX() > -90) {

							player.setY(o.getY() + 65);

						} else if (player.getY() < o.getY()
								&& player.getX() - o.getX() < 120
								&& player.getX() - o.getX() > -90) {
							player.setY(o.getY() - 65);

						}
						player.setCollision(true);
					}
					player.setCollision(true);

					break;
				} else
					player.setCollision(false);

			}
		}

	}

	private void gameRender() {
		// draw background
		g.setColor(Color.GREEN.darker());
		g.fillRect(0, 0, WIDTH, HEIGHT / 6);
		g.setColor(Color.GRAY);
		g.fillRect(0, HEIGHT / 6, WIDTH, 5 * HEIGHT / 6);
		g.setColor(Color.GREEN.darker());
		g.fillRect(0, 5 * HEIGHT / 6, WIDTH, HEIGHT);
		Integer avg = (int) averageFPS;
		// draw road lines
		for (int i = 0; i < roadLines.size(); i++) {
			roadLines.get(i).draw(g);
		}

		// game draw
		if (state == STATE.GAME) {

			// draw player
			player.draw(g);
			// draw opponents
			for (int i = 0; i < opponents.size(); i++) {
				opponents.get(i).draw(g);
			}

			// test values
			g.drawString(avg.toString() + "time: " + raceTime / 10000000
					+ "----" + player.getDistance() / 100 + "/150", 10, 30);
		}
		// menu draw
		else if (state == STATE.MENU) {

			menu.update();
			menu.draw(g);
		}
		// result draw
		else if (state == STATE.RESULT) {
			result.update();
			result.draw(g);
		}
	}

	private void gameDraw() {
		Graphics2D g2 = (Graphics2D) this.getGraphics();
		g2.drawImage(image, 0, 0, null);

		g2.dispose();
	}

	public static Player getPlayer() {
		return player;
	}

	private void setVar() {
		player = new Player();
		result = new Result();
		menu = new Menu();
		roadLines = new ArrayList<RoadLine>(4);
		for (int i = 0; i < 4; i++) {
			roadLines.add(new RoadLine(360 * i));
		}
		opponents = new ArrayList<Opponent>(25);
		// road 268>y>67
		opponents.add(new Opponent(1000, 67));
		opponents.add(new Opponent(1500, 170));
		opponents.add(new Opponent(1900, 168));
		opponents.add(new Opponent(2180, 250));
		opponents.add(new Opponent(2380, 190));
		opponents.add(new Opponent(2680, 90));
		opponents.add(new Opponent(2920, 150));
		opponents.add(new Opponent(3220, 70));
		opponents.add(new Opponent(3390, 220));
		opponents.add(new Opponent(3630, 69));
		opponents.add(new Opponent(3930, 150));
		opponents.add(new Opponent(4150, 170));
		opponents.add(new Opponent(4440, 80));
		opponents.add(new Opponent(4700, 250));
		opponents.add(new Opponent(5100, 180));
		opponents.add(new Opponent(5400, 100));
		opponents.add(new Opponent(5650, 240));
		opponents.add(new Opponent(5950, 140));
		opponents.add(new Opponent(6100, 67));
		opponents.add(new Opponent(6290, 268));
		opponents.add(new Opponent(6590, 148));
		opponents.add(new Opponent(6900, 188));
		opponents.add(new Opponent(7140, 228));
		opponents.add(new Opponent(7400, 88));
		opponents.add(new Opponent(7700, 128));
	}

	public static long getRaceTime() {
		return raceTime;
	}

	// key listener
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode == KeyEvent.VK_LEFT) {
			player.setLeft(true);

		}

		if (keyCode == KeyEvent.VK_UP) {
			player.setUp(true);
		}
		if (keyCode == KeyEvent.VK_DOWN) {
			player.setDown(true);
		}
		if (keyCode == KeyEvent.VK_SPACE) {
			
		}
		if (keyCode == KeyEvent.VK_ESCAPE) {

			state = STATE.MENU;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode == KeyEvent.VK_LEFT) {
			player.setLeft(false);

		}

		if (keyCode == KeyEvent.VK_UP) {
			player.setUp(false);
		}
		if (keyCode == KeyEvent.VK_DOWN) {
			player.setDown(false);
		}

	}

	// MOUSE LISTENER
	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		int eX = e.getX();
		int eY = e.getY();
		// play/resume button
		if (eX > GamePanel.WIDTH / 4 && eX < GamePanel.WIDTH / 4 + 285) {
			if (eY > GamePanel.HEIGHT / 4 && eY < GamePanel.HEIGHT / 4 + 60) {
				if (player.getDistance() >= 15000)
					setVar();

				state = STATE.GAME;
			}
		}
		// quit/restart button
		if (eX > GamePanel.WIDTH / 4 && eX < GamePanel.WIDTH / 4 + 285) {
			if (eY > GamePanel.HEIGHT / 2 && eY < GamePanel.HEIGHT / 2 + 60) {
				if (player.getDistance() > 0) {
					setVar();
					state = STATE.GAME;
				} else
					System.exit(0);
			}

		}
		// quit button
		if (player.getDistance() > 0) {
			if (eX > GamePanel.WIDTH / 4 && eX < GamePanel.WIDTH / 4 + 285) {
				if (eY > 3 * GamePanel.HEIGHT / 4
						&& eY < 3 * GamePanel.HEIGHT / 4 + 60) {
					System.exit(0);
				}

			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
