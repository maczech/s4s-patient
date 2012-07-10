package com.mcz.fusionsleep.domain

class Compilance {
	Patient patient
	Date date
	Status status
	String ahi
	Boolean excluded
	
	enum Status {
		COMPILANT, NON_COMPILANT, NOT_USED, PENDING
	}
	
    static constraints = {
    }
}
