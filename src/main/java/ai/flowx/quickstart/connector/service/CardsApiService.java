package ai.flowx.quickstart.connector.service;

import ai.flowx.quickstart.connector.dto.CardCreatedResponseDto;

public interface CardsApiService {
    CardCreatedResponseDto createCard(String holderName);
}