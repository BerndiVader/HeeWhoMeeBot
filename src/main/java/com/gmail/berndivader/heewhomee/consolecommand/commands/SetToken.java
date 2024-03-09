package com.gmail.berndivader.heewhomee.consolecommand.commands;

import com.gmail.berndivader.heewhomee.HeeWhooMee;
import com.gmail.berndivader.heewhomee.consolecommand.Command;
import com.gmail.berndivader.heewhomee.annotations.ConsoleCommand;

@ConsoleCommand(name=".settoken",usage=".settoken <token> - set the discord token.")
public class SetToken extends Command {

	public SetToken() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void command(String args) {	
		HeeWhooMee.config.token=name;
	}

}
