package com.gmail.berndivader.heewhomee.database;

public class ConsoleRequest extends Worker<String> {
	
	private String args;
			
	public ConsoleRequest(String arg) {
		args=arg;
	}

	@Override
	public String call() throws Exception {
		return getResult(args.toLowerCase().split(" ", 2));
	}

}
