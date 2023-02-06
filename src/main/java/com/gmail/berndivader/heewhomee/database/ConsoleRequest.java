package com.gmail.berndivader.heewhomee.database;

public class ConsoleRequest extends Worker<String> {
	
	private String args;
			
	public ConsoleRequest(String arg) {
		super();
		args=arg;
	}

	@Override
	public String call() throws Exception {
		return getSQLResult(args.toLowerCase().split(" ", 2));
	}

}
