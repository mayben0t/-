package rocketmq;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendCallback;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;

public class PProducer {
    /**
     * 6步
     *
     *
     * rabbitmq步骤：
     * 1.生成指定对象
     * 2.开启channel
     * 3.声明exchange
     * 4.声明queue
     * 5.绑定exchange和queue
     * 6.发送消息
     * 7.关闭
     *
     *
     *
     * */
    
    public static void main(String[] args)throws Exception {
        /**
         * 1.创建消息生产者producer，并制定生产者组名
         * 2.指定Nameserver地址
         * 3.启动producer
         * 4.生成message对象，指定主题topic tag和消息体
         * 5.发送消息
         * 6.关闭producer
         */
        //1.
        DefaultMQProducer producer = new DefaultMQProducer("group-a");
        //2.
        producer.setNamesrvAddr("localhost:9876");
        //3.
        producer.start();
        //4.
        Message message = new Message("topic","msg","哈哈哈".getBytes());
        //5.1 同步发送消息
        producer.send(message);
        //5.2 异步发送消息  多了个回调的函数作为参数
        producer.send(message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println("success:"+sendResult);
            }

            @Override
            public void onException(Throwable throwable) {
                System.out.println("failed:"+throwable);
            }
        });
        //6.关闭producer
        producer.shutdown();

    }
}
