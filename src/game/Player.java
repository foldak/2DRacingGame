package game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player {
	//FIELDS
	private int x;
	private int y;
	private int dx;
	private int dy;
	private int speed;
	
	private boolean up;
	private boolean down;
	private boolean left;
	private boolean right;
	
	//CONSTRUCTOR
	public Player(){
		x = GamePanel.WIDTH/2;
		y = GamePanel.HEIGHT/2;
		
		dx = 0;
		dy = 0;
		speed = 5;
	}
	
	// FUNCTIONS

	public int getx() {
		return x;
	}

	public int gety() {
		return y;
	}
	public void setLeft(boolean b) {
		left = b;
	}

	public void setRight(boolean b) {
		right = b;
	}

	public void setUp(boolean b) {
		up = b;
	}

	public void setDown(boolean b) {
		down = b;
	}
	
	public void update(){
		if (left) {
			dx = -speed;
		}
		if (right) {
			dx = speed;
		}
		if (up) {
			dy = -speed;
		}
		if (down) {
			dy = speed;
		}
		x += dx;
		y += dy;

		
		if (x > GamePanel.WIDTH )
			x = GamePanel.WIDTH ;
		if (y > GamePanel.HEIGHT )
			y = GamePanel.HEIGHT ;

		dx = 0;
		dy = 0;
	}

	public void draw(Graphics2D g){
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(new File("C:\\Documents and Settings\\Filip\\Pulpit\\tes.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		g.drawImage(image,x,y,null);
		
	}


	
}
