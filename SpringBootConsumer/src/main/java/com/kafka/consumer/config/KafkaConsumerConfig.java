package com.kafka.consumer.SpringBootConsumer.config; // Ajusta el paquete si es necesario

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

@EnableKafka // Habilita la detección de @KafkaListener en tu aplicación
@Configuration // Indica que esta clase es una fuente de definiciones de beans
public class KafkaConsumerConfig {

    // Inyecta la dirección del broker de Kafka desde application.properties
    @Value("${spring.kafka.consumer.bootstrap-servers}")
    private String bootstrapServers;

    // Define el ID del grupo de consumidores
    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    /**
     * Configura las propiedades del consumidor de Kafka.
     * @return Un mapa de propiedades de configuración del consumidor.
     */
    public Map<String, Object> consumerConfig() {
        Map<String, Object> properties = new HashMap<>();
        // Servidores bootstrap de Kafka
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        // Deserializador para la clave del mensaje (String)
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        // Deserializador para el valor del mensaje (String)
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        // ID del grupo de consumidores. Todos los consumidores con el mismo group.id
        // son parte del mismo grupo y reciben un subconjunto de las particiones de un topic.
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        // Estrategia para reiniciar el offset si no hay un offset inicial en Kafka
        // 'earliest': empieza desde el principio del topic
        // 'latest': empieza desde el último mensaje
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        // Habilita la confirmación automática de offsets.
        // Se recomienda deshabilitarlo para un control manual más preciso en producción.
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        // Intervalo de tiempo en que el offset se confirma automáticamente (si auto.commit.enable es true)
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000"); // 1 segundo
        return properties;
    }

    /**
     * Crea una fábrica de consumidores de Kafka.
     * Esta fábrica se utiliza para crear instancias de consumidores de Kafka.
     * @return Una instancia de ConsumerFactory.
     */
    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfig());
    }

    /**
     * Crea una fábrica de contenedores de listener de Kafka concurrente.
     * Esta fábrica se utiliza para crear los contenedores que gestionan los listeners de Kafka.
     * Permite procesar mensajes de forma concurrente.
     * @param consumerFactory La fábrica de consumidores de Kafka.
     * @return Una instancia de ConcurrentKafkaListenerContainerFactory.
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(
            ConsumerFactory<String, String> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        // Puedes añadir configuraciones adicionales aquí, por ejemplo:
        // factory.setConcurrency(3); // Número de hilos de consumo
        // factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL); // Para confirmación manual
        return factory;
    }
}
