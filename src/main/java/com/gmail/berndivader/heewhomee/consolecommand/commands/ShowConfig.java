package com.gmail.berndivader.heewhomee.consolecommand.commands;

import com.gmail.berndivader.heewhomee.ConfigFile;
import com.gmail.berndivader.heewhomee.Console;
import com.gmail.berndivader.heewhomee.consolecommand.Command;
import com.gmail.berndivader.heewhomee.annotations.ConsoleCommand;

@ConsoleCommand(name=".showconfig", usage="view current configuration")
public class ShowConfig extends Command {

	@Override
	public void execute(String args) {
		if(!args.isEmpty()&&args.charAt(0)=='?') {
			Console.out(usage);
			return;
		}		
		Console.out(ConfigFile.toJson());
		
	}

}
