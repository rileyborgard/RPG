package main;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class Main extends BasicGame {
	
	private TiledMap map;
	
	private int px, py;
	private int dx, dy;
	private Image[][] playerImage;
	
	public Main(String title) {
		super(title);
	}
	
	public static void main(String[] args) {
		try {
			AppGameContainer app = new AppGameContainer(new Main("RPG"));
			app.setDisplayMode(800, 600, false);
			app.setVSync(true);
			app.setMinimumLogicUpdateInterval(15);
			app.setShowFPS(false);
			app.start();
		}catch(SlickException e) {
			e.printStackTrace();
		}
	}
	
	public void init(GameContainer gc) throws SlickException {
		px = 32;
		py = 32;
		dx = 0;
		dy = 0;
		map = new TiledMap("res/map.tmx");
		playerImage = new Image[3][3];
		Image sheet = new Image("res/player.png");
		for(int i = 0; i < playerImage.length; i++) {
			for(int j = 0; j < playerImage[0].length; j++) {
				playerImage[i][j] = sheet.getSubImage(i*16, j*16, 16, 16);
				playerImage[i][j].setFilter(Image.FILTER_NEAREST);
			}
		}
	}
	public void render(GameContainer gc, Graphics g) throws SlickException {
		int scale = 2;
		g.scale(scale, scale);
		int width = gc.getWidth()/scale;
		int height = gc.getHeight()/scale;
		int sx = width/2-px;
		int sy = height/2-py;
		map.render(sx, sy);
		getPlayerImage(dx, dy).draw(width/2, height/2);
	}
	public void update(GameContainer gc, int dt) throws SlickException {
		Input input = gc.getInput();
		int dx = (input.isKeyDown(Input.KEY_RIGHT) ? 1 : 0) - (input.isKeyDown(Input.KEY_LEFT) ? 1 : 0);
		int dy = (input.isKeyDown(Input.KEY_DOWN) ? 1 : 0) - (input.isKeyDown(Input.KEY_UP) ? 1 : 0);
		checkCollisions(dx, dy);
	}
	
	private void checkCollisions(int dx, int dy) {
		if(dx != 0 || dy != 0) {
			this.dx = dx;
			this.dy = dy;
		}
		if(placeFree(px+dx, py))
			px += dx;
		if(placeFree(px, py+dy))
			py += dy;
		
		//borders of map collisions
		px = Math.max(0, px);
		py = Math.max(0, py);
		px = Math.min(map.getWidth()*map.getTileWidth() - map.getTileWidth(), px);
		py = Math.min(map.getHeight()*map.getTileHeight() - map.getTileHeight(), py);
	}
	public boolean placeFree(int x, int y) {
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
	
	public Image getPlayerImage(int dx, int dy) {
		return playerImage[dx+1][dy+1];
	}
	
}
