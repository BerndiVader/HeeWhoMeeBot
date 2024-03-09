package com.gmail.berndivader.heewhomee.consolecommand.commands;

import com.gmail.berndivader.heewhomee.HeeWhooMee;
import com.gmail.berndivader.heewhomee.consolecommand.Command;
import com.gmail.berndivader.heewhomee.annotations.ConsoleCommand;

@ConsoleCommand(name=".setdbpwd", usage="password for the sql user login")
public class SetSQLPwd extends Command {
	
	@Override
	protected void command(String args) {
		HeeWhooMee.config.dbPwd=args;
		
	}

}
