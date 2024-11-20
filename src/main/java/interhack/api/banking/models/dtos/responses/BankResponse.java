package interhack.api.banking.models.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankResponse {
    private Long bankId;
    private String name;
    private String imageUrl;
    private String ruc;
    private RateResponse rate;
}
