package com.gmail.berndivader.heewhomee;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class Cooldowner implements Runnable {
	
	public static Cooldowner instance;
	private ConcurrentHashMap<Long,Long>questers;

	public Cooldowner() {
		questers=new ConcurrentHashMap<Long,Long>();
		instance=this;
		Helper.scheduler.scheduleAtFixedRate(this,0L,1L,TimeUnit.SECONDS);
	}

	@Override
	public void run() {
		long currentTime=System.currentTimeMillis();
		questers.values().removeIf(time->{
			return currentTime-time>HeeWhooMee.config.msgCooldownMilli;
		});
	}
	
	public boolean onCooldown(long id) {
		if(questers.containsKey(id)) {
			return true;
		}
		questers.put(id,System.currentTimeMillis());
		return false;
	}
	
}
