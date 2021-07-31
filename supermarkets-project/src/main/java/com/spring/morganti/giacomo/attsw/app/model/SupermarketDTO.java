package com.spring.morganti.giacomo.attsw.app.model;

import java.math.BigInteger;

public class SupermarketDTO {

	private BigInteger id;
	private String name;
	private String address;
	
	public SupermarketDTO (BigInteger id, String name, String address) {
		this.id = id;
		this.name = name;
		this.address = address;
	}
		
	public SupermarketDTO() {

	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
}
