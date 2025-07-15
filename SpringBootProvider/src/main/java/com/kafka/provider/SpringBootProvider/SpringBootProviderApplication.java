package com.kafka.provider.SpringBootProvider;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class SpringBootProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootProviderApplication.class, args);
	}


	@Bean
	CommandLineRunner init(KafkaTemplate<String, String> kafkaTemplate){
		return  args -> {
			kafkaTemplate.send("naceunProgramador-Topic", "Final prueba de Kafka");
		};
	};

}
