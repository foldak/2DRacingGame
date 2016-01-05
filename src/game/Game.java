package game;


import javax.swing.JFrame;


public class Game {

	public static void main(String[] args) {
		JFrame window= new JFrame("Side Scroll Racing");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		GamePanel contentPane = new GamePanel();
		window.setContentPane(contentPane);
		window.pack();
		window.setVisible(true);
		

	}

}