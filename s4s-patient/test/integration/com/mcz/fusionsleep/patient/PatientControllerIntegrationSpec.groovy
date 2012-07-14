package com.mcz.fusionsleep.patient

import com.mcz.fusionsleep.location.Location;
import com.mcz.fusionsleep.patient.Patient;

import grails.plugin.spock.IntegrationSpec;



class PatientControllerIntegrationSpec extends IntegrationSpec{
	def controller;
	def compilanceService
	def setup(){
		controller = new PatientController();
		controller.compilanceService = compilanceService
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
}
