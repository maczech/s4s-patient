
<%@ page import="com.mcz.fusionsleep.domain.Patient" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'patient.label', default: 'Patient')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-patient" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-patient" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
						<g:sortableColumn property="medicalRecordNumber" title="${message(code: 'patient.medicalRecordNumber.label', default: 'MR')}" />
						
						<g:sortableColumn property="fullName" title="${message(code: 'patient.fullName.label', default: 'Full name')}" />
					
						<g:sortableColumn property="age" title="${message(code: 'patient.age.label', default: 'Age')}" />
										
						<g:sortableColumn property="gender" title="${message(code: 'patient.gender.label', default: 'Gender')}" />
					
						<g:sortableColumn property="status" title="${message(code: 'patient.status.label', default: 'Status')}" />
						
						<g:sortableColumn property="compilance" title="${message(code: 'patient.compilane.label', default: 'Compilance')}" />
						
						<g:sortableColumn property="effort" title="${message(code: 'patient.effort.label', default: 'Effort')}" />
						
						<g:sortableColumn property="ahi" title="${message(code: 'patient.status.label', default: 'AHI')}" />
						
						<g:sortableColumn property="ahiDate" title="${message(code: 'patient.date.label', default: 'Date')}" />

						<g:sortableColumn property="locationName" title="${message(code: 'patient.locationName.label', default: 'Location')}" />					

					</tr>
				</thead>
				<tbody>
				<g:each in="${patientInstanceList}" status="i" var="patientInstance">
					<g:render template="/patient/patientRecord" model="['patientInstance':patientInstance]"/>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${patientInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
