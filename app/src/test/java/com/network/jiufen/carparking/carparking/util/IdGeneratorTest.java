package com.network.jiufen.carparking.carparking.util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by asus on 2017/10/14.
 */
public class IdGeneratorTest {

    @Test
    public void testGenerator()
    {
        System.out.println(IdGenerator.INSTANCE.nextId());

    }

}