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

    public final static String EXCHANGE_NAME = "todo-exchange";
    public final static String QUEUE_NAME = "todo";
    public final static String ROUTING_KEY = "todo-key";

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    Queue queue() {
        return new Queue(QUEUE_NAME, false);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue)
            .to(exchange)
            .with(ROUTING_KEY);
    }

    @Bean
    Broker broker() throws Exception {
        Broker broker=new Broker();
        broker.startup(brokerOptions());
        return broker;
    }

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
        container.setQueueNames(QUEUE_NAME);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(AmqpReceiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

}
