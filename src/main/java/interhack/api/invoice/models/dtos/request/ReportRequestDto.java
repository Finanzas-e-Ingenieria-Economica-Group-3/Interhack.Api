package interhack.api.invoice.models.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportRequestDto {
    private Long invoiceId;
    private Long bankId;
}
