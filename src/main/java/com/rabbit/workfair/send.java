package com.rabbit.workfair;

import com.rabbit.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class send {
    private static final String QUEUE_NAME="test_work_queue";
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel=connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        int perferchCount=1;
        channel.basicQos(perferchCount);
        for (int i = 0; i < 50; i++) {
            String msg="hello "+i;
            System.out.println("send [  "+msg+"  ]");
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            Thread.sleep(i );
        }
        channel.close();
        connection.close();
    }
}
