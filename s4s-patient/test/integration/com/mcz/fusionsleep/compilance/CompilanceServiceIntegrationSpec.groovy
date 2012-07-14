package com.mcz.fusionsleep.compilance

import static org.junit.Assert.*

import com.mcz.fusionsleep.location.Location;
import com.mcz.fusionsleep.patient.Patient;

import grails.plugin.spock.IntegrationSpec;
import grails.plugin.spock.UnitSpec;



class CompilanceServiceIntegrationSpec extends IntegrationSpec{

	def location
	def patient
	def compilanceService
	
	def setup() {

		Compilance.list()*.delete(flush:true)
		Patient.list()*.delete(flush:true)
		Location.list()*.delete(flush:true)
		
		location = new Location(code:"1",name:"Test location 1").save(failOnError:true);
		patient = new Patient(firstName:"Marcin",lastName:"Czech",gender:Patient.Gender.MALE,status: Patient.Status.INITIAL,location:location)
				.save(failOnError:true,flush:true)
	
		compilanceService = new CompilanceService();
	}
	
	def "should count only non-excluded and compilant records"(){
		setup:
			new Compilance(patient: patient,excluded: false, status: Compilance.Status.COMPILANT,date: new Date()).save(failOnError:true)
			new Compilance(patient: patient,excluded: true, status: Compilance.Status.COMPILANT,date: new Date()).save(failOnError:true)
			new Compilance(patient: patient,excluded: false, status: Compilance.Status.COMPILANT,date: new Date()).save(failOnError:true)
			new Compilance(patient: patient,excluded: false, status: Compilance.Status.NOT_USED,date: new Date()).save(failOnError:true)
			
		when:
		def compilantRecordsCount = compilanceService.countCompilantRecords(patient.id)
		
		then:
		assert	compilantRecordsCount == 2 
	}
	
	def "should count only non-excluded and not pending records"(){
		setup:
			new Compilance(patient: patient,excluded: false, status: Compilance.Status.COMPILANT,date: new Date()).save(failOnError:true)
			new Compilance(patient: patient,excluded: false, status: Compilance.Status.NON_COMPILANT,date: new Date()).save(failOnError:true)
			new Compilance(patient: patient,excluded: false, status: Compilance.Status.NOT_USED,date: new Date()).save(failOnError:true)
			new Compilance(patient: patient,excluded: false, status: Compilance.Status.PENDING,date: new Date()).save(failOnError:true)
			
		when:
		def compilantRecordsCount = compilanceService.countNotPendingRecords(patient.id)
		
		then:
		assert	compilantRecordsCount == 3
	}
	
	def "should count only non-excluded and compilant or non compilant records"(){
		setup:
			new Compilance(patient: patient,excluded: false, status: Compilance.Status.COMPILANT,date: new Date()).save(failOnError:true)
			new Compilance(patient: patient,excluded: false, status: Compilance.Status.NON_COMPILANT,date: new Date()).save(failOnError:true)
			new Compilance(patient: patient,excluded: false, status: Compilance.Status.NOT_USED,date: new Date()).save(failOnError:true)
			new Compilance(patient: patient,excluded: false, status: Compilance.Status.PENDING,date: new Date()).save(failOnError:true)
			
		when:
		def compilantRecordsCount = compilanceService.countCompilantAndNotCompilantRecords(patient.id)
		
		then:
		assert	compilantRecordsCount == 2
	}
	
	def "should find latest ahi"(){
		setup:
			new Compilance(patient: patient,excluded: false, status: Compilance.Status.COMPILANT,date: new Date()-4).save(failOnError:true)
			new Compilance(patient: patient,excluded: false, status: Compilance.Status.NON_COMPILANT,date: new Date()-3,ahi:5).save(failOnError:true)
			new Compilance(patient: patient,excluded: false, status: Compilance.Status.NOT_USED,date: new Date()-2).save(failOnError:true)
			new Compilance(patient: patient,excluded: false, status: Compilance.Status.PENDING,date: new Date()-1,ahi:10).save(failOnError:true,flush:true)
			
		when:
		def l = Compilance.list()
		def ahiData = compilanceService.findLatestAHI(patient.id)
		
		then:
		assert	ahiData.index == 10
	}
	
	def "should return null fields when no ahi records"(){
		setup:
			new Compilance(patient: patient,excluded: false, status: Compilance.Status.COMPILANT,date: new Date()).save(failOnError:true)
			new Compilance(patient: patient,excluded: false, status: Compilance.Status.NON_COMPILANT,date: new Date()).save(failOnError:true)
			
			
		when:
		def ahiData = compilanceService.findLatestAHI(patient.id)
		
		then:
		assert	ahiData.index == null
		assert ahiData.date == null
	}
	
	def "should return null fields when no compilace record"(){
		setup:
			new Compilance(patient: patient,excluded: false, status: Compilance.Status.COMPILANT,date: new Date()).save(failOnError:true)
			new Compilance(patient: patient,excluded: false, status: Compilance.Status.NON_COMPILANT,date: new Date()).save(failOnError:true)
			
			
		when:
		def ahiData = compilanceService.findLatestAHI(-1L)
		
		then:
		assert	ahiData.index == null
		assert ahiData.date == null
	}
}
