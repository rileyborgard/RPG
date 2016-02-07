package main;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public class Player {
	
	private int x, y;
	private int dx, dy;
	
	public Player(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void update(GameContainer gc, int dt) {
		Input input = gc.getInput();
		int dx = (input.isKeyDown(Input.KEY_D) ? 1 : 0) - (input.isKeyDown(Input.KEY_A) ? 1 : 0);
		int dy = (input.isKeyDown(Input.KEY_S) ? 1 : 0) - (input.isKeyDown(Input.KEY_W) ? 1 : 0);
		checkCollisions(dx, dy);
	}
	public void render(GameContainer gc, Graphics g) {
		int width = gc.getWidth()/Main.SCALE;
		int height = gc.getHeight()/Main.SCALE;
		Images.getPlayerImage(dx, dy).draw(width/2, height/2);
	}
	
	private void checkCollisions(int dx, int dy) {
		if(dx != 0 || dy != 0) {
			this.dx = dx;
			this.dy = dy;
		}
		if(World.placeFree(x+dx, y))
			x += dx;
		if(World.placeFree(x, y+dy))
			y += dy;
		
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
	
}