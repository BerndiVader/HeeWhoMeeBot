package com.gmail.berndivader.heewhomee.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.Callable;

import com.gmail.berndivader.heewhomee.Helper;

public abstract class Worker<T> implements Callable<T> {
	
	protected String query;
	
	protected String table;
	protected String filter;
	protected StringBuilder builder;
		
	protected String getResult(String[]temp) {
		if(temp.length>0) {
			table=temp[0].trim();
			filter=temp.length>1?temp[1].trim():"";
			switch(table.charAt(0)) {
				case 's':
					table="skills";
					break;
				case 'c':
					table="conditions";
					break;
				case 't':
					table="targeters";
					break;
				case 'g':
					table="goals";
					break;
				default:
					table="help";
					filter="Commands";
					break;
			}
		}
		
		builder=new StringBuilder();
		
		try(Connection connection=Helper.getNewDatabaseConnection()) {
			boolean secondRun=false;

			while(true) {
				String sql="select * from ".concat(table).concat(" where name like '").concat(filter).concat("' order by name ASC;");
				try(PreparedStatement statement=connection.prepareStatement(sql)) {
					try(ResultSet result=statement.executeQuery()) {
						int hits=0;
						if(result.last()) {
							hits=result.getRow();
							result.beforeFirst();
							if(hits==1) {
								matchOne(result);
							} else {
								matchMore(result,hits);
							}
							break;
						} else if(!secondRun) {
							secondRun=true;
							filter="%".concat(filter).concat("%");
							continue;
						}
						builder.append("Sorry, nothing found.");
						break;
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return builder.toString();
	}
	
	private void matchMore(ResultSet result, int hits) throws SQLException {
		builder.append("**").append(hits).append(" hits!** Need details? Precise your request! You might search for:```\n");
		int c=1;
		while (result.next()) {
			builder.append(result.getString("name")).append(c<hits?", ":".```\n");
			c++;
		}
	}
	
	private void matchOne(ResultSet result) throws SQLException {
		if (result.next()) {
			builder.append("Here we go:\n```Markdown\n#").append(result.getString("name")).append("\n#");
			if (table.equals("help")) {
				builder.append(result.getString("syntax"));
			} else {
				builder.append("[Syntax:](").append(result.getString("syntax")).append(")\n\nUsage:\n======\n").append(result.getString("description"))
					.append("\n[Dependings:](").append(result.getString("addon")).append(")```");
			}
		}
	}
	

	@Override
	public abstract T call() throws Exception;
}
