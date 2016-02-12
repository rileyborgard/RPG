package main;

public class DialogueHandler {

	public static String get(String id) {
		
		// room 1
		
		if(id.equals("test npc 1")) { // the random test npc
			return "3Hello, George./9I WANT TO KILL YOU./3But I'm too lazy...";
		}else if(id.equals("test npc 2")) { // the random test npc
			return "1Testing testing\n44 11 potato";
		}

		// room 2

		// etc
		
		return "dun goofed";
	}
	
}
