package game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player {
	// FIELDS
	private int x;
	private int y;
	private int dx;
	private int dy;
	private static int speedY;
	private static int speedX;
	private boolean up;
	private boolean down;
	private boolean left;

	private long distance;
	private boolean collision;

	// CONSTRUCTOR
	public Player() {
		x = GamePanel.WIDTH / 5;
		y = GamePanel.HEIGHT / 2;

		dx = 0;
		dy = 0;
		speedY = 10;
		speedX = 18;
		collision = false;
		distance = 0;
	}

	// FUNCTIONS
	public long getDistance() {
		return distance;
	}
	public void setDistance(long d){
		d = distance;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int i) {
		x = i;
	}

	public void setY(int i) {
		y = i;
	}

	public void setLeft(boolean b) {
		left = b;
	}

	public void setUp(boolean b) {
		up = b;
	}

	public void setDown(boolean b) {
		down = b;
	}

	public void setCollision(boolean b) {
		collision = b;
	}

	public static int getSpeedX() {
		return speedX;
	}

	public void update() {
		/*
		 * } }
		 */

		if (up) {
			dy = -speedY;

		}
		if (down) {

			dy = speedY;

		}
		
		  if(up==true||down==true)
			  speedX = 17; 
		  //else speedX = 18;

		x += dx;
		y += dy;
		if (collision) {
			speedX = 3;
		}
		// if (right) speedX += 1;
		if (left){
			speedX -= 1;
			if(speedX<=0)
				speedX = 0;
			}
		else if (speedX < 18)
			speedX += 1;
		if (x <= 0)
			x = 0;
		if (y <= GamePanel.HEIGHT / 6)
			y = GamePanel.HEIGHT / 6;
		if (y >= (5 * GamePanel.HEIGHT / 6) - 65)
			y = (5 * GamePanel.HEIGHT / 6) - 65;
		if (x > GamePanel.WIDTH)
			x = GamePanel.WIDTH;
		if (y > GamePanel.HEIGHT)
			y = GamePanel.HEIGHT;
		distance += speedX;
		dx = 0;
		dy = 0;

	}

	public void draw(Graphics2D g) {
		BufferedImage image = null;

		try {
			image = ImageIO.read(new File("res/player.PNG"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		g.drawImage(image, x, y, null);

	}

}
