package com.gmail.berndivader.heewhomee.consolecommand.commands;

import java.io.IOException;

import com.gmail.berndivader.heewhomee.ConfigFile;
import com.gmail.berndivader.heewhomee.consolecommand.Command;
import com.gmail.berndivader.heewhomee.annotations.ConsoleCommand;

@ConsoleCommand(name=".save",usage="save the current config to the default config.json file")
public class SaveConfig extends Command {

	public SaveConfig() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void command(String args) {
		try {
			ConfigFile.save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
