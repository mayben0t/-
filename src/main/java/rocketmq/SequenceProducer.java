package rocketmq;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.MessageQueueSelector;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageQueue;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SequenceProducer {

    public static void main(String[] args) throws Exception{
        DefaultMQProducer producer = new DefaultMQProducer("seq-producer");
        producer.setNamesrvAddr("localhost:9876");
        producer.start();

        String[] tags = new String[]{"TagA","TagB","TagC"};
        Instant instant = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant();
        Date from = Date.from(instant);
        List<OrderDO> orders = new SequenceProducer().buildOrders();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdf.format(from);
        for(int i=0;i<10;i++){
            String body = dateStr +" Hello RocketMQ "+ orders.get(i).getOrderId()+orders.get(i).getDesc();
            Message msg = new Message("SequenceTopic", tags[i % tags.length], "KEY " + i, body.getBytes());
            SendResult res = producer.send(msg, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> list, Message message, Object o) {
                    Long id = Long.valueOf((String) o);
                    long index = id % list.size();
                    return list.get((int) index);
                }
            }, orders.get(i).getOrderId());
            System.out.println(res+", body:"+body);
        }

        producer.shutdown();

    }


    private List<OrderDO> buildOrders() {
        List<OrderDO> orderList = new ArrayList<OrderDO>();

        OrderDO OrderDO = new OrderDO();
        OrderDO.setOrderId("15103111039");
        OrderDO.setDesc("创建");
        orderList.add(OrderDO);

        OrderDO = new OrderDO();
        OrderDO.setOrderId("15103111065");
        OrderDO.setDesc("创建");
        orderList.add(OrderDO);

        OrderDO = new OrderDO();
        OrderDO.setOrderId("15103111039");
        OrderDO.setDesc("付款");
        orderList.add(OrderDO);

        OrderDO = new OrderDO();
        OrderDO.setOrderId("15103117235");
        OrderDO.setDesc("创建");
        orderList.add(OrderDO);

        OrderDO = new OrderDO();
        OrderDO.setOrderId("15103111065");
        OrderDO.setDesc("付款");
        orderList.add(OrderDO);

        OrderDO = new OrderDO();
        OrderDO.setOrderId("15103117235");
        OrderDO.setDesc("付款");
        orderList.add(OrderDO);

        OrderDO = new OrderDO();
        OrderDO.setOrderId("15103111065");
        OrderDO.setDesc("完成");
        orderList.add(OrderDO);

        OrderDO = new OrderDO();
        OrderDO.setOrderId("15103111039");
        OrderDO.setDesc("推送");
        orderList.add(OrderDO);

        OrderDO = new OrderDO();
        OrderDO.setOrderId("15103117235");
        OrderDO.setDesc("完成");
        orderList.add(OrderDO);

        OrderDO = new OrderDO();
        OrderDO.setOrderId("15103111039");
        OrderDO.setDesc("完成");
        orderList.add(OrderDO);
        return orderList;
    }

}
