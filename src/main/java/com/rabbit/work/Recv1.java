package com.rabbit.work;

import com.rabbit.util.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class Recv1 {
    private static final String QUEUE_NAME="test_work_queue";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
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
                }
            }
        };
        channel.basicConsume(QUEUE_NAME,true,Consumer);
    }
}
