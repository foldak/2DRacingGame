package game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Opponent {
	// FIELDS
	private int x;
	private int y;
	private int speed;
	
	//CONSTRUCTOR
	public Opponent(int x, int y){
		this.y = y;
		this.x = x;
		speed = Player.getSpeedX()-8;
		
	}
	// FUNCTIONS
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public void setX(int x){
		this.x = x;
	}
	public void setY(int y){
		this.y = y;
	}
	public void update(){
		speed = Player.getSpeedX()-8;
		
		x-=speed;
	}
	public void draw(Graphics2D g){
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File("res/opponents.PNG"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		g.drawImage(image, x, y, null);
	}
	public void restart(){
		if(x<-150){
			x = (int)( (Math.random()*10000)-9000)+GamePanel.WIDTH;
			y =(int)( Math.random()*200)+GamePanel.HEIGHT/6;
		}
	}
}
