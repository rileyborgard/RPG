package main;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Player {
	
	private int x, y;
	private static int dx, dy;
	private static int vx, vy;
	
	public static final int WIDTH = 17, HEIGHT = 26;
	
	public Player(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void update(GameContainer gc, int dt) throws SlickException {
		Input input = gc.getInput();
		int dx = (input.isKeyDown(Input.KEY_D) ? 1 : 0) - (input.isKeyDown(Input.KEY_A) ? 1 : 0);
		int dy = (input.isKeyDown(Input.KEY_S) ? 1 : 0) - (input.isKeyDown(Input.KEY_W) ? 1 : 0);
		checkCollisions(dx, dy);
		World.checkExits(x, y);
	}
	public void render(GameContainer gc, Graphics g) throws SlickException {
		int width = gc.getWidth()/Main.SCALE;
		int height = gc.getHeight()/Main.SCALE;
		int sx = width / 2 - x - WIDTH/2;
		int sy = height / 2 - y - HEIGHT/2;
		SpriteHandler.get("playerImage", ""+dx, ""+dy, ""+vx, ""+vy).draw(x + sx, y + sy);
	}
	
	private void checkCollisions(int dx, int dy) {
		vx = 0;
		vy = 0;
		if(dx != 0 || dy != 0) {
			Player.dx = dx;
			Player.dy = dy;
		}
		if(World.isPlaceFree(x+dx, y, WIDTH, HEIGHT))
			x += dx;
		if(World.isPlaceFree(x, y+dy, WIDTH, HEIGHT))
			y += dy;
		vx = dx;
		vy = dy;
		
		//borders of map collisions
		x = Math.max(0, x);
		y = Math.max(0, y);
		x = Math.min(World.getWidth()*World.getTileWidth() - World.getTileWidth(), x);
		y = Math.min(World.getHeight()*World.getTileHeight() - World.getTileHeight(), y);
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	
	public int getDX() {
		return dx;
	}
	public int getDY() {
		return dy;
	}
	
}
