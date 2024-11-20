package interhack.api.invoice.models.dtos.responses;

import interhack.api.banking.models.dtos.responses.BankResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportResponseDto {
    private Long reportId;
    private double discount;
    private double netAmount;
    private double tcea;
    private InvoiceResponse invoice;
    private BankResponse bank;
}
