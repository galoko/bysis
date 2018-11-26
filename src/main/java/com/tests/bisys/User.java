package com.tests.bisys;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "user")
public class User {
	
	private final long id; // Integer
	private final String name; // String
	private final int age; // Integer
	private final double height; // Number
	private final boolean sex; // Boolean
	private final List<String> favouriteColors; // Array
	private final Object reserved; // Null
	
	public User(long id, String name, int age, double height, boolean sex, List<String> favouriteColors) {
		
		this.id = id;
		this.name = name;
		this.age = age;
		this.height = height;
		this.sex = sex;		
		this.favouriteColors = favouriteColors != null ? favouriteColors : new ArrayList<String>();
		this.reserved = null;
	}
	
	public User() {
		this.id = 0;
		this.name = "";
		this.age = 0;
		this.height = 0.0;
		this.sex = false;		
		this.favouriteColors = new ArrayList<String>();	
		this.reserved = null;	
	}
	
	@XmlAttribute(name = "id")
	public long getId() {
		return id;
	}
	
	@XmlAttribute(name = "name")
	public String getName() {
		return name;
	}
	
	@XmlAttribute(name = "age")
	public int getAge() {
		return age;
	}
	
	@XmlAttribute(name = "height")
	public double getHeight() {
		return height;
	}
	
	@XmlAttribute(name = "sex")
	public boolean getSex() {
		return sex;
	}
	
	@XmlElementWrapper(name = "favcolors")
	@XmlElement(name = "favcolor")
	public List<String> getFavcolors() {
		return favouriteColors;
	}
	
	@XmlElement(name = "reserved")
	public Object getReserved() {
		return reserved;
	}
}