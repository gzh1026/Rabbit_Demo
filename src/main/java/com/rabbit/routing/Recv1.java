package com.rabbit.routing;

import com.rabbit.util.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class Recv1 {
    private static final String exchange_name = "TEST_EXCHANG_DIRECT";
    private static final String queue_name = "TEST_QUEUE_DIRECT";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(queue_name, false, false, false, null);
        channel.basicQos(1);
        channel.queueBind(queue_name, exchange_name, "error");
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
