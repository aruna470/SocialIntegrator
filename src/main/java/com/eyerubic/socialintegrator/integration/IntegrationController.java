package com.eyerubic.socialintegrator.integration;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.eyerubic.socialintegrator.Constants;
import com.eyerubic.socialintegrator.helpers.AppLogger;
import com.eyerubic.socialintegrator.helpers.ContextData;

@RestController
public class IntegrationController {
    
    @Autowired
    private IntegrationService integrationService;

    @Resource(name = "requestScopedBeanAppLogger")
    AppLogger appLogger;

    @Resource(name = "requestScopedBeanContextData")
    ContextData contextData;

    @PostMapping(value="/integration/")
    public ResponseEntity<IntegrationDTO> integration(@Valid @RequestBody IntegrationDTO integrationDTO) {
        IntegrationDTO integrationDTORes = integrationService.create(integrationDTO, Integer.parseInt(contextData.getUserId()));
        appLogger.writeActivityLog("Integration created. Name:" + integrationDTORes.getName(), Constants.ACT_CRT_INT);
        return new ResponseEntity<>(integrationDTORes, HttpStatus.OK);
    }
}
