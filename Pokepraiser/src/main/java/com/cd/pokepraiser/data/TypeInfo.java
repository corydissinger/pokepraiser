package com.cd.pokepraiser.data;

import java.io.Serializable;

public class TypeInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6695278596349082585L;

	private int dbId;
	private String name;
	public int getDbId() {
		return dbId;
	}
	public void setDbId(int dbId) {
		this.dbId = dbId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
