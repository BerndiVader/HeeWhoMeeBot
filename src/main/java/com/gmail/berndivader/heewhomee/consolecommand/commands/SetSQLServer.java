package com.gmail.berndivader.heewhomee.consolecommand.commands;

import com.gmail.berndivader.heewhomee.HeeWhooMee;
import com.gmail.berndivader.heewhomee.consolecommand.Command;
import com.gmail.berndivader.heewhomee.annotations.ConsoleCommand;

@ConsoleCommand(name=".setdbserver", usage="url to dbserver like 'sql.server.my'")
public class SetSQLServer extends Command {
	
	@Override
	protected void command(String args) {
		HeeWhooMee.config.dbServer=args;
		
	}

}
