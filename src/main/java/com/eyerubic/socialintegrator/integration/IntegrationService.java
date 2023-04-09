package com.eyerubic.socialintegrator.integration;

import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import com.eyerubic.socialintegrator.helpers.Util;


@Service
@Transactional
public class IntegrationService {

    @Value("${wh.whatsApp}")
    private String whatsAppWh;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IntegrationRepository integrationRepository;

    @Autowired
    private Util util;
    
    public IntegrationDTO create(IntegrationDTO integrationDTO, Integer userId) { 
        UUID uuid = UUID.randomUUID();
        Integration integration = integrationDtoToEntity(integrationDTO);
        integration.setCode(uuid.toString());
        integration.setCreatedAt(util.getCurrentDateTimeUtc());
        integration.setUpdatedAt(util.getCurrentDateTimeUtc());
        integration.setUserId(userId);
        Integration res = integrationRepository.save(integration);

        IntegrationDTO resIntegrationDto = entityToIntegrationDto(res);
        resIntegrationDto.setWaWebHookUrl(whatsAppWh + integration.getCode());

        return resIntegrationDto;
    }

    private Integration integrationDtoToEntity(IntegrationDTO integrationDTO) {
        return modelMapper.map(integrationDTO, Integration.class);
    }

    private IntegrationDTO entityToIntegrationDto(Integration integration) {
        return modelMapper.map(integration, IntegrationDTO.class);
    }
}
