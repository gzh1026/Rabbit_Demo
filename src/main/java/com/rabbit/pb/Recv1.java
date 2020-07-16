package com.rabbit.pb;

import com.rabbit.util.ConnectionUtils;
import com.rabbitmq.client.*;
import sun.rmi.rmic.Main;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class Recv1 {
    private static final String QUEUE_NAME = "text_queue_fanout_email";
    private static final String exchang_name = "test_exchang_fanout";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        //队列声明
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //绑定队列到交换机
        channel.queueBind(QUEUE_NAME, exchang_name, "");
        channel.basicQos(1);
        //定义一个消费者
        Consumer Consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msgstring = new String(body, StandardCharsets.UTF_8);
                System.out.println("[1] --- new recv is  [  " + msgstring+"  ]");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("[1] --- done");
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
            }
        };
        boolean autoACK=false;
        channel.basicConsume(QUEUE_NAME, false,Consumer);
    }
}
