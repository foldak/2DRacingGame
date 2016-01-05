package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.text.DecimalFormat;

public class Result {
	private long raceTime = GamePanel.getRaceTime();
	
	public void draw(Graphics2D g) {
		g.setColor(new Color(0, 100, 255));
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		DecimalFormat t = new DecimalFormat("##.##");
		double d = raceTime / 1000000000;
		String s = Long.toString(raceTime / 10000000);
		d = Double.parseDouble(s) / 100;
		t.format(d);

		s = "Your Time : " + d + " seconds";
		int length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
		g.drawString(s, (GamePanel.WIDTH - length) / 2, GamePanel.HEIGHT / 2);
	}
	public void update(){
		raceTime = GamePanel.getRaceTime();
	}
}
