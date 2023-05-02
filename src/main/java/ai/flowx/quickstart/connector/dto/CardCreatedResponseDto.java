package ai.flowx.quickstart.connector.dto;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class CardCreatedResponseDto {
    private UUID cardId;
    private String holderName;
    private String pan;
    private Date expiryDate;
    private String accountIBAN;
}
