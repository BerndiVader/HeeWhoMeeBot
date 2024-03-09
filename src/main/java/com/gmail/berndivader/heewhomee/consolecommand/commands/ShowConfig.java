package com.gmail.berndivader.heewhomee.consolecommand.commands;

import com.gmail.berndivader.heewhomee.ConfigFile;
import com.gmail.berndivader.heewhomee.Console;
import com.gmail.berndivader.heewhomee.consolecommand.Command;
import com.gmail.berndivader.heewhomee.annotations.ConsoleCommand;

@ConsoleCommand(name=".showconfig", usage="view current configuration")
public class ShowConfig extends Command {

	@Override
	protected void command(String args) {	
		Console.out(ConfigFile.toJson());
		
	}

}
