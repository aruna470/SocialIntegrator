package com.eyerubic.socialintegrator.integration;

import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.eyerubic.socialintegrator.Constants;
import com.eyerubic.socialintegrator.helpers.Mail;
import com.eyerubic.socialintegrator.helpers.Util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SpringExtension.class)
class IntegrationServiceTest {
    
    @Mock
    private IntegrationRepository integrationRepository;

    @Mock
    private Util util;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private IntegrationService integrationService;

    @Test
    void integrationCreate() {
        IntegrationDTO integrationDTO = getMockIntegrationDTO();
        Integration integration = getMockIntegration();
        when(integrationRepository.save(ArgumentMatchers.any(Integration.class))).thenReturn(integration);
        when(modelMapper.map(integrationDTO, Integration.class)).thenReturn(integration);
        when(modelMapper.map(integration, IntegrationDTO.class)).thenReturn(integrationDTO);

        IntegrationDTO integrationDTORes = integrationService.create(integrationDTO, 1);
        assertEquals(integrationDTORes.getName(), integrationDTO.getName());
    }

    private Integration getMockIntegration() {
        Integration integration = new Integration();
        integration.setName("MyTestIntegration");

        return integration;
    }

    private IntegrationDTO getMockIntegrationDTO() {
        IntegrationDTO postIntegrationDTO = new IntegrationDTO();
        postIntegrationDTO.setName("MyTestIntegration");
        return postIntegrationDTO;
    }
}
