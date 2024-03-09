package com.gmail.berndivader.heewhomee.consolecommand.commands;

import com.gmail.berndivader.heewhomee.HeeWhooMee;
import com.gmail.berndivader.heewhomee.consolecommand.Command;
import com.gmail.berndivader.heewhomee.annotations.ConsoleCommand;

@ConsoleCommand(name=".setpandoraid", usage="id to the pandora id for the ai bot feature")
public class SetPandoraID extends Command {
	
	@Override
	protected void command(String args) {
		HeeWhooMee.config.pandoraId=args;
		
	}

}
