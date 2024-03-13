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
	public static DBSTATUS dbStatus;
	public static boolean autoConnect=false, quit=false, debug=false;
	
	public static void main(String[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {
		
		config=new ConfigFile();
		ConfigFile.Init();

		args(args);
		
		dbStatus=Helper.testDatabaseConnection();
		
		new Console();
		new Cooldowner();
		
		if(dbStatus!=DBSTATUS.OK) {
			Console.err("There is a problem connecting to the sql server. Error: ".concat(dbStatus.name()),null);
		}
		
		Discord.createNewDiscordSession();
	}
	
	private static void args(String[]args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method[]methods=HeeWhooMee.class.getDeclaredMethods();
		HashMap<String,Method>commands=new HashMap<>();
		for(Method m:methods) {
			if(m.getAnnotation(Arg.class)!=null) {
				commands.put(m.getName(),m);
			}
		}
		for(int i=0;i<args.length;i++) {
			if(!args[i].isEmpty()&&args[i].charAt(0)=='-') {
				String cmd=args[i].substring(1);
				if(commands.containsKey(cmd)) {
					int j;
					for(j=i+1;j<args.length;j++) {
						if(args[j].charAt(0)=='-') {
							break;
						}
					}
					j-=i+1;
					String[]params=new String[j];
					System.arraycopy(args, i+1, params, 0, j);
					commands.get(cmd).invoke(null,new Object[] {params});
				} else {
					Console.err("UNKNOWN COMMAND: ".concat(cmd),null);
				}
			}
		}
	}
	
	@Arg
	static void token(String...params) {
		if(params.length>0) {
			token=params[0];
			Console.out("Token set to: ".concat(token));
		} else {
			Console.err("Token not set, because not found. Usage: -t <token>",null);
		}
	}
	
	@Arg
	static void config(String...params) {
		if(params.length>0) {
			
		} else {
			Console.err("No config file set. Usage: -c <configfile>",null);
		}
	}
	
	@Arg
	static void auto(String...params) {
		autoConnect=true;
	}
	
	@Arg
	static void debug(String...params) {
		debug=true;
	}

}
