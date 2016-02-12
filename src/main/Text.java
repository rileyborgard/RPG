package main;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
	
public class Text {
	
	private static ArrayList<Fragment> frags = new ArrayList<>();
	private static double t;
	
	public static void render(Graphics g) {
		g.setFont(Main.fontLarge);
		update();
		int scale = g.getFont().getHeight("c");
		t += 0.1;
		for(Fragment f : frags) {
			if(f.toDelete && f.done()) {
				frags.remove(f);
				continue;
			}
			g.setColor(f.color);
//			String[] text = f.get().substring(0, f.index).split("\n");
			String[] text = f.get().split("\n");
			int x, y = f.posY-g.getFont().getHeight(text[0])/2;
			int num = Integer.parseInt(""+f.get().charAt(0));
			int sum = 0;
			loop:for(int k = 0; k < text.length; k++) {
				x = f.posX-g.getFont().getWidth(text[k])/2;
				for(int i = 1; i < text[k].length(); i++) {
					sum++;
					if(sum >= f.index)
						break loop;
					g.drawString(""+text[k].charAt(i), (int) (x+Math.cos(t*0.5*num+i*20)*num*0.01*scale), (int) (y+Math.sin(t*0.4*num+i*17)*num*0.01*scale));
					x += g.getFont().getWidth(""+text[k].charAt(i));
				}
				if(text[0].length() != 0)
					y += g.getFont().getHeight(""+text[0].charAt(0));
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
		private double rawI = 1, scrollSpeed = 0.3, delay = 0;
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
