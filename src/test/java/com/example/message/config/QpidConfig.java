package com.example.message.config;

import com.example.message.message.AmqpReceiver;
import com.google.common.io.Files;
import org.apache.qpid.server.Broker;
import org.apache.qpid.server.BrokerOptions;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.File;

@Profile("test")
@Configuration
public class QpidConfig {

    String amqpPort = "5672";
    String qpidHomeDir = "spring-boot-acceptance-test-mocks";
    String configFileName = "src/test/resources/qpid-config.json";

    private final String exchangeName;
    private final String queueName;
    private final String routingKey;

    public QpidConfig(@Value("${rabbitmq.exchange}") String exchangeName,
                      @Value("${rabbitmq.queue}") String queueName,
                      @Value("${rabbitmq.routing.key}") String routingKey) {
        this.exchangeName = exchangeName;
        this.queueName = queueName;
        this.routingKey = routingKey;
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(exchangeName);
    }

    @Bean
    Queue queue() {
        return new Queue(queueName, false);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue)
            .to(exchange)
            .with(routingKey);
    }

    @Bean
    Broker broker() throws Exception {
        Broker broker=new Broker();
        broker.startup(brokerOptions());

        return broker;
    }

    /**
     * Need to configure Qpid.
     * @See https://qpid.apache.org/releases/qpid-java-6.0.5/java-broker/book/Java-Broker-Initial-Configuration-Configuration-Properties.html
     */
    @Bean
    BrokerOptions brokerOptions() {

        File tmpFolder= Files.createTempDir();

        //small hack, because userDir is not same when running Application and ApplicationTest
        //it leads to some issue locating the files after, so hacking it here
        String userDir=System.getProperty("user.dir").toString();
        if(!userDir.contains(qpidHomeDir)){
            userDir=userDir+File.separator+qpidHomeDir;
        }

        File file = new File(userDir);
        String homePath = file.getAbsolutePath();

        BrokerOptions brokerOptions=new BrokerOptions();

        brokerOptions.setConfigProperty("qpid.work_dir", tmpFolder.getAbsolutePath());
        brokerOptions.setConfigProperty("qpid.amqp_port",amqpPort);
        brokerOptions.setConfigProperty("qpid.home_dir", homePath);
        brokerOptions.setInitialConfigurationLocation(homePath + "/"+configFileName);

        return brokerOptions;
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(AmqpReceiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

}
