package com.mcz.fusionsleep.service

import static org.junit.Assert.*

import com.mcz.fusionsleep.domain.Compilance;
import com.mcz.fusionsleep.domain.Location;
import com.mcz.fusionsleep.domain.Patient;

import grails.plugin.spock.IntegrationSpec;
import grails.plugin.spock.UnitSpec;



class CompilanceServiceIntegrationSpec extends IntegrationSpec{

	def location
	def patient
	def compilanceService
	
	def setup() {
		
		location = new Location(code:"1",name:"Test location 1").save(failOnError:true);
		patient = new Patient(firstName:"Marcin",lastName:"Czech",gender:Patient.Gender.MALE,status: Patient.Status.INITIAL,location:location)
				.save(failOnError:true)
				
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
			new Compilance(patient: patient,excluded: false, status: Compilance.Status.COMPILANT,date: new Date()).save(failOnError:true)
			new Compilance(patient: patient,excluded: false, status: Compilance.Status.NON_COMPILANT,date: new Date(),ahi:5).save(failOnError:true)
			new Compilance(patient: patient,excluded: false, status: Compilance.Status.NOT_USED,date: new Date()).save(failOnError:true)
			new Compilance(patient: patient,excluded: false, status: Compilance.Status.PENDING,date: new Date(),ahi:10).save(failOnError:true)
			
		when:
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
