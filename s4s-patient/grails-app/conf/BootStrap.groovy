import com.mcz.fusionsleep.domain.Patient;

class BootStrap {

    def init = { servletContext ->
		Patient.enableHibernateFilter('enabledFilter')
    }
    def destroy = {
    }
}
