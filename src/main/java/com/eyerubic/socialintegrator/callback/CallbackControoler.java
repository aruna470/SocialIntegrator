package com.eyerubic.socialintegrator.callback;

import javax.annotation.Resource;
import com.eyerubic.socialintegrator.helpers.AppLogger;
import com.eyerubic.socialintegrator.helpers.ContextData;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

@RestController
public class CallbackControoler {
    
    @Resource(name = "requestScopedBeanAppLogger")
    AppLogger appLogger;

    @Resource(name = "requestScopedBeanContextData")
    ContextData contextData;

    @PostMapping(value="/callback/whatsapp")
    public String whatsapp(@RequestBody String pBody) {

        //System.out.println(pBody); NOSONAR
        JSONObject obj = new JSONObject(pBody); // NOSONAR
        //System.out.println(obj.getString("email")); NOSONAR
        return "";
    }
}
