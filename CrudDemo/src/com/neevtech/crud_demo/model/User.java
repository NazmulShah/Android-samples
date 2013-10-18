package com.neevtech.crud_demo.model;

import java.io.Serializable;

/**
 * @author prashant
 * 
 */
public class User implements Serializable {

	private static final long serialVersionUID = 6283189112237038571L;
	
	private int id;
	private String name;
	private String phoneNo;
	private String address;

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

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
