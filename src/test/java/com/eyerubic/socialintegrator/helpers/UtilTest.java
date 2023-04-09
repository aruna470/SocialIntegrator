package com.eyerubic.socialintegrator.helpers;

import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@ExtendWith(SpringExtension.class)
class UtilTest {
    
    @InjectMocks
    private Util util;

    @Test
    void getCurrentDateTimeUtc() {
       String currentDateTimeUtc = util.getCurrentDateTimeUtc();

       Timestamp timestamp = new Timestamp(System.currentTimeMillis());
       SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

       assertEquals(currentDateTimeUtc, dateFormat.format(timestamp));
    }
}
