package rabbit;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.rabbitmq.client.*;
import com.rabbitmq.client.impl.AMQConnection;
import rabbit.b.User;

import java.io.IOException;
import java.util.Map;

public class Demo {

    public static String exchangeName = "19exchangeName";
    public static String queueName ="sharks.product.create";

    public static void main(String[] args) throws Exception {
        Connection connection = getConnection();
        Channel channel = connection.createChannel();

//
//        channel.exchangeDeclare(exchangeName,"topic",true,false,null);
//        channel.queueDeclare(queueName,true,false,false,null);
//        channel.queueBind(queueName,exchangeName,"");
        rabbit.a.User user = new rabbit.a.User();
        user.setId(1);
        user.setNamec("测试");
        user.setDesc("wqewae");
        String s = JSONObject.toJSONString(user);
        System.out.println(s);
        channel.basicPublish(exchangeName,"",false,false,null,
               s.getBytes());

//        channel.close();
//        connection.close();

    }


    public static Connection getConnection() throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setPort(5672);
        factory.setHost("127.0.0.1");
        factory.setVirtualHost("/");

        Connection connection = factory.newConnection();
        return connection;
    }
}

class consumer{
    public static void main(String[] args)  throws Exception{
        Connection connection = Demo.getConnection();
        Channel channel = connection.createChannel();
        Map<String, Object> arguments = null;
        channel.basicConsume(Demo.queueName,true,arguments,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                User user = JSONObject.parseObject(new String(body, "UTF-8"), User.class);
                System.out.println(user.getId()+"  :   "+user.getName());
            }
        });
//        channel.close();
//        connection.close();
    }
}
