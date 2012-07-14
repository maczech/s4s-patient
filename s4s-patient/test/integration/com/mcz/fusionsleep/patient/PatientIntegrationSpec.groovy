package com.mcz.fusionsleep.patient

import grails.plugin.spock.IntegrationSpec;
import com.mcz.fusionsleep.location.Location;

class PatientIntegrationSpec extends IntegrationSpec {
	
	
	def sessionFactory
	
	def setup() {
		Patient.list()*.delete()
	}
	
	def "should medical record number be filled after load"() {
		setup:
		def location = new Location(code:"1",name:"Test location 1").save(failOnError:true)
		
		when:
		def patient = new Patient(firstName:firstName,lastName:lastName,gender:gender,status: status,location:location)
					.save(failOnError:true,flush:true)
		sessionFactory.currentSession.clear()
		then:
		assert Patient.findById(patient.id).medicalRecordNumber == "MR00000"+patient.id;
		
		where:
		firstName="Marcin"
		lastName="Czech"
		gender = Patient.Gender.MALE
		status = Patient.Status.INITIAL
		
			
	}
	
	def "should return only not deleted Patients"() {
		setup:
		def location = new Location(code:"1",name:"Test location 1").save(failOnError:true)
		
		def patient = new Patient(firstName:firstName,lastName:lastName,gender:gender,status: initialStatus,location:location)
					.save(failOnError:true)
		new Patient(firstName:firstName,lastName:lastName,gender:gender,status: treatmentStatus,location:location)
					.save(failOnError:true)
		
		when:
		patient.deleted = true
		patient.save(failOnError:true,flush:true)
		
		
		then:
		assert Patient.count == 1
		
		
	
		
		where:
		firstName="Marcin"
		lastName="Czech"
		gender = Patient.Gender.MALE
		initialStatus = Patient.Status.INITIAL
		treatmentStatus = Patient.Status.TREATMENT
		
			
	}
}
