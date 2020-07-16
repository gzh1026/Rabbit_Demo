package com.rabbit.topic;

import com.rabbit.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {
    private static String Exchange_NAME = "test_exchange_topic";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(Exchange_NAME,"topic");
        String msg="商品";
        channel.basicPublish(Exchange_NAME, "goods.add", null, msg.getBytes());
        System.out.println("send---" + msg);
        channel.close();
        connection.close();
    }
}
