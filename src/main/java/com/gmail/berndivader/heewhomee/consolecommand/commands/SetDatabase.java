package com.gmail.berndivader.heewhomee.consolecommand.commands;

import com.gmail.berndivader.heewhomee.HeeWhooMee;
import com.gmail.berndivader.heewhomee.consolecommand.Command;
import com.gmail.berndivader.heewhomee.annotations.ConsoleCommand;

@ConsoleCommand(name=".setdbname",usage=".setdbname <databasename>")
public class SetDatabase extends Command {

	public SetDatabase() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void command(String args) {	
		HeeWhooMee.config.dbName=name;
	}

}
