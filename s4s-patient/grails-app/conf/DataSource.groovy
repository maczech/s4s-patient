import  org.grails.plugin.hibernate.filter.HibernateFilterDomainConfiguration

dataSource {
	pooled = true
	driverClassName = "org.postgresql.Driver"
	dialect = org.hibernate.dialect.PostgreSQLDialect
	configClass = HibernateFilterDomainConfiguration.class
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = false
    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
}
// environment specific settings
environments {
    development {
        dataSource {
			
			pooled = true
			driverClassName = "org.h2.Driver"
			username = "sa"
			password = ""
			
            dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
            url = "jdbc:h2:mem:devDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
        }
    }
    test {
        dataSource {

			dbCreate = "create-drop" // one of 'create', 'create-drop','update'
			url = "jdbc:postgresql://localhost:5432/s4s-patient-test"
			username = "postgres"
			password = "postgres"
        }
    }
    production {
        dataSource {
			
			dbCreate = "create" // one of 'create', 'create-drop','update'
			url = "jdbc:postgresql://localhost:5432/s4s-patient"
			username = "postgres"
			password = "postgres"
        }
    }
}
