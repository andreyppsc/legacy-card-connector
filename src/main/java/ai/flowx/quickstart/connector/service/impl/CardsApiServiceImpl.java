package ai.flowx.quickstart.connector.service.impl;

import ai.flowx.commons.trace.aop.Trace;
import ai.flowx.quickstart.connector.dto.CardCreatedResponseDto;
import ai.flowx.quickstart.connector.dto.CreateCardRequestDto;
import ai.flowx.quickstart.connector.service.CardsApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Trace
@Slf4j
@Service
@RequiredArgsConstructor
public class CardsApiServiceImpl implements CardsApiService {

    @Value("${card-management-api.baseUrl}")
    private String baseUrl;

    @Value("${card-management-api.endpoint}")
    private String endpoint;

    @Override
    public CardCreatedResponseDto createCard(String holderName) {
        CreateCardRequestDto requestBody = new CreateCardRequestDto(holderName);

        WebClient client = WebClient.create(baseUrl);

        return client.post()
                .uri(endpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(CardCreatedResponseDto.class)
                .block();
    }
}
