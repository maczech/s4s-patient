package com.mcz.fusionsleep.location

class Location {
	String code
	String name
	
	String toString(){
		return name
	}
    
	static constraints = {
		
		code maxSize:10, blank: true, nullable: true
		name maxSize:80, blank: false, nullable: false
		
    }
}
