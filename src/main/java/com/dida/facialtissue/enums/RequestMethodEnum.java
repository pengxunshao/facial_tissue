package com.dida.facialtissue.enums;

public enum RequestMethodEnum
{
    GET(0,"GET"),
	POST(1,"POST");
	
	final private int id;
    final private String name;

    RequestMethodEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }
    
    public String getName() {
    	return name;
    }
}
