package com.gmail.berndivader.heewhomee.ai;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
public class Bot {
    final String bid;

    public Bot(String bid) {
        this.bid=bid;
    }

    public Session createSession() {
        return new Session();
    }

    private class Session implements ISession {
        final Map<String,String>vars;

        public Session() {
        	vars=new LinkedHashMap<String, String>();
        	vars.put("botid",bid);
        	vars.put("custid",UUID.randomUUID().toString());
        }

        public Thought think(Thought thought) throws Exception {
        	vars.put("input",thought.getText());
            String text=Utils.request("https://www.pandorabots.com/pandora/talk-xml",null,null,vars);
            Thought t=new Thought();
            t.setText(Utils.xPathSearch(text,"//result/that/text()"));
            return t;
        }

        public String think(String text) throws Exception {
        	Thought thought=new Thought();
        	thought.setText(text);
            return think(thought).getText();
        }
    }
}