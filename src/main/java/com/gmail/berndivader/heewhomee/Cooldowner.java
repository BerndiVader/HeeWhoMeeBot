package com.gmail.berndivader.heewhomee;

import java.util.Iterator;
import java.util.Map.Entry;
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
		
		long timestamp=System.currentTimeMillis();
		
		Iterator<Entry<Long,Long>>iterator=questers.entrySet().iterator();
		while(iterator.hasNext()) {
			Entry<Long,Long>entry=iterator.next();
			if(timestamp-entry.getValue()>HeeWhooMee.config.msgCooldownMilli) {
				iterator.remove();
			}
		}
	}
	
	public boolean onCooldown(long id) {
		if(questers.containsKey(id)) {
			return true;
		}
		questers.put(id,System.currentTimeMillis());
		return false;
	}
	
}
