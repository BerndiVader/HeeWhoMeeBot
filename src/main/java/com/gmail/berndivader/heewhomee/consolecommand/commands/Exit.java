package com.gmail.berndivader.heewhomee.consolecommand.commands;

import com.gmail.berndivader.heewhomee.Discord;
import com.gmail.berndivader.heewhomee.HeeWhooMee;
import com.gmail.berndivader.heewhomee.Helper;
import com.gmail.berndivader.heewhomee.annotations.ConsoleCommand;
import com.gmail.berndivader.heewhomee.consolecommand.Command;

@ConsoleCommand(name=".quit",usage="exit the bot.")
public class Exit extends Command{

	@Override
	protected void command(String arg) {

		HeeWhooMee.quit=true;
		Discord.instance.close();
		Helper.scheduler.shutdown();
		Helper.executor.shutdown();
		System.out.println("Bye.....");
	}

}
