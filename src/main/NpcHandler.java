package main;

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
	
	public static class Npc {
		
		public int x, y, dx, dy, imgId, numTalks;
		public boolean talking;
		
	}
	
}
