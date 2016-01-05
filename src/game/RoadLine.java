package game;

import java.awt.Color;
import java.awt.Graphics2D;

public class RoadLine {
	// FIELDS
	private int x;
	private int y;
	private Color color;
	private int speed;
	private int height;
	private int width;
	
	// CONSTRUCTOR
	public RoadLine(int x){
	this.x = x;
	y = GamePanel.HEIGHT/2;
	height = 15;
	width = 120;
	speed = Player.getSpeedX();
	color = Color.WHITE;
	
}
	// FUNCTIONS
	public int getx(){
		return x;
	}
	public void setX(int x){
		this.x=x;
	}
	public void draw(Graphics2D g){
		g.setColor(color);
		g.fillRect(x, y, width, height);
		
	}
	public void update(){
		
		speed = Player.getSpeedX();
		x -=speed;
		
	}
	public boolean restart(){
		if(x<=-120){
			return true;
		}
		else return false;
	}
	
}
