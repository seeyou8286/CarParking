package com.network.jiufen.carparking.carparking.util;

import org.junit.Test;

/**
 * Created by asus on 2017/9/24.
 */
public class SMSenderTest {


    @Test
    public void testSendSMS() throws Exception
    {
        SMSender smSender = new SMSender("18682379493","4412","LTAIlqRFpTsP6gRO","k2Q56aMHFKo3L1cT9C2sVWlyTnCtQh");
        smSender.run();
        Thread thread = new Thread(smSender);
        thread.start();
        System.out.println(smSender.isSuccess());
    }
}