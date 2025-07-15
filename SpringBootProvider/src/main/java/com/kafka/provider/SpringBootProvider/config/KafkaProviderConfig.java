package com.kafka.provider.SpringBootProvider.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean; // Importar @Bean
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProviderConfig {

    // Corregir el typo en la propiedad de Spring Boot
    @Value("${spring.kafka.producer.bootstrap-servers}")
    private String bootstrapServers;

    // Este método no necesita ser un @Bean si solo es un helper para producerFactory()
    public Map<String, Object> producerConfig(){
        Map<String, Object> properties = new HashMap<>();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return properties;
    }

    @Bean // ¡Importante! Para que Spring lo gestione como un bean
    public ProducerFactory<String, String> providerFactory(){
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean // ¡Importante! Para que Spring lo gestione como un bean
    public KafkaTemplate<String, String> kafkaTemplate(){ // No necesita el argumento si Spring lo inyecta
        return new KafkaTemplate<>(providerFactory()); // Llama al método que crea la ProducerFactory
    }
}
