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
        
        if(input!=null&&!input.isEmpty()) {
        	String[]args=input.split(" ",2);
        	String cmd=args[0];
        	String arg="";
        	if(args.length==2) {
        		arg=args[1];
        	}
        	Command command=null;
			try {
				command=Command.getCommand(cmd).getDeclaredConstructor().newInstance();
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
        	if(command!=null) {
            	command.execute(arg);
        	} else {
        		err("UNKNOWN COMMAND: ".concat(input));
        	}
        }
	}
	
	public static void out(String output) {
		out(output,false);
		
	}
	
	public static void err(String output) {
		err(output,false);
	}
	
	public static void out(String output,boolean prompt) {
		if(output!=null) {
			System.out.println(output);
		}
		if(prompt) {
			prompt();
		}
	}
	
	public static void err(String output,boolean prompt) {
		if(output!=null) {
			System.err.println(output);
		}
		if(prompt) {
			prompt();
		}
	}
	
	public static void prompt() {
        System.out.print("$ "); 
	}
	
}
