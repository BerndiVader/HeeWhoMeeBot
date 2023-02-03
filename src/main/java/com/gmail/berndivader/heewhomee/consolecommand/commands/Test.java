package com.gmail.berndivader.heewhomee.consolecommand.commands;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.gmail.berndivader.heewhomee.Console;
import com.gmail.berndivader.heewhomee.Helper;
import com.gmail.berndivader.heewhomee.Helper.DBSTATUS;
import com.gmail.berndivader.heewhomee.consolecommand.Command;
import com.gmail.berndivader.heewhomee.annotations.ConsoleCommand;
import com.gmail.berndivader.heewhomee.database.Worker;

@ConsoleCommand(name=".test", usage="test sql server and database connections")
public class Test extends Command {
	
	private class TestConnection extends Worker<DBSTATUS> {

		@Override
		public DBSTATUS call() throws Exception {
			return Helper.testDatabaseConnection();
		}
		
	}

	@Override
	public void execute(String args) {
		if(!args.isEmpty()&&args.charAt(0)=='?') {
			Console.out(usage);
			return;
		}
		Console.out("Test connection to mysql database....");
		Future<DBSTATUS>future=Helper.executor.submit(new TestConnection());
		try {
			Console.out("Test completed with status "+future.get(20,TimeUnit.SECONDS).name());
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			Console.out("Test timeouted by unknown reason.");
		}
	}

}
