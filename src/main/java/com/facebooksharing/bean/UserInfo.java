package com.facebooksharing.bean;

import java.io.Serializable;

public class UserInfo  implements Serializable
{
	
	private String NA="NA";
	private String id;
	private String name;
	private String location;
	private String picture;
	
	
	public UserInfo(String id, String name, String location, String picture) {
		super();
		
		if(id==null)
		  id = "error";
		
		if(name==null)
			name = NA;
		
		if(location==null)
			location = "India";
		
		if(picture==null)
			picture = "images/use/anonymousperson.gif";
		
		
		this.id = id;
		this.name = name;
		this.location = location;
		this.picture = picture;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	
}
