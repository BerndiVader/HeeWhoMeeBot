package com.gmail.berndivader.heewhomee.database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

import com.gmail.berndivader.heewhomee.Console;

import net.dv8tion.jda.api.entities.Message;

public class DiscordRequest extends Worker<Void> {
	
	private static int size=1900;
	private static 	Pattern pattern=Pattern.compile("@(.*?) ");
	private final Message message;
	private final boolean bot;
			
	public DiscordRequest(Message m,boolean bot) {
		message=m;
		this.bot=bot;
	}

	@Override
	public Void call() throws Exception {
		ArrayList<String>messages=new ArrayList<>();
		if(!bot) {
			String context=getResult(message.getContentRaw().substring(1).trim().toLowerCase().split(" ",2));
			if(context.length()>size) {
				while (context.length()>size) {
					messages.add(context.substring(0, size).concat("```"));
					context=context.substring(size, context.length());
					context="```Markdown\n"+context.trim();
				}
			}
			messages.add(context.concat("\n"));	
			for(String msg:messages) {
				message.reply(msg).submit();
			}
		} else {
			Console.out(Arrays.toString(pattern.split(message.getContentStripped())),true);
		}
		return null;
	}
}
