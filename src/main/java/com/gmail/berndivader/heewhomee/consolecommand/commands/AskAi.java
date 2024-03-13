package com.gmail.berndivader.heewhomee.consolecommand.commands;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.gmail.berndivader.heewhomee.Console;
import com.gmail.berndivader.heewhomee.HeeWhooMee;
import com.gmail.berndivader.heewhomee.Helper;
import com.gmail.berndivader.heewhomee.ai.BotSession;
import com.gmail.berndivader.heewhomee.annotations.ConsoleCommand;
import com.gmail.berndivader.heewhomee.consolecommand.Command;

import io.github.furstenheim.CopyDown;

@ConsoleCommand(name=".askai",usage="ask pandora bot a question")
public class AskAi extends Command {
	
	private CopyDown converter;
	
	private static BotSession botSession;
	
	static {
		botSession=new BotSession(HeeWhooMee.config.pandoraId);
	}
	
	public AskAi() {
		converter=new CopyDown();
	}
	
	@Override
	protected void command(String args) {
		if(args.isBlank()||!botSession.useable) {
			if(args.isBlank()) {
				Console.out("Blank questions are not allowed.");
			}
			if(!botSession.useable) {
				Console.out("Bot is not useable.");
			}
			return;
		}
		Future<String>future=Helper.executor.submit(new Callable<String>() {
			@Override
			public String call() throws Exception {
				return getAiResult(args);
			}
		});
		
		try {
			String answer=future.get(20,TimeUnit.SECONDS);
			Console.out(answer);
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			e.printStackTrace();
		}		
	}
	
	private String getAiResult(String contentRaw) {
		String answer="";
		try {
			answer=converter.convert(botSession.think(contentRaw));
		} catch (Exception e) {
			Console.err(e.getMessage(),true,e);
		}
		return answer;
	}

}
