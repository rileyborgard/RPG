package main;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.tiled.TiledMap;

public class NpcHandler {

	public static Npc[] npc;
	
	public static void load(TiledMap map) {
		int count = map.getObjectCount(World.OL_NPC);
		npc = new Npc[count];
		
		for(int i = 0; i < count; i++) {
			npc[i] = new Npc();
			npc[i].x = map.getObjectX(World.OL_NPC, i);
			npc[i].y = map.getObjectY(World.OL_NPC, i);
			npc[i].imgId = Integer.parseInt(map.getObjectProperty(World.OL_NPC, i, "image", ""));
		}
		
	}
	
	public static void render(GameContainer gc, Graphics g) {
		int width = gc.getWidth() / Main.SCALE;
		int height = gc.getHeight() / Main.SCALE;
		int sx = width / 2 - World.player.getX();
		int sy = height / 2 - World.player.getY();
		for (int i = 0; i < npc.length; i++) {
			int x = npc[i].x + sx;
			int y = npc[i].y + sy;
			int id = npc[i].imgId;
			int dx = npc[i].dx;
			int dy = npc[i].dy;
			SpriteHandler.get("npc", ""+id, ""+dx, ""+dy).draw(x, y);
		}
	}
	
	public static class Npc {
		
		public int x, y, dx, dy, imgId, numTalks;
		public boolean talking;
		
	}
	
}
