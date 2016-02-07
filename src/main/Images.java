package main;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Images {
	
	private static Image[][] playerImage;
	
	public static void load() throws SlickException {
		playerImage = new Image[3][3];
		Image sheet = new Image("res/player.png");
		for(int i = 0; i < playerImage.length; i++) {
			for(int j = 0; j < playerImage[0].length; j++) {
				playerImage[i][j] = sheet.getSubImage(i*16, j*16, 16, 16);
				playerImage[i][j].setFilter(Image.FILTER_NEAREST);
			}
		}	
	}
	
	public static Image getPlayerImage(int dx, int dy) {
		return playerImage[dx+1][dy+1];
	}
	
}
