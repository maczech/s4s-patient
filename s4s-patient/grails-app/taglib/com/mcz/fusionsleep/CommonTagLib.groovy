package com.mcz.fusionsleep
import java.text.DecimalFormat

class CommonTagLib {
	def age = { attrs ->
		def date = attrs.date
		if(date){
			Integer birthdayYear = Integer.valueOf(date.format("yyyy"))
			Integer currentYear = Integer.valueOf(new Date().format("yyyy"))
			def age = currentYear - birthdayYear
			out << age
		}
	}
	
	def percentage = { attrs ->
		
		def value = attrs.value
		
		if(value){
			def percentage = new DecimalFormat("###.#").format(value * 100)
			out << "${percentage}%"	
		}else{
			out << "N/A"
		}

	}
	
	def dateFormatter = { attrs ->
		def date = attrs.date
		
		if(date){
			out << date.format("dd-MM-yyyy")
		}else{
			out << "N/A"
		}
	}
}
