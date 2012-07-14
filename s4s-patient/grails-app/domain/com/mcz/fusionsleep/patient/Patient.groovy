package com.mcz.fusionsleep.patient

import com.mcz.fusionsleep.location.Location;

class Patient {
	
	
	String firstName
	String middleName
	String lastName
	String medicalRecordNumber
	Date dateOfBirth
	Gender gender
	Status status
	Location location
	
	Boolean deleted = false;
	
	
	enum Gender{
		UNDISCLOSED, MALE, FEMALE	
	}
	
	enum Status{
		INITIAL, REFERRED, TREATMENT, CLOSED
	}
	
	static transients = ["medicalRecordNumber"]
	
    static constraints = {
    	firstName maxSize:30, blank: false, nullable: false
		middleName maxSize:10, blank: true, nullable: true
		lastName maxSize:30, blank: false, nullable: false
		gender blank: false, nullable: false
		status blank: false, nullable: false
		location blank: false, nullable: false
		
		dateOfBirth nullable:true
		
    }
	
	static namedQueries = {
		onTreatment {
			eq 'status', Status.TREATMENT
		}
	}
	
	static hibernateFilters = {
		enabledFilter(condition: "deleted='false'", default: true)
	}
	
	def onLoad() {
		this.@medicalRecordNumber = String.format('MR%06d',id)
	}
	

	

}
