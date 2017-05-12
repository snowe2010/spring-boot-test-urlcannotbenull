package com.company.stuff.iam.config;

import java.sql.SQLException;
import java.util.Collections;
import java.util.concurrent.Executors;

import javax.sql.DataSource;

import org.axonframework.commandhandling.AsynchronousCommandBus;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.axonframework.common.jpa.ContainerManagedEntityManagerProvider;
import org.axonframework.common.jpa.EntityManagerProvider;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.saga.ResourceInjector;
import org.axonframework.eventhandling.scheduling.EventScheduler;
import org.axonframework.eventhandling.scheduling.java.SimpleEventScheduler;
import org.axonframework.eventhandling.tokenstore.TokenStore;
import org.axonframework.eventhandling.tokenstore.jpa.JpaTokenStore;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.jpa.JpaEventStorageEngine;
import org.axonframework.messaging.Message;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.axonframework.messaging.interceptors.BeanValidationInterceptor;
import org.axonframework.messaging.interceptors.CorrelationDataInterceptor;
import org.axonframework.messaging.interceptors.LoggingInterceptor;
import org.axonframework.messaging.interceptors.TransactionManagingInterceptor;
import org.axonframework.serialization.AnnotationRevisionResolver;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.xml.XStreamSerializer;
import org.axonframework.spring.messaging.unitofwork.SpringTransactionManager;
import org.axonframework.spring.saga.SpringResourceInjector;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Axon Java Configuration with reasonable defaults like SimpleCommandBus, SimpleEventBus and GenericJpaRepository.
 */
@Configuration
public class AxonConfig {
//
//    @Autowired
//    public void registerTrackingProcessors(EventHandlingConfiguration eventHandlingConfig) {
////        eventHandlingConfig.registerTrackingProcessor("factoryProjectors");
//    }

    @Bean
    public TokenStore tokenStore(EntityManagerProvider entityManagerProvider) {
        return new JpaTokenStore(entityManagerProvider, new XStreamSerializer(new AnnotationRevisionResolver()));
    }

    @Bean
    public EntityManagerProvider entityManagerProvider() {
        return new ContainerManagedEntityManagerProvider();
    }

    @Bean
    public ResourceInjector resourceInjector() {
        return new SpringResourceInjector();
    }

    @Bean
    public TransactionManager transactionManager(PlatformTransactionManager platformTransactionManager) {
        return new SpringTransactionManager(platformTransactionManager);
    }

    @Bean
    public EventStorageEngine eventStorageEngine(Serializer serializer,
                                                 DataSource dataSource,
                                                 EntityManagerProvider provider,
                                                 TransactionManager manager) throws SQLException {
        return new JpaEventStorageEngine(serializer, null, dataSource, provider, manager);
    }

    @Bean
    public EventScheduler simpleEventScheduler(EventBus bus) {
        return new SimpleEventScheduler(Executors.newScheduledThreadPool(5), bus);
    }
}

