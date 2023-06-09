package ai.flowx.quickstart.connector.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
public class KafkaResponseMessageDTO {
    private UUID cardId;
    private String pan;
    private Date expiryDate;
    private String accountIBAN;
    private String errorMessage;
}
