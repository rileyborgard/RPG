package main;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class World {
	
	private static TiledMap map;
	
	private static Player player;
	
	public static void init() throws SlickException {
		player = new Player(0, 0);
		try {
			map = new TiledMap("res/map.tmx");
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public static void update(GameContainer gc, int dt) throws SlickException {
		player.update(gc, dt);
	}
	public static void render(GameContainer gc, Graphics g) throws SlickException {
		int width = gc.getWidth()/Main.SCALE;
		int height = gc.getHeight()/Main.SCALE;
		int sx = width/2-player.getX();
		int sy = height/2-player.getY();
		map.render(sx,  sy);
		player.render(gc, g);
	}
	
	public static boolean placeFree(int x, int y) {
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
