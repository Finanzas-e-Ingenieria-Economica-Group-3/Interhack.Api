package interhack.api.banking.models.dtos.requests;

import interhack.api.banking.models.enums.EPeriod;
import interhack.api.banking.models.enums.ERateType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RateRequest {

    private Double value;
    private String type;
    private String period;
}
