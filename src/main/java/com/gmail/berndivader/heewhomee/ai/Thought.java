package com.gmail.berndivader.heewhomee.ai;

public class Thought {
    String[]emotions;
    String text;
    
    public Thought(String text) {
    	this.text=text;
    }

    public String[] getEmotions() {
        return emotions;
    }

    public void setEmotions(String[]emos) {
        this.emotions=emos.clone();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text=text;
    }
}