# KafkaWithSpringBoot


Spring Boot Kafka Project: Producer and Consumer Microservices
This project demonstrates the implementation of an asynchronous messaging system using Apache Kafka with Spring Boot. It consists of two main microservices: a Producer (Provider) that sends messages to a Kafka topic, and a Consumer that listens for and processes those messages.

🚀 Project Architecture
The project follows a microservices architecture, where each component has a specific responsibility:

SpringBootForKafka (Parent Module): A Maven multi-module project that manages common dependencies and configuration for the SpringBootProvider and SpringBootConsumer microservices.

SpringBootProvider (Producer Microservice):

Responsible for generating and sending messages to the Kafka topic.

Configures the Kafka producer and serializes messages.

Handles the automatic creation of the Kafka topic (naceunProgramador-Topic) upon application startup.

SpringBootConsumer (Consumer Microservice):

Responsible for listening for and consuming messages from the Kafka topic.

Configures the Kafka consumer and deserializes messages.

Contains a Kafka listener that processes received messages.

Apache Kafka: Acts as the central messaging broker, storing messages durably and enabling decoupled communication between the producer and consumer.

Conceptual diagram of the interaction between microservices and Kafka.

⚙️ Prerequisites
Before running the project, make sure you have the following installed:

Java Development Kit (JDK) 17 or higher: Download JDK

Apache Maven: Download Maven

Apache Kafka (and Apache ZooKeeper): You can download it from the official Kafka website or use Docker for a simpler setup.

Download Kafka

To run Kafka locally, first start ZooKeeper and then the Kafka broker.

📦 Project Structure
SpringBootForKafka/
├── .mvn/
├── src/
├── pom.xml                   # Parent POM (manages modules and common dependencies)
├── SpringBootConsumer/       # Consumer Microservice Module
│   ├── src/main/java/com/kafka/consumer/
│   │   ├── config/
│   │   │   └── KafkaConsumerConfig.java  # Kafka consumer configuration
│   │   ├── listeners/
│   │   │   └── KafkaConsumerListener.java # Kafka message listener
│   │   └── SpringBootConsumerApplication.java # Main consumer class
│   ├── src/main/resources/
│   │   └── application.properties # Consumer configuration properties
│   └── pom.xml                   # Consumer module POM
└── SpringBootProvider/       # Producer Microservice Module
    ├── src/main/java/com/kafka/provider/
    │   ├── config/
    │   │   ├── KafkaProviderConfig.java  # Kafka producer configuration
    │   │   └── KafkaTopicConfig.java     # Configuration for topic creation
    │   └── SpringBootProviderApplication.java # Main producer class
    ├── src/main/resources/
    │   └── application.properties # Producer configuration properties
    └── pom.xml                   # Producer module POM

🚀 Configuration and Execution
Follow these steps to get the project up and running:

1. Start Kafka and ZooKeeper
Ensure that your Kafka and ZooKeeper services are running. If you downloaded them manually, you can start them from the command line:

# 1. Start ZooKeeper
# From Kafka installation directory
bin/zookeeper-server-start.sh config/zookeeper.properties
# On Windows: bin\windows\zookeeper-server-start.bat config\zookeeper.properties

# 2. Start Kafka Broker
# In a new terminal, from Kafka installation directory
bin/kafka-server-start.sh config/server.properties
# On Windows: bin\windows\kafka-server-start.bat config\server.properties

2. Configure Microservices
Ensure that the application.properties files in both modules (SpringBootConsumer and SpringBootProvider) are correctly configured to point to your Kafka broker (default localhost:9092).

SpringBootProvider/src/main/resources/application.properties:

spring.application.name=SpringKafkaProvider
server.port=8080 

# Kafka Configuration
spring.kafka.producer.bootstrap-servers=localhost:9092

SpringBootConsumer/src/main/resources/application.properties:

spring.application.name=SpringKafkaConsumer
server.port=8081 # A different port than the provider

# Kafka Consumer Configuration
spring.kafka.consumer.bootstrap-servers=localhost:9092
spring.kafka.consumer.group-id=my-consumer-group # A group ID for your consumer

3. Compile the Project
From the project root (SpringBootForKafka), compile both modules using Maven:

mvn clean install

4. Run the Microservices
Open two different terminals or use your IDE (IntelliJ IDEA) to run each microservice separately.

4.1. Start the Producer Microservice (SpringBootProvider)
From the root of the SpringBootProvider module:

cd SpringBootProvider
mvn spring-boot:run

Or from IntelliJ IDEA, run the SpringBootProviderApplication class.

Upon startup, this microservice will attempt to create the naceunProgramador-Topic topic in Kafka and then send a message.

4.2. Start the Consumer Microservice (SpringBootConsumer)
From the root of the SpringBootConsumer module:

cd SpringBootConsumer
mvn spring-boot:run

Or from IntelliJ IDEA, run the SpringBootConsumerApplication class.

Once started, this microservice will begin listening for messages on the naceunProgramador-Topic topic and print them to its console.

🧪 Verification
To verify that everything is working correctly:

Verify Topic Creation:
After starting the SpringBootProvider, you can list Kafka topics to confirm that naceunProgramador-Topic was created:

# From Kafka installation directory
bin/kafka-topics.sh --list --bootstrap-server localhost:9092
# On Windows: bin\windows\kafka-topics.bat --list --bootstrap-server localhost:9092

Observe Consumer Logs:
In the SpringBootConsumer microservice console, you should see the message "Mensaje recibido: Hola mundo con Kafka desde Spring Boot" (or the message your producer is sending).

🛠️ Technologies Used
Java 17

Spring Boot 3.x

Apache Kafka

Apache Maven
