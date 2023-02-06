package com.gmail.berndivader.heewhomee;

import java.util.EnumSet;

import com.gmail.berndivader.heewhomee.ai.BotSession;
import com.gmail.berndivader.heewhomee.database.DiscordRequest;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.SelfUser;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.events.session.SessionDisconnectEvent;
import net.dv8tion.jda.api.events.session.SessionRecreateEvent;
import net.dv8tion.jda.api.events.session.SessionResumeEvent;
import net.dv8tion.jda.api.events.session.ShutdownEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Discord {
	
	public static Discord instance;
	public static boolean connected=false;
	private static int connectRetries=0;
	
	public BotSession aiSession;
	
	private final JDA jda;
	private SelfUser selfUser;
	
	private class Listener extends ListenerAdapter {
		
		@Override
		public void onReady(ReadyEvent event) {
			connectRetries=0;
			connected=true;
			Console.out("Connected to discord",true);
			jda.setAutoReconnect(true);
			selfUser=jda.getSelfUser();
		}
				
		@Override
		public void onSessionDisconnect(SessionDisconnectEvent event) {
			connected=false;
			Console.out("Connection to discord lost.");
		}
		
		@Override
		public void onSessionResume(SessionResumeEvent event) {
			connected=true;
			Console.out("Reconnected to discord",true);
		}
		
		@Override
		public void onSessionRecreate(SessionRecreateEvent event) {
			connected=true;
			Console.out("Recreated discord connection",true);
		}
		
		@Override
		public void onMessageReceived(MessageReceivedEvent event) {
			Message message=event.getMessage();
			String content=message.getContentRaw();
			char test=content.charAt(0);
			if(test=='!'&&content.length()>1) {
				Helper.executor.submit(new DiscordRequest(message,false));
			} else if(test=='@'&&message.getMentions().getUsers().contains(selfUser)) {
				Helper.executor.submit(new DiscordRequest(message,true));
			}
		}
		
		@Override
		public void onShutdown(ShutdownEvent event) {
			connected=false;
			if(!HeeWhooMee.quit) {
				createNewDiscordSession();
			}
		}
		
	}
	
	public static void createNewDiscordSession() {
		if(instance!=null) {
			instance.unregisterHandlers();
			instance.close();
			if(!connected&&connectRetries<5) {
				connectRetries++;
				instance=new Discord();
			}
		} else {
			instance=new Discord();
		}
	}

	public Discord() {
		connectRetries++;
		jda=JDABuilder.createLight(HeeWhooMee.config.token, EnumSet.of(
				GatewayIntent.DIRECT_MESSAGES,
				GatewayIntent.GUILD_MESSAGES,
				GatewayIntent.MESSAGE_CONTENT
			)).addEventListeners(new Listener()).setActivity(Activity.competing("is back again!")).build();
		
		if(HeeWhooMee.config.pandoraId!=null&&!HeeWhooMee.config.pandoraId.isEmpty()) {
			aiSession=new BotSession(HeeWhooMee.config.pandoraId);
		}
	}
	
	public static void setActivity(String str) {
		instance.jda.getPresence().setActivity(Activity.competing(str));
	}
	
	public void unregisterHandlers() {
		jda.cancelRequests();
		jda.removeEventListener(this);
	}
	
	public void close() {
		jda.shutdownNow();
	}

}
