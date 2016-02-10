package main;

import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class World {

	private static TiledMap map;

	private static Player player;

	private static boolean talking;
	//	private static String text;

	private static Image textImage;
	private static Graphics textG;

	public static void init(GameContainer gc) throws SlickException {
		loadMap("room1", "spawn0");
		int width = gc.getWidth()/Main.SCALE;
		int height = gc.getHeight()/Main.SCALE;
		textImage = new Image(width, height/4);
		textG = textImage.getGraphics();
	}

	public static void update(GameContainer gc, int dt) throws SlickException {
		if(!talking)
			player.update(gc, dt);
		if(gc.getInput().isKeyPressed(Input.KEY_SPACE)) {
			if(!talking) chat();
			else if(Text.done()) {
				talking = false;
				Text.reset();
			}else
				Text.skip();
		}
	}
	public static void render(GameContainer gc, Graphics g) throws SlickException {
		int width = gc.getWidth()/Main.SCALE;
		int height = gc.getHeight()/Main.SCALE;
		int sx = width/2-player.getX();
		int sy = height/2-player.getY();
		map.render(sx,  sy);
		player.render(gc, g);
		//		g.setFont((Font) Main.fontLarge);
		//		Text.render(g);
		//		if(talking) {
		//			textG.setFont(Main.font);
		//			textG.setColor(Color.black);
		//			textG.fillRect(0, 0, width, height);
		//			textG.setColor(Color.white);
		//			textG.drawRect(0, 0, width, height);
		//			textG.drawString(text, 4, 4);
		//			g.drawImage(textImage, 0, 3*height/4);
		//		}
	}

	private static void chat() {
		int x = player.getX();
		int y = player.getY();
		int tw = map.getTileWidth();
		int th = map.getTileHeight();
		int objGroupCount = map.getObjectGroupCount();
		for(int i = 0; i < objGroupCount; i++) {
			int objectCount = map.getObjectCount(i);
			for(int j = 0; j < objectCount; j++) {
				if(map.getObjectName(i, j).substring(0, 3).equals("npc")) {
					int ex = map.getObjectX(i, j);
					int ey = map.getObjectY(i, j);
					int ew = map.getObjectWidth(i, j);
					int eh = map.getObjectHeight(i, j);
					if(ex + ew > x && x + tw > ex && ey + eh > y && y + th > ey) {
						Text.say(map.getObjectProperty(i, j, "text", ""),
								Integer.parseInt(map.getObjectProperty(i, j, "x", "")),
								Integer.parseInt(map.getObjectProperty(i, j, "y", "")));
						talking = true;
						return;
					}
				}
			}
		}
	}

	public static void loadMap(String strMap, String strSpawn) throws SlickException {
		map = new TiledMap("res/" + strMap + ".tmx");
		int objGroupCount = map.getObjectGroupCount();
		for(int i = 0; i < objGroupCount; i++) {
			int objectCount = map.getObjectCount(i);
			for(int j = 0; j < objectCount; j++) {
				if(map.getObjectName(i, j).equals(strSpawn)) {
					player = new Player(map.getObjectX(i, j), map.getObjectY(i, j));
					return;
				}
			}
		}
	}

	public static boolean isPlaceFree(int x, int y) {
		boolean rightEdge = 1+x/map.getTileWidth() >= map.getWidth();
		boolean bottomEdge = 1+y/map.getTileHeight() >= map.getHeight();
		boolean ul = map.getTileId(x/map.getTileWidth(), y/map.getTileHeight(), map.getLayerIndex("Walls")) > 0;
		boolean ur = rightEdge ? true :
			map.getTileId(1+x/map.getTileWidth(), y/map.getTileHeight(), map.getLayerIndex("Walls")) > 0;
			boolean bl = bottomEdge ? true :
				map.getTileId(x/map.getTileWidth(), 1+y/map.getTileHeight(), map.getLayerIndex("Walls")) > 0;
				boolean br = rightEdge || bottomEdge ? true :
					map.getTileId(1+x/map.getTileWidth(), 1+y/map.getTileHeight(), map.getLayerIndex("Walls")) > 0;

					boolean xAligned = x % map.getTileWidth() == 0;
					boolean yAligned = y % map.getTileHeight() == 0;

					if(ul) return false;
					if(ur && !xAligned) return false;
					if(bl && !yAligned) return false;
					if(br && !xAligned && !yAligned) return false;
					return true;
	}
	public static void checkExits(int x, int y) throws SlickException {
		int tw = map.getTileWidth();
		int th = map.getTileHeight();
		int objGroupCount = map.getObjectGroupCount();
		for(int i = 0; i < objGroupCount; i++) {
			int objectCount = map.getObjectCount(i);
			for(int j = 0; j < objectCount; j++) {
				if(map.getObjectName(i, j).substring(0, 4).equals("exit")) {
					int ex = map.getObjectX(i, j);
					int ey = map.getObjectY(i, j);
					int ew = map.getObjectWidth(i, j);
					int eh = map.getObjectHeight(i, j);
					if(ex + ew > x && x + tw > ex && ey + eh > y && y + th > ey) {
						loadMap(map.getObjectProperty(i, j, "map", ""), map.getObjectProperty(i, j, "spawn", ""));
						return;
					}
				}
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
