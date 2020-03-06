package rocketmq;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.alibaba.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;

public class Producer2 {

    /**
     * 1.生成producer  指定groupName
     * 2.指定NameServer
     * 3.生成producer对象
     * 4.构造消息  包括指定topic  tag 以及body
     * 5.发送消息 （同步发送 异步发送 单向发送）
     * 6.关闭producer
     * @param args
     */

    public static void main(String[] args) throws Exception{
        DefaultMQProducer producer = new DefaultMQProducer("gourp-D");
        producer.setNamesrvAddr("localhost:9876");
        producer.start();

        for(int i = 0;i<10;i++){
            Message msg = new Message("SharkTopic","tag1",("第一次版本"+i).getBytes());
            producer.send(msg);
            System.out.println("发送消息:【"+new String(msg.getBody())+"】成功");
        }

        producer.shutdown();
    }

    static class Consumer{
        /**
         * 1.构建consumer对象 指定consumerGroup
         * 2.指定NameServer地址
         * 3.consumer订阅
         * 4.指定消息消费逻辑
         * 5.启动消费者
         *
         *
         * @param args
         */
        public static void main(String[] args) throws Exception{
            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group-D");
            consumer.setMessageModel(MessageModel.CLUSTERING);
            consumer.setNamesrvAddr("localhost:9876");
            consumer.subscribe("SharkTopic","tag1");
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                    list.stream().forEach(item->{
                        System.out.println(String.format("开始消费:【%s】",new String(item.getBody())));
                    });
                    return null;
                }
            });
            consumer.start();

        }
    }


}
