package com.gmail.berndivader.heewhomee.ai;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import com.gmail.berndivader.heewhomee.Console;
public class BotSession implements ISession {
	
	public boolean useable;
    final Map<String,String>vars;
    
    public BotSession(String bid) {
    	vars=new LinkedHashMap<String, String>();
    	
    	vars.put("botid",bid);
    	vars.put("custid",UUID.randomUUID().toString());
    	
    	testSession();
    }
    
    private void testSession() {
    	try {
			think("ping");
			useable=true;
		} catch (Exception e) {
			Console.err(e.getMessage(),true);
			useable=false;
		}
    }
    
    @Override
    public Thought think(Thought thought) throws Exception {
    	vars.put("input",thought.getText());
        String text=Utils.request("https://www.pandorabots.com/pandora/talk-xml",null,null,vars);
        Thought t=new Thought();
        t.setText(Utils.xPathSearch(text,"//result/that/text()"));
        return t;
    }

    @Override
    public String think(String text) throws Exception {
    	Thought thought=new Thought();
    	thought.setText(text);
        return think(thought).getText();
    }
    
}