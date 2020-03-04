package rocketmq;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;

public class Producer {


    public static void main(String[] args)throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("producer-group");
        producer.setNamesrvAddr("localhost:9876");
        producer.setInstanceName("producer");
        producer.start();
        try {
            for(int i=0;i<10;i++){
                Message msg = new Message("producer-topic","别再靠近我了",("别再靠近我了."+i).getBytes());
                SendResult send = producer.send(msg);
                System.out.println(send.toString());
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        producer.shutdown();
    }
}
