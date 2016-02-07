package main;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Main extends BasicGame {
	
	public static int SCALE = 2;
	
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
		Images.load();
		World.init();
	}
	public void render(GameContainer gc, Graphics g) throws SlickException {
		g.scale(SCALE, SCALE);
		World.render(gc, g);
	}
	public void update(GameContainer gc, int dt) throws SlickException {
		World.update(gc, dt);
	}
	
}
