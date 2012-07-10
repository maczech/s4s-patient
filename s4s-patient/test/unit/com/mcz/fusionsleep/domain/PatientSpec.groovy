package com.mcz.fusionsleep.domain

import grails.plugin.spock.UnitSpec;
import grails.test.*
import grails.validation.ValidationException;

class PatientSpec  extends UnitSpec{

	def "should be persisted when all mandatory fileds are filled"() {
		setup:
		mockDomain(Patient)
		mockDomain(Location)
		
		when:
		new Patient(firstName:firstName,lastName:lastName,gender:gender,status: status,location:location)
				.save(failOnError:true)
		
		then:
		Patient.count()==1;
		
		where:
		firstName="Marcin"
		lastName="Czech";
		gender = Patient.Gender.MALE;
		status = Patient.Status.INITIAL;
		location = new Location(code:"1",name:"Test location 1")
			
	}
	
	def "should throw exception when mandatory field is missing"() {
		setup:
		mockDomain(Patient)
		mockDomain(Location)
		
		when:
		new Patient(lastName:lastName,gender:gender,status: status,location:location)
					.save(failOnError:true)
		
		then:
		def e = thrown(ValidationException);
		
		where:
		lastName="Czech";
		gender = Patient.Gender.MALE;
		status = Patient.Status.INITIAL;
		location = new Location(code:"1",name:"Test location 1")
			
	}
	
	def "should throw exception when field is too long"() {
		setup:
		mockDomain(Patient)
		mockDomain(Location)
		
		when:
		new Patient(firstName:firstName,lastName:lastName,gender:gender,status: status,location:location)
					.save(failOnError:true)
		
		then:
		def e = thrown(ValidationException);
		
		where:
		firstName="MarcinMarcinMarcinMarcinMarcinMarcin"
		lastName="Czech"
		gender = Patient.Gender.MALE
		status = Patient.Status.INITIAL
		location = new Location(code:"1",name:"Test location 1")
			
	}
	
	def "should medical record number be empty after save"() {
		setup:
		mockDomain(Patient)
		mockDomain(Location)
		
		when:
		def patient = new Patient(firstName:firstName,lastName:lastName,gender:gender,status: status,location:location)
					.save(failOnError:true)
		
		then:
		assert patient.medicalRecordNumber == null;
		
		where:
		firstName="Marcin"
		lastName="Czech"
		gender = Patient.Gender.MALE
		status = Patient.Status.INITIAL
		location = new Location(code:"1",name:"Test location 1")
			
	}
	
//	def "should medical record number be filled after load"() {
//		setup:
//		mockDomain(Patient)
//		mockDomain(Location)
//		
//		when:
//		def patient = new Patient(firstName:firstName,lastName:lastName,gender:gender,status: status,location:location)
//					.save(failOnError:true)
//		
//		then:
//		assert Patient.get(patient.id).medicalRecordNumber == "MR00000"+patient.id;
//		
//		where:
//		firstName="Marcin"
//		lastName="Czech"
//		gender = Patient.Gender.MALE
//		status = Patient.Status.INITIAL
//		location = new Location(code:"1",name:"Test location 1")
//			
//	}
	
	def "should return only Patients with Treatment status"() {
		setup:
		mockDomain(Patient)
		mockDomain(Location)
		new Patient(firstName:firstName,lastName:lastName,gender:gender,status: initialStatus,location:location)
					.save(failOnError:true)
		new Patient(firstName:firstName,lastName:lastName,gender:gender,status: treatmentStatus,location:location)
					.save(failOnError:true)
		
		when:			
		def patients = Patient.onTreatment.list()
		
		then:
		Patient.count == 2
		and:
		patients.size == 1
		patients[0].id == 2
	
		
		where:
		firstName="Marcin"
		lastName="Czech"
		gender = Patient.Gender.MALE
		initialStatus = Patient.Status.INITIAL
		treatmentStatus = Patient.Status.TREATMENT
		location = new Location(code:"1",name:"Test location 1")
			
	}
	
	def "should return only not deleted Patients"() {
		setup:
		mockDomain(Patient)
		mockDomain(Location)
		def patient = new Patient(firstName:firstName,lastName:lastName,gender:gender,status: initialStatus,location:location)
					.save(failOnError:true)
		new Patient(firstName:firstName,lastName:lastName,gender:gender,status: treatmentStatus,location:location)
					.save(failOnError:true)
		
		when:
		patient.deleted = true
		patient.save(failOnError:true)
		
		then:
		Patient.findById(1).deleted ==true
		Patient.count == 1
		
	
		
		where:
		firstName="Marcin"
		lastName="Czech"
		gender = Patient.Gender.MALE
		initialStatus = Patient.Status.INITIAL
		treatmentStatus = Patient.Status.TREATMENT
		location = new Location(code:"1",name:"Test location 1")
			
	}
  
}
