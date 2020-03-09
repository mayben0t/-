package selfRocket;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.MessageQueueSelector;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageQueue;
import lombok.Data;
import lombok.experimental.Accessors;
import rocketmq.Producer;

import java.util.ArrayList;
import java.util.List;

public class Provider {

    public static void main(String[] args) throws Exception{
        DefaultMQProducer producer = new DefaultMQProducer("group-seq");
        producer.setNamesrvAddr("localhost:9876");
        producer.start();
        String[] tags = {"tagA","tagB","tagC"};
        List<Order> list = new Provider().getList();
        for (int i = 0; i < list.size(); i++) {
            Order order = list.get(i);
            Message message = new Message("wx-seq", tags[i%tags.length], order.toString().getBytes());
            SendResult send = producer.send(message, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> list, Message message, Object o) {
                    long orderNo = Long.valueOf(String.valueOf(o));
                    long index = orderNo % list.size();

                    return list.get((int) index);
                }
            }, order.getOrder());
            System.out.println(send.getSendStatus()+":"+new String(message.getBody()));
        }

        producer.shutdown();
    }





    List<Order> getList(){
        List<Order> res = new ArrayList<>();

        Order o101a = new Order().setId(1).setOrder("00000001").setDesc("创建");
        Order o101b = new Order().setId(2).setOrder("00000001").setDesc("已付款");
        Order o101c = new Order().setId(1).setOrder("00000001").setDesc("发货");
        Order o101d = new Order().setId(1).setOrder("00000001").setDesc("完结");
        Order o102a = new Order().setId(2).setOrder("00000002").setDesc("创建");
        Order o103a = new Order().setId(3).setOrder("00000003").setDesc("创建");
        Order o102b = new Order().setId(2).setOrder("00000002").setDesc("已付款");
        Order o102c = new Order().setId(2).setOrder("00000002").setDesc("发货");
        Order o103b = new Order().setId(3).setOrder("00000003").setDesc("已付款");
        Order o103c = new Order().setId(3).setOrder("00000003").setDesc("发货");
        Order o102d = new Order().setId(2).setOrder("00000002").setDesc("完结");
        res.add(o101a);
        res.add(o101b);
        res.add(o101c);
        res.add(o101d);
        res.add(o102a);
        res.add(o103a);
        res.add(o102b);
        res.add(o102c);
        res.add(o103b);
        res.add(o103c);
        res.add(o102d);
        return res;
    }



}

@Data
@Accessors(chain = true)
class Order{
    private Integer id;
    private String order;
    //创建 已付款 发货 已完结
    private String desc;
}
