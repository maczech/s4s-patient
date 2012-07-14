package com.mcz.fusionsleep.patient

import com.mcz.fusionsleep.location.Location;
import com.mcz.fusionsleep.patient.Patient;
import com.mcz.fusionsleep.compilance.Compilance;



import grails.plugin.spock.IntegrationSpec;



class PatientControllerIntegrationSpec extends IntegrationSpec{
	def controller;
	def compilanceService
	def setup(){
		controller = new PatientController();
		controller.compilanceService = compilanceService
		
		Compilance.list()*.delete(flush:true)
		Patient.list()*.delete(flush:true)
		Location.list()*.delete(flush:true)
	}
	
	def "should fill compilance data when list"() {
		setup:
		def location = new Location(code:"1",name:"Test location 1").save(failOnError:true)
		new Patient(firstName:firstName,lastName:lastName,gender:gender,status: status,location:location)
		.save(failOnError:true)
		
		when:
		def responseMap = controller.list();
		
		then:
		assert responseMap.patientInstanceList.compilance != null;
		
		where:
		firstName="Marcin"
		lastName="Czech";
		gender = Patient.Gender.MALE;
		status = Patient.Status.INITIAL;
		
		
	}
	
	def "should fill remove patient"() {
		setup:
		def location = new Location(code:"1",name:"Test location 1").save(failOnError:true)
		def patient = new Patient(firstName:firstName,lastName:lastName,gender:gender,status: status,location:location)
		.save(failOnError:true)
		
		when:
		controller.params.id = patient.id;
		controller.delete()
		
		then:"redirect to list view"
		assert Patient.count == 0
		
		
		where:
		firstName="Marcin"
		lastName="Czech";
		gender = Patient.Gender.MALE;
		status = Patient.Status.INITIAL;
		
		
	}
}
