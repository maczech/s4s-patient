	
	<tr class="${patientInstance.compilance.compilancePercentage < 0.7 ? 'red' : 'green'}">
					
						<td><g:link action="show" id="${patientInstance.id}">${fieldValue(bean: patientInstance, field: "medicalRecordNumber")}</g:link></td>
					
						<td>${patientInstance.firstName} ${patientInstance.middleName} ${patientInstance.lastName}</td>
					
						<td><g:age date="${patientInstance.dateOfBirth}"/></td>
					
						<td>${fieldValue(bean: patientInstance, field: "gender")}</td>
					
						<td>${fieldValue(bean: patientInstance, field: "status")}</td>
						
						<td><g:percentage value="${patientInstance.compilance.compilancePercentage}"/></td>
						
						<td><g:percentage value="${patientInstance.compilance.effortPercentage}"/></td>
						
						<td>${fieldValue(bean: patientInstance.compilance.ahi, field: "index")}</td>
						
						<td><g:dateFormatter date="${patientInstance.compilance.ahi.date}"/></td>
					
						<td><g:link controller="location" action="show" id="${patientInstance.location.id}">${fieldValue(bean: patientInstance, field: "location")}</g:link></td>
					
	</tr>