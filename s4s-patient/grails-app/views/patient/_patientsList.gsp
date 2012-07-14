
	<table>
		<thead>
			<tr>
				<g:sortableColumn property="medicalRecordNumber"
					title="${message(code: 'patient.medicalRecordNumber.label', default: 'MR')}" />

				<g:sortableColumn property="fullName"
					title="${message(code: 'patient.fullName.label', default: 'Full name')}" />

				<g:sortableColumn property="age"
					title="${message(code: 'patient.age.label', default: 'Age')}" />

				<g:sortableColumn property="gender"
					title="${message(code: 'patient.gender.label', default: 'Gender')}" />

				<g:sortableColumn property="status"
					title="${message(code: 'patient.status.label', default: 'Status')}" />

				<g:sortableColumn property="compilance"
					title="${message(code: 'patient.compilane.label', default: 'Compilance')}" />

				<g:sortableColumn property="effort"
					title="${message(code: 'patient.effort.label', default: 'Effort')}" />

				<g:sortableColumn property="ahi"
					title="${message(code: 'patient.ahi.label', default: 'AHI')}" />

				<g:sortableColumn property="ahiDate"
					title="${message(code: 'patient.date.label', default: 'Date')}" />

				<g:sortableColumn property="locationName"
					title="${message(code: 'patient.locationName.label', default: 'Location')}" />

			</tr>
		</thead>
		<tbody>
			<g:each in="${patientInstanceList}" status="i" var="patientInstance">
				<g:render template="/patient/patientRecord"
					model="['patientInstance':patientInstance]" />
			</g:each>
		</tbody>
	</table>
	<div class="pagination">
		<g:paginate total="${patientInstanceTotal}" />
	</div>
