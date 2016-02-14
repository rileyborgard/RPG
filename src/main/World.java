package main;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.nio.charset.Charset;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class World {

	public static final int OL_EXIT = 0, OL_SPAWN = 1, OL_NPC = 2;
	
	public static TiledMap map;
	
	public static Player player;
	
	public static boolean talking;
	// private static String text;
	
	public static void init(GameContainer gc) throws SlickException {
		loadMap("room1", "spawn0");
	}
	
	public static void update(GameContainer gc, int dt) throws SlickException {
		if (!talking)
			player.update(gc, dt);
		if (gc.getInput().isKeyPressed(Input.KEY_SPACE)) {
			if (!talking)
				chat(gc);
			else if (Text.done()) {
				talking = false;
				Text.reset();
			} else
				Text.skip();
		}
	}
	
	public static void render(GameContainer gc, Graphics g) throws SlickException {
		int width = gc.getWidth() / Main.SCALE - Player.WIDTH;
		int height = gc.getHeight() / Main.SCALE - Player.HEIGHT;
		int sx = width / 2 - player.getX();
		int sy = height / 2 - player.getY();
		map.render(sx, sy);
		player.render(gc, g);
		NpcHandler.render(gc, g);
	}
	
	private static void chat(GameContainer gc) {
		int x = player.getX();
		int y = player.getY();
		int dx = player.getDX();
		int dy = player.getDY();
		int tw = map.getTileWidth();
		int th = map.getTileHeight();
		int pw = Player.WIDTH;
		int ph = Player.HEIGHT;
		int objectCount = map.getObjectCount(OL_NPC);
		for (int i = 0; i < objectCount; i++) {
			int ex = map.getObjectX(OL_NPC, i) - tw/2;
			int ey = map.getObjectY(OL_NPC, i) - th/2;
			int ew = map.getObjectWidth(OL_NPC, i) + tw;
			int eh = map.getObjectHeight(OL_NPC, i) + th;
			if (ex + ew > x && x + pw > ex && ey + eh > y && y + ph > ey && dx * (x + pw / 2 - ex - ew / 2) <= 0
					&& dy * (y + ph / 2 - ey - eh / 2) <= 0) {
				Text.say(DialogueHandler.get(map.getObjectProperty(OL_NPC, i, "text", "")),
						(int) (Double.parseDouble(map.getObjectProperty(OL_NPC, i, "textX", ""))*gc.getWidth()),
						(int) (Double.parseDouble(map.getObjectProperty(OL_NPC, i, "textY", ""))*gc.getHeight()));
				talking = true;
				NpcHandler.npc[i].dx = -player.getDX();
				NpcHandler.npc[i].dy = -player.getDY();
				return;
			}
		}
	}

	public static void loadMap(String strMap, String strSpawn) throws SlickException {
		//make sure object layers have width and height properties so that slick doesn't freak out
		String mapText = "";
		try {
			BufferedReader in = new BufferedReader(new FileReader("res/" + strMap + ".tmx"));
			String line;
			while ((line = in.readLine()) != null) {
				if(line.contains("width"))
					mapText = mapText + line.replaceAll("\"tilemap.png\"", "\"res/tilemap.png\"");
				else
					mapText = mapText + line.replaceAll("<objectgroup", "<objectgroup width=\"1\" height=\"1\"").replaceAll("\"tilemap.png\"", "\"res/tilemap.png\"") + "\n";
			}
			in.close();
		} catch (Exception e) {
			throw new SlickException("Failed to load map");
		}
		map = new TiledMap(new ByteArrayInputStream(mapText.getBytes(Charset.forName("UTF-8"))));
		int objectCount = map.getObjectCount(OL_SPAWN);
		for (int i = 0; i < objectCount; i++) {
			if (map.getObjectName(OL_SPAWN, i).equals(strSpawn)) {
				player = new Player(map.getObjectX(OL_SPAWN, i), map.getObjectY(OL_SPAWN, i));
				break;
			}
		}
		NpcHandler.load(map);
	}

	public static boolean isPlaceFree(int x, int y) {
		//check walls layer
		boolean rightEdge = 1 + x / map.getTileWidth() >= map.getWidth();
		boolean bottomEdge = 1 + y / map.getTileHeight() >= map.getHeight();
		boolean ul = map.getTileId(x / map.getTileWidth(), y / map.getTileHeight(), map.getLayerIndex("Walls")) > 0;
		boolean ur = rightEdge ? true
				: map.getTileId(1 + x / map.getTileWidth(), y / map.getTileHeight(), map.getLayerIndex("Walls")) > 0;
		boolean bl = bottomEdge ? true
				: map.getTileId(x / map.getTileWidth(), 1 + y / map.getTileHeight(), map.getLayerIndex("Walls")) > 0;
		boolean br = rightEdge || bottomEdge ? true
				: map.getTileId(1 + x / map.getTileWidth(), 1 + y / map.getTileHeight(),
						map.getLayerIndex("Walls")) > 0;

		boolean xAligned = x % map.getTileWidth() == 0;
		boolean yAligned = y % map.getTileHeight() == 0;

		if (ul)
			return false;
		if (ur && !xAligned)
			return false;
		if (bl && !yAligned)
			return false;
		if (br && !xAligned && !yAligned)
			return false;
		
		int tw = map.getTileWidth();
		int th = map.getTileHeight();
		int objectCount = map.getObjectCount(OL_NPC);
		for(int i = 0; i < objectCount; i++) {
			int ex = map.getObjectX(OL_NPC, i);
			int ey = map.getObjectY(OL_NPC, i);
			int ew = map.getObjectWidth(OL_NPC, i);
			int eh = map.getObjectHeight(OL_NPC, i);
			if (ex + ew > x && x + tw > ex && ey + eh > y && y + th > ey) {
				return false;
			}
		}
		
		//return true if no collisions
		return true;
	}

	public static void checkExits(int x, int y) throws SlickException {
		int tw = map.getTileWidth();
		int th = map.getTileHeight();
		int objectCount = map.getObjectCount(OL_EXIT);
		for (int i = 0; i < objectCount; i++) {
			int ex = map.getObjectX(OL_EXIT, i);
			int ey = map.getObjectY(OL_EXIT, i);
			int ew = map.getObjectWidth(OL_EXIT, i);
			int eh = map.getObjectHeight(OL_EXIT, i);
			if (ex + ew > x && x + tw > ex && ey + eh > y && y + th > ey) {
				loadMap(map.getObjectProperty(OL_EXIT, i, "map", ""), map.getObjectProperty(OL_EXIT, i, "spawn", ""));
				return;
			}
		}
	}

	public static int getWidth() {
		return map.getWidth();
	}

	public static int getHeight() {
		return map.getHeight();
	}

	public static int getTileWidth() {
		return map.getTileWidth();
	}

	public static int getTileHeight() {
		return map.getTileHeight();
	}

}
