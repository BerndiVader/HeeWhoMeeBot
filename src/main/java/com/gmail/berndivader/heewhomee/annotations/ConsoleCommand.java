package com.gmail.berndivader.heewhomee.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ConsoleCommand {
	public String name();
	public String usage();
}