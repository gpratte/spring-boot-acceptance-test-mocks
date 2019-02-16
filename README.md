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

## 02-receive-jms-message
To receive a JMS message I first wanted to have an ActiveMQ server running locally. To do this I

1. Installed ActiveMQ (http://activemq.apache.org/) by downloading the stand alone (the tar for mac)
2. Ran it via command line
3. Viewed it in the browser (http://localhost:8161/admin/)

I needed a starting place so I 
Googled Spring Boot JMS ActiveMQ and read
 * https://spring.io/guides/gs/messaging-jms/
 * https://dzone.com/articles/using-jms-in-spring-boot-1

I used the Spring Guide Spring Boot code (the first link above) as a starting point but removed the broker (that's for sending) and removed the sending code from Application

I also set the application.properties to my local ActiveMQ server and added the jackson dependency (that I learned from the second link above).

The Spring Boot code listens on the "mailbox" queue.
 
Brough up the Spring Boot server and it complains about not connecting to the queue.

Started the ActiveMQ server. In the ActiveMQ browser created a mailbox queue.

Brought up Spring Boot and no complaints.

In the ActiveMQ browser set the Type to TEXT and set the body to
```
{  
   "to" : "gpratte",
   "body" : "hi there"
}
```
and hit send.

Here is a screen shot 

![alt text](this-and-that/img/activemq-send-mailbox-queue.png?raw=true "ActiveMQ send message")

Spring boot complains 
`MessageConversionException: Could not find type id property [_type]`

## 03-message-converter

Tried to resolve the message conversion problem a few different ways to no avail.

Finally decided to roll my own converter. Changed the configuration to use the new EmailMessageConverter class.

Now the message posted to the mailbox queue is converted into an Email object.

__Note:__ If running server in IntelliJ remember to _Enable annotation processing_ to get Lombok to work.




.