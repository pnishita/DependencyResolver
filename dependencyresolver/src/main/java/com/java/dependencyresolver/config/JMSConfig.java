package com.java.dependencyresolver.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

@EnableJms
@Configuration
public class JMSConfig {
        @Bean
        public ActiveMQConnectionFactory connectionFactory() {
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
            connectionFactory.setBrokerURL("tcp://localhost:61616");
            connectionFactory.setTrustAllPackages(true);
            return connectionFactory;
        }

        @Bean
        public JmsTemplate jmsTemplate(ActiveMQConnectionFactory connectionFactory) {
            JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
            jmsTemplate.setDeliveryPersistent(true);
            return jmsTemplate;
        }

        @Bean
        public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(ActiveMQConnectionFactory connectionFactory) {
            DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
            factory.setConnectionFactory(connectionFactory);
            factory.setConcurrency("1-1");
            return factory;
        }

}
