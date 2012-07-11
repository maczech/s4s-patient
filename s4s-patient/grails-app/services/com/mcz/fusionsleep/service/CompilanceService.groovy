package com.mcz.fusionsleep.service

import com.mcz.fusionsleep.AHIData;
import com.mcz.fusionsleep.CompilanceData;
import com.mcz.fusionsleep.domain.Compilance;

class CompilanceService {

    
	def getCompilanceData(patientId){
		def compilanceData = new CompilanceData()
		compilanceData.compilancePercentage = calculateCompilancePercentage(patientId);
		compilanceData.effortPercentage = calculateEffortPercentage(patientId);
		compilanceData.ahi = findLatestAHI(patientId)
		
		return compilanceData	
	}
	
	def findLatestAHI(patientId){
	
			def criteria =  Compilance.createCriteria()
//			def compilance =  Compilance.findByAhiIsNotNullAndId(patientId, [sort:'date',order:'desc']); 
			def compilance = criteria.list{
				isNotNull("ahi")
				and{
					patient{
						eq("id", patientId)	
					}	
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
				eq("excluded",true)
				eq("status",Compilance.Status.PENDING)
			}
			and{
				patient{
					eq("id",patientId)
				}
				
			};
		}
	}

}
