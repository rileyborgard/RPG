package main;

import org.newdawn.slick.Image;

public class SpriteHandler {
	
	public static Image get(String ...str) {
		try {
			if(str[0].equals("playerImage")) {
				return Images.getPlayerImage(Integer.parseInt(str[1]), Integer.parseInt(str[2]));
			}
			if(str[0].equals("npc")) {
				return Images.getNpcImage(Integer.parseInt(str[1]), Integer.parseInt(str[2]), Integer.parseInt(str[3]));
			}
			return null;
		}catch(ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}
	
}
