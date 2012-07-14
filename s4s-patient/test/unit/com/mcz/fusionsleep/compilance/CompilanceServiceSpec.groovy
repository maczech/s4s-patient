package com.mcz.fusionsleep.compilance

import com.mcz.fusionsleep.location.Location;
import com.mcz.fusionsleep.patient.Patient;

import grails.plugin.spock.UnitSpec;
import grails.test.*
import grails.validation.ValidationException;

class CompilanceServiceSpec extends UnitSpec{

	def compilanceService
	
	def setup() {
		
		compilanceService = new CompilanceService();
	}
	
	def "should calculate compilance percentage"(){
		setup:
		compilanceService.metaClass.countCompilantRecords = {args -> 
			return 10
		}
		compilanceService.metaClass.countNotPendingRecords = {args ->
			return 20
		}
		
		when:
		def compilancePercentage = compilanceService.calculateCompilancePercentage(1)
		
		then:
		assert compilancePercentage == 0.5 
	}
	
	def "should throw exception when calculate compilance percentage and not pending records eq zero"(){
		setup:
		compilanceService.metaClass.countCompilantRecords = {args ->
			return 10
		}
		compilanceService.metaClass.countNotPendingRecords = {args ->
			return 0
		}
		
		when:
		def compilancePercentage = compilanceService.calculateCompilancePercentage(1)
		
		then:
		def e = thrown(IllegalStateException);
	}
	
	def "should  calculate compilance percentage when not compilant records eq zero"(){
		setup:
		compilanceService.metaClass.countCompilantRecords = {args ->
			return 0
		}
		compilanceService.metaClass.countNotPendingRecords = {args ->
			return 20
		}
		
		when:
		def compilancePercentage = compilanceService.calculateCompilancePercentage(1)
		
		then:
		assert compilancePercentage == 0

	}
	
	
	def "should calculate effort percentage"(){
		setup:
		compilanceService.metaClass.countCompilantAndNotCompilantRecords = {args ->
			return 10
		}
		compilanceService.metaClass.countNotPendingRecords = {args ->
			return 20
		}
		
		when:
		def compilancePercentage = compilanceService.calculateEffortPercentage(1)
		
		then:
		assert compilancePercentage == 0.5
	}
	
	def "should throw exception when calculate effort percentage and not pending records eq zero"(){
		setup:
		compilanceService.metaClass.countCompilantAndNotCompilantRecords = {args ->
			return 10
		}
		compilanceService.metaClass.countNotPendingRecords = {args ->
			return 0
		}
		
		when:
		def compilancePercentage = compilanceService.calculateEffortPercentage(1)
		
		then:
		def e = thrown(IllegalStateException);
	}
	
	def "should  calculate effort percentage when not compilant records eq zero"(){
		setup:
		compilanceService.metaClass.countCompilantAndNotCompilantRecords = {args ->
			return 0
		}
		compilanceService.metaClass.countNotPendingRecords = {args ->
			return 20
		}
		
		when:
		def compilancePercentage = compilanceService.calculateEffortPercentage(1)
		
		then:
		assert compilancePercentage == 0

	}
	
	def "should get compilance data"(){
		setup:
		compilanceService.metaClass.calculateCompilancePercentage = {args ->
			return compilancePercentage
		}
		compilanceService.metaClass.calculateEffortPercentage = {args ->
			return effortPercentage
		}
		compilanceService.metaClass.findLatestAHI = {args ->
			return new AHIData(index:index,date:date)
		}
		
		when:
		def compilanceData = compilanceService.getCompilanceData(1)
		
		then:
		assert compilanceData.compilancePercentage == compilancePercentage
		assert compilanceData.effortPercentage == effortPercentage
		
		assert compilanceData?.ahi.index == index
		assert compilanceData?.ahi.date == date
		
		where:
		compilancePercentage = 0.5
		effortPercentage = 0.75
		index = 5
		date = new Date()
	}
	
}