package rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.impl.AMQConnection;

import javax.swing.*;

public class Producer {

    public static void main(String[] args) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        String exchangeName = "sharkks";
        String queueName = "sharkks.queue";
        channel.exchangeDeclare(exchangeName,"topic",true);

        channel.queueDeclare(queueName,true,false,false,null);
        channel.queueBind(queueName,exchangeName,"");


        channel.basicPublish(exchangeName,"",true,null,"可以不再失眠吗".getBytes());




        channel.close();
        connection.close();


    }
}
