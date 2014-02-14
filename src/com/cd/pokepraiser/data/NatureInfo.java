package com.cd.pokepraiser.data;

import java.io.Serializable;

public class NatureInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6300386018747564524L;

	private int id;
	private String name;
	private int plus;
	private int minus;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPlus() {
		return plus;
	}
	public void setPlus(int plus) {
		this.plus = plus;
	}
	public int getMinus() {
		return minus;
	}
	public void setMinus(int minus) {
		this.minus = minus;
	}
	
}
