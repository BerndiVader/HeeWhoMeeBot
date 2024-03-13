package com.gmail.berndivader.heewhomee.ai;

import java.util.HashMap;
import java.util.UUID;

import com.gmail.berndivader.heewhomee.Console;
public class BotSession implements ISession {
	
	public boolean useable;
    final HashMap<String,String>vars;
    
    public BotSession(String bid) {
    	vars=new HashMap<String, String>();
    	
    	vars.put("botid",bid);
    	vars.put("custid",UUID.randomUUID().toString());
    	
    	testSession();
    }
    
    private void testSession() {
    	try {
			think("ping");
			useable=true;
		} catch (Exception e) {
			Console.err(e.getMessage(),true,e);
			useable=false;
		}
    }
    
    @Override
    public Thought think(Thought thought) throws Exception {
    	vars.put("input",thought.getText());
        String text=Utils.request("https://www.pandorabots.com/pandora/talk-xml",vars);
        return new Thought(Utils.xPathSearch(text,"//result/that/text()"));
    }

    @Override
    public String think(String text) throws Exception {
    	Thought thought=new Thought(text);
        return think(thought).getText();
    }
    
}