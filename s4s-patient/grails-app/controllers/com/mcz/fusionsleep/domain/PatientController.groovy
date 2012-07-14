package com.mcz.fusionsleep.domain

import org.springframework.dao.DataIntegrityViolationException

class PatientController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def compilanceService;
	
    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
		
		def patients =  Patient.list(params)
		
		fillCompilanceData(patients)
		
        [patientInstanceList: patients, patientInstanceTotal: Patient.count()]
    }

	def fillCompilanceData(patients) {
		patients.each{
			try{
				def compilanceData = compilanceService.getCompilanceData(it.id)
				it.metaClass.compilance = compilanceData
			}catch(IllegalStateException e){
				it.errors.reject(e.getMessage())
			}
		}
	}

    def create() {
        [patientInstance: new Patient(params)]
    }

    def save() {
        def patientInstance = new Patient(params)
        if (!patientInstance.save(flush: true)) {
            render(view: "create", model: [patientInstance: patientInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'patient.label', default: 'Patient'), patientInstance.id])
        redirect(action: "show", id: patientInstance.id)
    }

    def show() {
        def patientInstance = Patient.get(params.id)
        if (!patientInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'patient.label', default: 'Patient'), params.id])
            redirect(action: "list")
            return
        }

        [patientInstance: patientInstance]
    }

    def edit() {
        def patientInstance = Patient.get(params.id)
        if (!patientInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'patient.label', default: 'Patient'), params.id])
            redirect(action: "list")
            return
        }

        [patientInstance: patientInstance]
    }

    def update() {
        def patientInstance = Patient.get(params.id)
        if (!patientInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'patient.label', default: 'Patient'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (patientInstance.version > version) {
                patientInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'patient.label', default: 'Patient')] as Object[],
                          "Another user has updated this Patient while you were editing")
                render(view: "edit", model: [patientInstance: patientInstance])
                return
            }
        }

        patientInstance.properties = params

        if (!patientInstance.save(flush: true)) {
            render(view: "edit", model: [patientInstance: patientInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'patient.label', default: 'Patient'), patientInstance.id])
        redirect(action: "show", id: patientInstance.id)
    }

    def delete() {
        def patientInstance = Patient.get(params.id)
        if (!patientInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'patient.label', default: 'Patient'), params.id])
            redirect(action: "list")
            return
        }

        try {
            patientInstance.deleted=true
			patientInstance.save(flush:true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'patient.label', default: 'Patient'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'patient.label', default: 'Patient'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
