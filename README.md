# spring-boot-acceptance-test-mocks
Spring Boot project that connects to various resources (database, mongo, rest calls, JMS messaging, Rabbit messaging, legacy). Mock the connections for acceptance testing.

As always I will build up the server in multiple steps. I will code each step on a new branch.

# Branches
## 01-spring-initalizr
Create a Spring Boot project using Spring Inializr https://start.spring.io/

Configure by taking the following steps...

1. Click the link to Switch to the full version.

2. Change the following:
 * Generate a Gradle Project with Java and Spring Boot 1.5.19
 * Group com.example
 * Artifact message
 * Package Name com.example.message
 * Dependencies
    * Lombok
    * JMS (ActiveMQ)
    * Cloud Contract Stub Runner

Merge this branch to master.
