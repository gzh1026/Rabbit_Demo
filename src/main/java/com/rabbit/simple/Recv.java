package com.rabbit.simple;

import com.rabbitmq.client.*;
import com.rabbit.util.ConnectionUtils;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;


public class Recv {
    private static final String TEST_NAME = "test_simple";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(TEST_NAME,false,false,false,null);
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                String msgstring = new String(body, StandardCharsets.UTF_8);
                System.out.println("new recv is  [  " + msgstring+"  ]");
            }
        };
        channel.basicConsume(TEST_NAME,true,consumer);
    }
}
