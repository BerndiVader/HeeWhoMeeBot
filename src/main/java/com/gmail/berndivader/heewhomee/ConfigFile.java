package com.gmail.berndivader.heewhomee;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ConfigFile {
	private static final String NAME="config.json";
	private static Gson gson;
	public String token="discordtoken";
	public int discordMaxReconnect=5;
	public String pandoraId="pandora_bot_id_if_used";
	public String sqlDriver="com.mysql.cj.jdbc.Driver";
	public String dbServer="your.sqlserver.name";
	public int dbPort=9999;
	public String dbName="sql_db_name";
	public String dbUsr="sql_login_name";
	public String dbPwd="sql_password";
	
	public static void Init() throws IOException {
		gson=new GsonBuilder().setPrettyPrinting().create();
		File file=new File(NAME);
		if(!file.exists()) save();
		load();
	}
	
	public static void save() throws IOException {
		try(FileWriter writer=new FileWriter(NAME)) {
			gson.toJson(HeeWhooMee.config,writer);
		}
	}
	
	public static void load() throws FileNotFoundException, IOException {
		loadCustom(NAME);
	}
	
	public static void loadCustom(String filename) throws FileNotFoundException, IOException {
		try(FileReader reader=new FileReader(filename)) {
			HeeWhooMee.config=gson.fromJson(reader,ConfigFile.class);
		}		
	}
	
	public static String toJson() {
		return gson.toJson(HeeWhooMee.config);
	}

}
