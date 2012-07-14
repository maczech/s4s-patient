import grails.util.Environment;

import com.mcz.fusionsleep.location.Location
import com.mcz.fusionsleep.patient.Patient;
import com.mcz.fusionsleep.compilance.Compilance;

class BootStrap {

    def init = { servletContext ->
		Patient.enableHibernateFilter('enabledFilter')
		
		createSampleData()
		
		if (Environment.current == Environment.DEVELOPMENT) {
			
			def location3 = new Location(name: 'Fusion Sleep 3'  ).save(failOnError:true)
			(1..25).each{it->
				def p = new Patient(firstName:"Anna${it}",lastName:"Barut${it}",
				   gender:Patient.Gender.FEMALE,
				   status: Patient.Status.REFERRED,
				   location:location3,
				   dateOfBirth : new Date() - (365*it))
							   .save(failOnError:true)
			   (1..5).each {index->
				   new Compilance(patient: p, date:new Date()-index,excluded: false, ahi: index,status:Compilance.Status.COMPILANT).save(failOnError:true)
			   }
		   }
		}
    }
    def destroy = {
    }
	
	
	
	def createSampleData(){
		def location1 = new Location(code: 1,name: 'Test location 1'  ).save(failOnError:true)
		def location2 = new Location(name: 'Fusion Sleep 2'  ).save(failOnError:true)
		

		def patient1 = new Patient(firstName:"Marcin",lastName:"Czech",
						gender:Patient.Gender.MALE,
						status: Patient.Status.INITIAL,
						location:location1,
						dateOfBirth : new Date() - (365*12)
						).save(failOnError:true)
					
		def patient2 = new Patient(firstName:"Anna",lastName:"Barut",
						gender:Patient.Gender.FEMALE,
						status: Patient.Status.REFERRED,
						location:location2,
						dateOfBirth : new Date() - (365*18))
						.save(failOnError:true)

		(1..5).each {it->
			new Compilance(patient: patient1, date:new Date()-it,excluded: false, ahi: it,status:Compilance.Status.COMPILANT).save(failOnError:true)
		}

		(1..10).each {it->
			new Compilance(patient: patient1, date:new Date()-it,excluded: false, ahi: it,status:Compilance.Status.NON_COMPILANT).save(failOnError:true)
		}

		new Compilance(patient: patient1, date:new Date()-15,excluded: true, ahi: 15,status:Compilance.Status.PENDING).save(failOnError:true)

		(1..5).each {it->
			new Compilance(patient: patient2, date:new Date()-it,excluded: false, ahi: it,status:Compilance.Status.COMPILANT ).save(failOnError:true)
		}

		
	}
}
