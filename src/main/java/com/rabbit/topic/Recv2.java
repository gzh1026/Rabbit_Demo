package com.rabbit.topic;

import com.rabbit.util.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class Recv2 {
    private static final String exchange_name = "test_exchange_topic";
    private static final String queue_name = "TEST_QUEUE_topic_2";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(queue_name, false, false, false, null);
        channel.basicQos(1);
        channel.queueBind(queue_name, exchange_name, "goods.#");
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
        channel.basicConsume(queue_name, false,Consumer);
    }
}
