package main;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.UnicodeFont;

public class Text {
	
	private static ArrayList<Fragment> frags = new ArrayList<>();
	private static double t;
	
	public static void render(Graphics g) {
		g.setFont(Main.fontLarge);
		update();
		t += 0.1;
		for(Fragment f : frags) {
			if(f.toDelete && f.done()) {
				frags.remove(f);
				continue;
			}
			g.setColor(f.color);
			int x = f.posX, y = f.posY;
			String text = f.get();
			int num = 2*Integer.parseInt(""+f.get().charAt(0));
			for(int i = 1; i < f.index; i++) {
				if(text.charAt(i) == '\\') {
					y += g.getFont().getHeight(""+text.charAt(i-1));
					x = f.posX;
					continue;
				}
				g.drawString(""+text.charAt(i), (int) (x+Math.cos(t*0.25*num+i*20)*num*0.2), (int) (y+Math.sin(t*0.15*num+i*17)*num*0.2));
				x += g.getFont().getWidth(""+text.charAt(i));
			}
		}
	}
	public static void update() {
		for(Fragment f : frags) {
			f.update();
		}
	}
	
	public static void reset() {
		frags = new ArrayList<>();
	}
	public static boolean done() {
		for(Fragment f : frags) {
			if(!f.done())
				return false;
		}
		return true;
	}
	public static void skip() {
		for(Fragment f : frags) {
			f.skip();
		}
	}
	
	public static void say(String str, int x, int y) {
		frags.add(new Fragment(str, x, y));
	}
	
	public static class Fragment {
		
		public String[] text;
		public int posX, posY;
		public int index = 0, lineIndex = 0;
		private double rawI = 1, scrollSpeed = 0.1, delay = 0;
		public Color color = Color.white;
		public boolean toDelete = false;
		
		public Fragment(String text, int x, int y) {
			this.text = text.split("/");
			posX = x;
			posY = y;
		}
		public Fragment(String text, int x, int y, int del1) {
			this.text = text.split("/");
			posX = x;
			posY = y;
			rawI -= del1;
		}
		
		public String get() {
			return text[lineIndex];
		}
		
		public void update() {
			rawI += scrollSpeed;
			index = (int) Math.min(get().length(), rawI);
		}
		
		public void skip() {
			if(rawI-delay < get().length())
				rawI = get().length()+delay;
			else {
				rawI = 0;
				lineIndex++;
			}
		}
		
		public boolean done() {
			return lineIndex == text.length-1 && (rawI-delay >= get().length());
		}
		
	}
	
}
