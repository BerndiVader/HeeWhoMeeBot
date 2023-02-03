package com.gmail.berndivader.heewhomee.consolecommand.commands;

import com.gmail.berndivader.heewhomee.Console;
import com.gmail.berndivader.heewhomee.HeeWhooMee;
import com.gmail.berndivader.heewhomee.consolecommand.Command;
import com.gmail.berndivader.heewhomee.annotations.ConsoleCommand;

@ConsoleCommand(name=".setdbserver", usage="url to dbserver like 'sql.server.my'")
public class SetSQLServer extends Command {
	
	@Override
	public void execute(String args) {
		if(args.isEmpty()||args.charAt(0)=='?') {
			Console.out(usage);
			return;
		}
		HeeWhooMee.config.dbServer=args;
		
	}

}
