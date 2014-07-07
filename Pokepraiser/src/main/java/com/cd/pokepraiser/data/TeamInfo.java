package com.cd.pokepraiser.data;

import java.io.Serializable;

public class TeamInfo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 634141047221636660L;
	
	private String name;
	private int count;
	private int dbId;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getDbId() {
		return dbId;
	}
	public void setDbId(int dbId) {
		this.dbId = dbId;
	}
	
}
