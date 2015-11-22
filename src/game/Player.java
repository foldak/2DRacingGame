package game;

import java.awt.Graphics2D;

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
		speed = 0;
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
		
	}

	public void draw(Graphics2D g){
		
	}


	
}
