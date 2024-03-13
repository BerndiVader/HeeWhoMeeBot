package com.gmail.berndivader.heewhomee;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import com.gmail.berndivader.heewhomee.Helper.DBSTATUS;
import com.gmail.berndivader.heewhomee.annotations.Arg;

public class HeeWhooMee {
	
	public static String token;
	public static ConfigFile config;
	public static boolean autoConnect=false, quit=false, debug=false;
	
	public static void main(String[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {
		
		config=new ConfigFile();
		ConfigFile.Init();

		processArgs(args);
		
		new Console();
		new Cooldowner();
		
		DBSTATUS dbStatus=Helper.testDatabaseConnection();
		if(dbStatus!=DBSTATUS.OK) {
			Console.err("There is a problem connecting to the sql server. Error: ".concat(dbStatus.name()),null);
		}
		
		Discord.createNewDiscordSession();
	}
	
	private static void processArgs(String[]args) {
		Method[]methods=HeeWhooMee.class.getDeclaredMethods();
		HashMap<String,Method>commands=new HashMap<>();
		for(int i=0;i<methods.length;i++) {
			Method method=methods[i];
			if(method.getDeclaredAnnotation(Arg.class)!=null) {
				commands.put(method.getName(),method);
			}
		}
		for(int i=0;i<args.length;i++) {
			if(!args[i].isEmpty()&&args[i].charAt(0)=='-') {
				String arg=args[i].substring(1);
				
				if(commands.containsKey(arg)) {
					int j;
					for(j=i+1;j<args.length;j++) {
						if(args[j].charAt(0)=='-') {
							break;
						}
					}
					j-=i+1;
					Object[]params=new String[j];
					System.arraycopy(args, i+1, params, 0, j);
					
					Method method=commands.get(arg);
					try {
						method.invoke(null,params);
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						Console.err("ERROR while processing argument: ".concat(arg),e);
					}
				} else {
					Console.err("UNKNOWN argument: ".concat(arg),null);
				}
			}
		}
	}
	
	@Arg
	private static void token(String...params) {
		if(params.length>0) {
			token=params[0];
			Console.out("Token set to: ".concat(token));
		} else {
			Console.err("Token not set, because not found. Usage: -t <token>",null);
		}
	}
	
	@Arg
	private static void config(String...params) {
		if(params.length>0) {
			
		} else {
			Console.err("No config file set. Usage: -c <configfile>",null);
		}
	}
	
	@Arg
	private static void auto(String...params) {
		autoConnect=true;
	}
	
	@Arg
	private static void debug(String...params) {
		debug=true;
	}

}
