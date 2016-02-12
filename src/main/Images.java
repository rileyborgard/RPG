package main;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Images {
	
	private static final int NPC_NUM = 1;
	
	private static Image[][] playerImage;
	private static Image[][][] npcImage;
	
	public static void load() throws SlickException {
		
		// load player image
		playerImage = new Image[3][3];
		Image sheet = new Image("res/player.png");
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				playerImage[i][j] = sheet.getSubImage(i*16, j*16, 16, 16);
				playerImage[i][j].setFilter(Image.FILTER_NEAREST);
			}
		}
		
		npcImage = new Image[NPC_NUM][3][3];
		sheet = new Image("res/npc.png");
		for(int a = 0; a < npcImage.length; a++) {
			for(int i = 0; i < 3; i++) {
				for(int j = 0; j < 3; j++) {
					npcImage[a][i][j] = sheet.getSubImage(i*16+a*48, j*16, 16, 16);
					npcImage[a][i][j].setFilter(Image.FILTER_NEAREST);
				}
			}
		}
		
	}
	
	public static Image getPlayerImage(int dx, int dy) {
		return playerImage[dx+1][dy+1];
	}
	
	public static Image getNpcImage(int npc, int dx, int dy) {
		return npcImage[npc][dx+1][dy+1];
	}
	
}
