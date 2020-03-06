package rocketmq;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.rabbitmq.client.DefaultConsumer;

import java.util.List;

public class CConsumer {
    /**
     * 1.创建消费者consumer，制定消费者组名
     * 2.指定Nameserver地址
     * 3.订阅主题Topic和Tag
     * 4.设置回调函数，处理消息
     * 5.启动消费者consumer
     * @param args
     */

    public static void main(String[] args) throws Exception{
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group-b");
        consumer.setNamesrvAddr("localhost:9876");
        consumer.subscribe("wt-topic","msg");
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            //接收消息内容
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                System.out.println(list);
                list.forEach(item->{
                    String object = new String(item.getBody());
                    User user = JSONObject.parseObject(object, User.class);
                    System.out.println(user);
                });
                return null;
            }
        });
        consumer.start();
    }
}
