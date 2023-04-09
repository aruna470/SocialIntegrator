package com.eyerubic.socialintegrator.helpers;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.springframework.stereotype.Component;

@Component
public class Util {
    
    public Util() {
        // Do Nothing
    }

    public String getCurrentDateTimeUtc() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return dateFormat.format(timestamp);
    }
}
