package com.gmail.berndivader.heewhomee.ai;

public
interface
ISession 
{
    Thought think(Thought thought) throws Exception;
    String think(String text) throws Exception;
}