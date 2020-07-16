package com.rabbit.pb;

import com.rabbit.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {
    private static final String exchang_name = "test_exchang_fanout";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        //声明交换机
        channel.exchangeDeclare(exchang_name, "fanout");
        String msg = "hello ps";
        channel.basicPublish(exchang_name, "", null, msg.getBytes());
        System.out.println("send ... :"+msg);
        channel.close();
        connection.close();
    }
}
