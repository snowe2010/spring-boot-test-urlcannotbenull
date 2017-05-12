package com.company.stuff.iam.config;

import java.util.Properties;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "iamEntityManagerFactory", transactionManagerRef = "iamJpaTransactionManager")
public class IamDatasourceConfig {

    private static final Logger logger = LoggerFactory.getLogger(IamDatasourceConfig.class);
    private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
    private static final String PROPERTY_NAME_HIBERNATE_FORMAT_SQL = "hibernate.format_sql";
    private static final String PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";
    private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
    private static final String PROPERTY_POSTGRESQL_DRIVER = "org.postgresql.Driver";

    @Bean(name = "iamEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean iamEntityManagerFactory(@Qualifier("iamDatasource") DataSource dataSource,
                                                                          @Qualifier("jpaProperties") Properties jpaProps) {
        LocalContainerEntityManagerFactoryBean emFactory = new LocalContainerEntityManagerFactoryBean();
        emFactory.setDataSource(dataSource);

        // Use Spring base scanning of jpa entities rather than persistence.xml
        emFactory.setPackagesToScan("com.company.stuff.iam.projection");

        emFactory.setJpaVendorAdapter(jpaVendorAdapter());
        emFactory.setJpaProperties(jpaProps);

        return emFactory;
    }


    @Bean(name = "loanappJpaTransactionManager")
    public JpaTransactionManager provideIamUserJpaTransactionManager(
            @Qualifier("iamEntityManagerFactory") LocalContainerEntityManagerFactoryBean iamEntityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(iamEntityManagerFactory.getObject());

        return transactionManager;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setDatabase(Database.POSTGRESQL);
        return hibernateJpaVendorAdapter;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.iamDatasource")
    public DataSource iamDatasource() {
        return new DataSource();
    }

//    @Bean(name = "jpaProperties")
//    public Properties additionalJpaProperties(@Value("${spring.jpa.hibernate.database-dialect}") String hibernateDialect,
//                                              @Value("${spring.jpa.hibernate.format-sql}") String hibernateFormatSql,
//                                              @Value("${spring.jpa.hibernate.show-sql}") String hibernateShowSql,
//                                              @Value("${spring.jpa.hibernate.ddl-auto}") String hibernateDdlAuto) {
//        Properties properties = new Properties();
//
//        logger.debug("hibernate dialect: " + hibernateDialect + "");
//        properties.setProperty(PROPERTY_NAME_HIBERNATE_DIALECT, hibernateDialect);
//        properties.setProperty(PROPERTY_NAME_HIBERNATE_FORMAT_SQL, hibernateFormatSql);
//        properties.setProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL, hibernateShowSql);
//        properties.setProperty(PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO, hibernateDdlAuto);
//
//        return properties;
//    }
}
