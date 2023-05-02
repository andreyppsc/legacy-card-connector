package ai.flowx.quickstart.connector.service.impl;

import ai.flowx.commons.kafka.KafkaUtils;
import ai.flowx.commons.trace.aop.Trace;
import ai.flowx.quickstart.connector.dto.CardCreatedResponseDto;
import ai.flowx.quickstart.connector.dto.KafkaRequestMessageDTO;
import ai.flowx.quickstart.connector.dto.KafkaResponseMessageDTO;
import ai.flowx.quickstart.connector.service.CardsApiService;
import ai.flowx.quickstart.connector.service.MessageHandlerService;
import ai.flowx.quickstart.connector.service.MessageSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.header.Headers;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientException;

@Trace
@Slf4j
@Service
@RequiredArgsConstructor
public class MessageHandlerServiceImpl implements MessageHandlerService {

    private final MessageSenderService messageSenderService;
    private final CardsApiService apiService;

    @Override
    public void process(KafkaRequestMessageDTO kafkaMessage, Headers headers) {
        String processInstanceUuid = KafkaUtils.extractHeaderString(headers, "processInstanceUuid");

        KafkaResponseMessageDTO.KafkaResponseMessageDTOBuilder responseMessageDTOBuilder = KafkaResponseMessageDTO.builder();

        try {
            CardCreatedResponseDto responseBody = apiService.createCard(kafkaMessage.getHolderName());
            responseMessageDTOBuilder
                    .cardId(responseBody.getCardId())
                    .pan(responseBody.getPan())
                    .expiryDate(responseBody.getExpiryDate())
                    .accountIBAN(responseBody.getAccountIBAN());
        } catch (WebClientException e) {
            responseMessageDTOBuilder.errorMessage(e.getMessage());
            messageSenderService.sendMessage(headers, processInstanceUuid, responseMessageDTOBuilder.build());
        }

        messageSenderService.sendMessage(headers, processInstanceUuid, responseMessageDTOBuilder.build());
    }
}
