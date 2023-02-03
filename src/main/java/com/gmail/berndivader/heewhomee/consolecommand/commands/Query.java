package com.gmail.berndivader.heewhomee.consolecommand.commands;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.gmail.berndivader.heewhomee.Console;
import com.gmail.berndivader.heewhomee.Helper;
import com.gmail.berndivader.heewhomee.annotations.ConsoleCommand;
import com.gmail.berndivader.heewhomee.consolecommand.Command;
import com.gmail.berndivader.heewhomee.database.ConsoleRequest;

@ConsoleCommand(name=".query",usage="make a mysql request")
public class Query extends Command {
		
	@Override
	public void execute(String arg) {
		if(arg.isEmpty()||arg.charAt(0)=='?') {
			Console.out(usage);
			return;
		}
		
		Future<String>future=Helper.executor.submit(new ConsoleRequest(arg));
		try {
			String message=future.get(20,TimeUnit.SECONDS);
			Console.out(message);
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			e.printStackTrace();
		}
	}

}
