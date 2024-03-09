package com.gmail.berndivader.heewhomee.consolecommand.commands;

import com.gmail.berndivader.heewhomee.HeeWhooMee;
import com.gmail.berndivader.heewhomee.consolecommand.Command;
import com.gmail.berndivader.heewhomee.annotations.ConsoleCommand;

@ConsoleCommand(name=".setdbusr", usage="user login name for the sql server")
public class SetSQLUser extends Command {
	
	@Override
	protected void command(String args) {
		HeeWhooMee.config.dbUsr=args;
		
	}

}
