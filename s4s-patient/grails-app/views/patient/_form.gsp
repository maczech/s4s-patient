<%@ page import="com.mcz.fusionsleep.domain.Patient" %>



<div class="fieldcontain ${hasErrors(bean: patientInstance, field: 'firstName', 'error')} required">
	<label for="firstName">
		<g:message code="patient.firstName.label" default="First Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="firstName" maxlength="30" required="" value="${patientInstance?.firstName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: patientInstance, field: 'middleName', 'error')} ">
	<label for="middleName">
		<g:message code="patient.middleName.label" default="Middle Name" />
		
	</label>
	<g:textField name="middleName" maxlength="10" value="${patientInstance?.middleName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: patientInstance, field: 'lastName', 'error')} required">
	<label for="lastName">
		<g:message code="patient.lastName.label" default="Last Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="lastName" maxlength="30" required="" value="${patientInstance?.lastName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: patientInstance, field: 'gender', 'error')} required">
	<label for="gender">
		<g:message code="patient.gender.label" default="Gender" />
		<span class="required-indicator">*</span>
	</label>
<%--	<g:select name="gender" from="${com.mcz.fusionsleep.domain.Patient$Gender?.values()}" keys="${com.mcz.fusionsleep.domain.Patient$Gender.values()*.name()}" required="" value="${patientInstance?.gender?.name()}"/>--%>
		
		<g:radioGroup name="gender" class="genderRadio" values="${com.mcz.fusionsleep.domain.Patient$Gender?.values()}" labels="${com.mcz.fusionsleep.domain.Patient$Gender?.values()}" value="${patientInstance?.gender?.name()}" >
			<p>${it.radio} <g:message code=" ${it.label}" /> </p>
		</g:radioGroup>
</div>

<div class="fieldcontain ${hasErrors(bean: patientInstance, field: 'status', 'error')} required">
	<label for="status">
		<g:message code="patient.status.label" default="Status" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="status" from="${com.mcz.fusionsleep.domain.Patient$Status?.values()}" keys="${com.mcz.fusionsleep.domain.Patient$Status.values()*.name()}" required="" value="${patientInstance?.status?.name()}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: patientInstance, field: 'location', 'error')} required">
	<label for="location">
		<g:message code="patient.location.label" default="Location" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="location" name="location.id" from="${com.mcz.fusionsleep.domain.Location.list()}" optionKey="id" required="" value="${patientInstance?.location?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: patientInstance, field: 'dateOfBirth', 'error')} ">
	<label for="dateOfBirth">
		<g:message code="patient.dateOfBirth.label" default="Date Of Birth" />
		
	</label>
	<g:datePicker name="dateOfBirth" precision="day"  value="${patientInstance?.dateOfBirth}" default="none" noSelection="['': '']" />
</div>

<div class="fieldcontain ${hasErrors(bean: patientInstance, field: 'deleted', 'error')} ">
	<label for="deleted">
		<g:message code="patient.deleted.label" default="Deleted" />
		
	</label>
	<g:checkBox name="deleted" value="${patientInstance?.deleted}" />
</div>

