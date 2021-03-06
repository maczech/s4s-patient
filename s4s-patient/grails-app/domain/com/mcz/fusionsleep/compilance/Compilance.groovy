package com.mcz.fusionsleep.compilance

import com.mcz.fusionsleep.patient.Patient;

class Compilance {
	Patient patient
	Date date
	Status status
	Integer ahi
	Boolean excluded = false
	
	enum Status {
		COMPILANT, NON_COMPILANT, NOT_USED, PENDING
	}
	
    static constraints = {
		
		ahi blank: true, nullable: true
    }

}
