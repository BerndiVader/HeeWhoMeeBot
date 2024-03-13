package com.gmail.berndivader.heewhomee;

import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import com.gmail.berndivader.heewhomee.consolecommand.Command;

public class Console implements Runnable {
	
	private static final Scanner keyboard=new Scanner(System.in);
	
	public Console() {
		Helper.scheduler.scheduleAtFixedRate(this,5l,5l,TimeUnit.MILLISECONDS);
	}

	@Override
	public void run() {
		prompt();
		String input = keyboard.nextLine();
        
        if(!input.isBlank()&&input.startsWith(".")) {
        	String[]args=input.split(" ",2);
        	String cmd=args[0];
        	if(!Command.hasCommand(cmd)) {
        		err("UNKNOWN COMMAND: ".concat(input),null);
        		return;
        	}
        	String arg="";
        	if(args.length==2) {
        		arg=args[1];
        	}
			try {
				Command command=Command.getCommand(cmd).getDeclaredConstructor().newInstance();
            	command.execute(arg);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
        		err("ERROR with CMD: ".concat(cmd.concat(" - with message:")).concat(e.getMessage()),e);
        	}
        }
	}
	
	public static void out(String output) {
		out(output,false);
		
	}
	
	public static void err(String output,Exception e) {
		err(output,false,e);
	}
	
	public static void out(String output,boolean prompt) {
		if(output!=null) {
			System.out.println(output);
		}
		if(prompt) {
			prompt();
		}
	}
	
	public static void err(String output,boolean prompt,Exception e) {
		if(output!=null) {
			System.err.println(output);
		}
		if(HeeWhooMee.debug&&e!=null) {
			e.printStackTrace();
		}
		if(prompt) {
			prompt();
		}
	}
	
	public static void prompt() {
        System.out.print("$ "); 
	}
	
}
