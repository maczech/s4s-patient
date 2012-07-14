package com.mcz.fusionsleep.compilance


class CompilanceService {

    
	def getCompilanceData(patientId){
		def compilanceData = new CompilanceData()
		
		try{
			
			compilanceData.compilancePercentage = calculateCompilancePercentage(patientId);
		
		}catch(IllegalStateException e){
			compilanceData.compilancePercentage = null;
		}
		
		try{
			compilanceData.effortPercentage = calculateEffortPercentage(patientId);
		}catch(IllegalStateException e){
			compilanceData.effortPercentage = null;
		}
		
		compilanceData.ahi = findLatestAHI(patientId)
		
		return compilanceData	
	}
	
	def findLatestAHI(patientId){
	
		def criteria =  Compilance.createCriteria()
 
		def compilance = criteria.list{
			isNotNull("ahi")
			and{
				patient{
					eq("id", patientId)	
				}
				eq("excluded",false)
			}
			order("date","desc")
			maxResults(1)
				
			}[0]
			
			if(compilance==null){
				return new AHIData()
			}
			return new AHIData(index:compilance.ahi,date:compilance.date)
		
	}
	
	def calculateCompilancePercentage(patientId) {
		def compilantCount = countCompilantRecords(patientId)
		def notPendingCount = countNotPendingRecords(patientId)
		
		if(notPendingCount==0){
			throw new IllegalStateException("Cannot calculate compilance percentage for patient id:${patientId} cause there is no not pending records")
		}
		
		return compilantCount / notPendingCount  

    }
	
	def calculateEffortPercentage(patientId){
		def compilantAndNotCompilantCount = countCompilantAndNotCompilantRecords(patientId)	
		
		def notPendingCount = countNotPendingRecords(patientId)
		
		if(notPendingCount==0){
			throw new IllegalStateException("Cannot calculate effort percentage for patient id:${patientId} cause there is no not pending records")
		}
		
		return compilantAndNotCompilantCount / notPendingCount
	}
	
	def countCompilantAndNotCompilantRecords(patientId){
		def countCriteria = Compilance.createCriteria()
		
		return countCriteria.count{
			not{
				eq("excluded",true)
			}
			and{
				
				patient{
					eq("id",patientId)
				}
				or{
					eq("status",Compilance.Status.COMPILANT)
					eq("status",Compilance.Status.NON_COMPILANT)
				}
			};
		}
	}
	
	def countCompilantRecords(patientId){
		def countCriteria = Compilance.createCriteria()
		
		return countCriteria.count{
			not{
				eq("excluded",true)
			}
			and{
				
				patient{
					eq("id",patientId)
				}
				eq("status",Compilance.Status.COMPILANT)
			};
		}
	}
	
	def countNotPendingRecords(patientId){
		def countCriteria = Compilance.createCriteria()
		
		return countCriteria.count{
			not{
				eq("status",Compilance.Status.PENDING)
			}
			and{
				patient{
					eq("id",patientId)
				}
				eq("excluded",false)
			}
			
			
		}
	}

}
