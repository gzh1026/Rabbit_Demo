package com.rabbit.confirm;

import com.rabbit.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {
    private static final String QUEUE_NAME = "test_queue_confirm";
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //生产者调用，将channel设为confirm模式
        channel.confirmSelect();
        String msg = "hello confirm batch";
        //批量发送
        for (int i = 0; i < 20; i++) {
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
        }
        //确认
        if (!channel.waitForConfirms()) {
            System.out.println("massage send failed");
        }
        else{
            System.out.println("send ok");
        }
        channel.close();
        connection.close();
    }
}
