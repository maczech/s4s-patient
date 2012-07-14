import  org.grails.plugin.hibernate.filter.HibernateFilterDomainConfiguration

dataSource {
    pooled = true
    driverClassName = "org.h2.Driver"
    username = "sa"
    password = ""
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
            dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
            url = "jdbc:h2:mem:devDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
			configClass = HibernateFilterDomainConfiguration.class
        }
    }
    test {
        dataSource {
            dbCreate = "update"
            url = "jdbc:h2:mem:testDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
			configClass = HibernateFilterDomainConfiguration.class
        }
    }
    production {
        dataSource {
			pooled = true
		    driverClassName = "org.postgresql.Driver"
		    dialect = org.hibernate.dialect.PostgreSQLDialect
			dbCreate = "create-drop" // one of 'create', 'create-drop','update'
			url = "jdbc:postgresql://localhost:5432/grails"
			username = "postgres"
			password = "postgres"
        }
    }
}
