package com.gmail.berndivader.heewhomee.consolecommand;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import com.gmail.berndivader.heewhomee.HeeWhooMee;
import com.gmail.berndivader.heewhomee.annotations.ConsoleCommand;


public abstract class Command {
	
	private final static String PACKAGE_NAME="com/gmail/berndivader/heewhomee/consolecommand/commands";
	private static String fileName;
	protected static HashMap<String,Command>commands;
	
	static {
		try {
			fileName = URLDecoder.decode(
					HeeWhooMee.class.getProtectionDomain().getCodeSource().getLocation().getPath(),
					StandardCharsets.UTF_8.toString());
		} catch (UnsupportedEncodingException e) {
			try {
				fileName = URLDecoder.decode(
						HeeWhooMee.class.getProtectionDomain().getCodeSource().getLocation().getPath(),
						StandardCharsets.ISO_8859_1.toString());
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
		}
		try {
			loadClasses();
		} catch (ClassNotFoundException | IOException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		
	}
	
	private static void loadClasses() throws FileNotFoundException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		commands=new HashMap<>();
		try(JarInputStream jarStream=new JarInputStream(new FileInputStream(fileName))) {
			while(jarStream.available()==1) {
				JarEntry entry=jarStream.getNextJarEntry();
				if(entry!=null) {
					String clazzName=entry.getName();
					if(clazzName.endsWith(".class")&&clazzName.startsWith(PACKAGE_NAME)) {
						clazzName=clazzName.substring(0,clazzName.length()-6).replace("/",".");
						Class<?>clazz=Class.forName(clazzName);
						ConsoleCommand anno=clazz.getAnnotation(ConsoleCommand.class);
						if(anno!=null) {
							commands.put(anno.name(),(Command) clazz.getDeclaredConstructor().newInstance());
						}
					}
				}
			}
		}
	}
	
	public static Command getCommand(String name) {
		return commands.get(name);
	}
	
	public final String usage;
	public final String name;
	
	public Command() {
		this.name=this.getClass().getAnnotation(ConsoleCommand.class).name();
		this.usage=this.getClass().getAnnotation(ConsoleCommand.class).usage();		
	}
	
	public abstract void execute(String args);	
}
