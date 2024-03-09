package com.gmail.berndivader.heewhomee.consolecommand.commands;

import java.io.IOException;

import com.gmail.berndivader.heewhomee.ConfigFile;
import com.gmail.berndivader.heewhomee.consolecommand.Command;
import com.gmail.berndivader.heewhomee.annotations.ConsoleCommand;

@ConsoleCommand(name=".load",usage="load the config.json file.")
public class LoadConfig extends Command {

	public LoadConfig() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void command(String args) {
		try {
			ConfigFile.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
