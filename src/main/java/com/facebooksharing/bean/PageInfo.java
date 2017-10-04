package com.facebooksharing.bean;

import java.io.Serializable;

public class PageInfo implements Serializable
{
	private String access_token;
	private String name;
	private String id;
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public PageInfo(String access_token, String name, String id)
	{
		super();
		this.access_token = access_token;
		this.name = name;
		this.id = id;
	}
}
