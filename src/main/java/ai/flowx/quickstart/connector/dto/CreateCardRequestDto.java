package ai.flowx.quickstart.connector.dto;

import lombok.Data;

@Data
public class CreateCardRequestDto {
    private String holderName;

    public CreateCardRequestDto(String holderName) {
        this.holderName = holderName;
    }
}
