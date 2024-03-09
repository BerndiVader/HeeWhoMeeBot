package com.gmail.berndivader.heewhomee.consolecommand.commands;

import com.gmail.berndivader.heewhomee.HeeWhooMee;
import com.gmail.berndivader.heewhomee.consolecommand.Command;
import com.gmail.berndivader.heewhomee.annotations.ConsoleCommand;

@ConsoleCommand(name=".setdbport", usage="port for the db server")
public class SetSQLPort extends Command {
	
	@Override
	protected void command(String args) {
		if(args.matches("[0-9]+")) {
			HeeWhooMee.config.dbPort=Integer.parseInt(args);
		}
		
	}

}
