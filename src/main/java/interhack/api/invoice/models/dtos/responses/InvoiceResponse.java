package interhack.api.invoice.models.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceResponse {
    private Long invoiceId;
    private Long clientId;
    private Long companyId;
    private Long amount;
    private String currencyType;
    private LocalDate issueDate;
    private LocalDate dueDate;
}
