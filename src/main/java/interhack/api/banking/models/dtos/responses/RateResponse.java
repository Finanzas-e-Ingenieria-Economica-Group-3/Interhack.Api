package interhack.api.banking.models.dtos.responses;

import interhack.api.banking.models.enums.EPeriod;
import interhack.api.banking.models.enums.ERateType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RateResponse {
    private Double value;
    private ERateType type;
    private EPeriod period;
}
