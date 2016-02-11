package main;

public class DialogueHandler {

	public static String get(String id) {
		
		// room 1
		
		if(id.equals("test npc 1")) { // the random test npc
			return "9THIS IS INTENSE\\this new line is calmer/5This ONe KIndA DoeSN'T Know";
		}else if(id.equals("test npc 2")) { // the random test npc
			return "1My text actually makes sense and is somewhat\\in-context. Maybe?\\Perhaps?\\Probably not.\\aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
		}

		// room 2

		// etc
		
		return "dun goofed";
	}
	
}
