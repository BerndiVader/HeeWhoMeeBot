package com.gmail.berndivader.heewhomee.database;

import java.util.ArrayList;

import com.gmail.berndivader.heewhomee.Discord;

import net.dv8tion.jda.api.entities.Message;

public class DiscordRequest extends Worker<Void> {
	
	private static int size=1900;
	private final Message message;
	private final boolean bot;
			
	public DiscordRequest(Message m,boolean bot) {
		super();
		message=m;
		this.bot=bot;
	}

	@Override
	public Void call() throws Exception {
		ArrayList<String>messages=new ArrayList<>();
		if(!bot) {
			String context=getSQLResult(message.getContentRaw().substring(1).trim().toLowerCase().split(" ",2));
			if(context.length()>size) {
				while (context.length()>size) {
					messages.add(context.substring(0, size).concat("```"));
					context=context.substring(size, context.length());
					context="```Markdown\n"+context.trim();
				}
			}
			messages.add(context.concat("More info? Try *!help*"));	
			for(String msg:messages) {
				message.reply(msg).submit();
			}
			Discord.setActivity(lastQuestion);
			
		} else if(Discord.instance.aiSession.useable) {
			String answer=getAiResult(message);
			if(!answer.isEmpty()) {
				message.reply(answer).submit();
			}
		}
		return null;
	}
}
