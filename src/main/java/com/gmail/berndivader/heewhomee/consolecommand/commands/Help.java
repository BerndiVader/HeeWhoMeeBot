package com.gmail.berndivader.heewhomee.consolecommand.commands;

import java.util.function.BiConsumer;

import com.gmail.berndivader.heewhomee.Console;
import com.gmail.berndivader.heewhomee.annotations.ConsoleCommand;
import com.gmail.berndivader.heewhomee.consolecommand.Command;

@ConsoleCommand(name=".help",usage="show all cli commands")
public class Help extends Command {
	
	private BiConsumer<String,Command>consumer;
	
	public Help() {
		consumer=new BiConsumer<String, Command>() {
			
			@Override
			public void accept(String name, Command command) {
				Console.out(name.concat(": ").concat(command.usage));
			}
		};
	}

	@Override
	protected void command(String arg) {
		commands.forEach(consumer);
	}

}
