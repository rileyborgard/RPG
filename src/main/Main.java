package main;

import java.awt.Font;
import java.io.InputStream;
import java.util.Random;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;

public class Main extends BasicGame {
	
	public static int SCALE = 2;
	
	public static TrueTypeFont fontSmall, fontMedium, fontLarge;
	
	public static Random r = new Random();
	
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
		World.init(gc);
		InputStream inputStream = ResourceLoader.getResourceAsStream("res/half_bold_pixel-7.ttf");
//		InputStream inputStream = ResourceLoader.getResourceAsStream("res/Hardpixel.OTF");
		Font awtFont;
		try {
			awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			fontSmall = new TrueTypeFont(awtFont.deriveFont((float) Math.min(48, gc.getWidth()/160)), false);
			fontMedium = new TrueTypeFont(awtFont.deriveFont((float) Math.min(48, gc.getWidth()/80)), false);
			fontLarge = new TrueTypeFont(awtFont.deriveFont((float) gc.getWidth()/40), true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void render(GameContainer gc, Graphics g) throws SlickException {
		g.scale(SCALE, SCALE);
		World.render(gc, g);
		g.scale(1.0f/SCALE, 1.0f/SCALE);
		g.setFont((org.newdawn.slick.Font) fontLarge);
		Text.render(g);
//		Image textImg = new Image(gc.getWidth(), gc.getHeight());
//		Text.render(textImg.getGraphics());
//		g.drawImage(textImg, 0, 0);
	}
	public void update(GameContainer gc, int dt) throws SlickException {
		World.update(gc, dt);
		if(gc.getInput().isKeyDown(Input.KEY_ESCAPE)) {
			gc.exit();
		}
	}
	
}
