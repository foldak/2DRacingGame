package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Menu {
	private Player player = GamePanel.getPlayer();
	private long pX = player.getDistance();

	private Rectangle playBtn = new Rectangle(GamePanel.WIDTH / 4,
			GamePanel.HEIGHT / 4, 285, 60);
	private Rectangle quitBtn = new Rectangle(GamePanel.WIDTH / 4,
			GamePanel.HEIGHT / 2, 285, 60);
	

	public void draw(Graphics2D g) {
		g.setFont(new Font("arial", Font.ITALIC, 37));
		g.setColor(Color.BLUE);
		g.drawString("RACING SCROLL GAME", (GamePanel.WIDTH / 4) - 70,
				GamePanel.HEIGHT / 6);
		g.setColor(Color.WHITE.darker());
		g.fill(playBtn);
		g.fill(quitBtn);
		g.setColor(Color.WHITE);
		if (pX <= 0||pX>=15000) {
			g.drawString("START", (GamePanel.WIDTH / 4) + 80,
					(GamePanel.HEIGHT / 4) + 45);
			g.drawString("QUIT", (GamePanel.WIDTH / 4) + 90,
					(GamePanel.HEIGHT / 2) + 45);
		} else if (pX > 0) {

			g.drawString("RESUME", (GamePanel.WIDTH / 4) + 80,
					(GamePanel.HEIGHT / 4) + 45);
			g.drawString("RESTART", (GamePanel.WIDTH / 4) + 80,
					(GamePanel.HEIGHT / 2) + 45);
			g.setColor(Color.WHITE.darker());
			g.fillRect(GamePanel.WIDTH/4, 3*GamePanel.HEIGHT/4, 285, 60);
			g.setColor(Color.WHITE);
			g.drawString("QUIT", (GamePanel.WIDTH/4)+90, (3*GamePanel.HEIGHT/4)+45);
		}

	}
	public void update(){
		pX = player.getDistance();
	}
	
}
