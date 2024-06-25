package com.gmail.berndivader.heewhomee.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.Callable;
import java.util.regex.Pattern;

import com.gmail.berndivader.heewhomee.Console;
import com.gmail.berndivader.heewhomee.Discord;
import com.gmail.berndivader.heewhomee.HeeWhooMee;
import com.gmail.berndivader.heewhomee.Helper;

import io.github.furstenheim.CopyDown;

public abstract class Worker<T> implements Callable<T> {
	
	private CopyDown converter;	
	public String lastQuestion;
	
	protected static Pattern pattern=Pattern.compile("@(.*?) ");
	
	protected String query;
	protected String table;
	protected String filter;
	protected StringBuilder builder;
		
	public Worker() {
		lastQuestion="";
		converter=new CopyDown();
	}
	
	protected String getSQLResult(String[]temp) {
		if(temp.length>0) {
			table=temp[0].trim();
			filter=temp.length>1?temp[1].trim().substring(0,temp[1].length()<HeeWhooMee.config.maxQuestionSize
					?temp[1].length():HeeWhooMee.config.maxQuestionSize):"";
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
				case 'p':
					table="priority";
					break;
				default:
					table="help";
					filter=filter.isEmpty()||filter.charAt(0)!='d'?"Commands":"Downloads";
					break;
			}
			lastQuestion="!".concat(table).concat(" ").concat(filter);
		}
		
		builder=new StringBuilder();
		
		try(Connection connection=Helper.getNewDatabaseConnection()) {
			boolean secondRun=false;
			while(true) {
				String sql="select * from "+table+" where name like '"+filter+"' order by name ASC";
				try(PreparedStatement statement=connection.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY)) {
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
						builder.append("Sorry, nothing found. ");
						break;
					}
				}
			}
		} catch (SQLException e) {
			Console.err(e.getMessage(),true,e);
		}
		return builder.toString();
	}
	
	protected String getAiResult(String contentRaw) {
		String[]parse=pattern.split(contentRaw.trim());
		String answer="";
		if(parse.length>1) {
			String question=parse[1].trim();
			if(question.length()>0) {
				try {
					answer=converter.convert(Discord.instance.aiSession.think(question));
				} catch (Exception e) {
					Console.err(e.getMessage(),true,e);
				}
			}
		}
		return answer;
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
				builder.append(result.getString("syntax").concat("```\n"));
			} else {
				builder.append("[Syntax:](").append(result.getString("syntax")).append(")\n\nUsage:\n======\n").append(result.getString("description"))
					.append("\n[Dependings:](").append(result.getString("addon")).append(")```\n");
			}
		}
	}
	

	@Override
	public abstract T call() throws Exception;
}
