package interhack.api.banking.models.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankRequest {
    private String Name;
    private String ruc;
    private RateRequest rate;
}
