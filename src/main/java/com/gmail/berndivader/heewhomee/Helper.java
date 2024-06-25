package com.gmail.berndivader.heewhomee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Helper {
	
	public enum DBSTATUS {
		OK,
		SERVER_CONNECTION_FAILED,
		DB_NOT_FOUND,
		DB_CONNECTION_FAILED,
		UNKNOWN
	}
	
	public static ExecutorService executor;
	public static ScheduledExecutorService scheduler;
		
	static {
		scheduler=Executors.newScheduledThreadPool(2);
		executor=Executors.newCachedThreadPool();
	}
	
	public static Connection getNewDatabaseConnection() throws SQLException {
		StringBuilder builder=new StringBuilder();
		builder.append("jdbc:mysql://".concat(HeeWhooMee.config.dbServer));
		builder.append(":".concat(Integer.toString(HeeWhooMee.config.dbPort)));
		builder.append("/".concat(HeeWhooMee.config.dbName));
		return DriverManager.getConnection(builder.toString(), HeeWhooMee.config.dbUsr, HeeWhooMee.config.dbPwd);
	}
	
	public static DBSTATUS testDatabaseConnection() {
		DBSTATUS status=DBSTATUS.OK;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			status=DBSTATUS.SERVER_CONNECTION_FAILED;
		}
				
		if(status==DBSTATUS.OK) {
			try(Connection connection=getNewDatabaseConnection()) {
				try(PreparedStatement statement=connection.prepareStatement("SHOW DATABASES LIKE'".concat(HeeWhooMee.config.dbName).concat("';")
						,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY)) {
					try(ResultSet result=statement.executeQuery()) {
						result.first();
						if(result.getRow()==0) {
							status=DBSTATUS.DB_NOT_FOUND;
						}
					}
				}				
			} catch (SQLException e) {
				status=DBSTATUS.DB_CONNECTION_FAILED;
				e.printStackTrace();
			}
		}
		return status;
	}

}
