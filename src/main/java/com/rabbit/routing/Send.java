package com.rabbit.routing;

import com.rabbit.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {
    private static final String exchange_name = "TEST_EXCHANG_DIRECT";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(exchange_name, "direct");
        String msg = "hello direct";
        System.out.println(msg+" have sent...");
        //路由key是error
        String routingKey = "info";
        channel.basicPublish(exchange_name, routingKey, null, msg.getBytes());
        channel.close();
        connection.close();
    }
}
