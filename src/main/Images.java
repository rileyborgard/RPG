package main;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Images {
	
	private static final int NPC_NUM = 1;
	
	private static Image[][] playerImage;
	private static Image[][][] npcImage;
	
	public static void load() throws SlickException {
		
		// load player image
		playerImage = new Image[4][4];
		Image sheet = new Image("res/player.png");
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				playerImage[i][j] = sheet.getSubImage(i*17, j*26, 17, 26);
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
	
	static int t = 0;
	
	public static Image getPlayerImage(int dx, int dy, int vx, int vy) {
		if(vx == dx && vy == dy)
			t = (t+1)%40;
		else
			//default
			t = 10;
		if(dy > 0)
			return playerImage[0][t/10];
		if(dy < 0)
			return playerImage[3][t/10];
		if(dx > 0)
			return playerImage[1][t/10];
		if(dx < 0)
			return playerImage[2][t/10];
		return playerImage[0][0];
//		return playerImage[dx+1][dy+1];
	}
	
	public static Image getNpcImage(int npc, int dx, int dy) {
		return npcImage[npc][dx+1][dy+1];
	}
	
}
