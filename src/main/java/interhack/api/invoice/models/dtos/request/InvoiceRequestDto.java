package interhack.api.invoice.models.dtos.request;

import interhack.api.invoice.models.enums.ECurrencyType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceRequestDto {

    @NotNull(message = "The invoice clientId is required")
    private Long clientId;

    @NotNull(message = "The invoice companyId is required")
    private Long companyId;

    @NotNull(message = "The invoice amount is required")
    private Long amount;

    @NotNull(message = "The invoice description is required")
    private ECurrencyType currencyType;

    @NotNull(message = "The invoice issueDate is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate issueDate;

    @NotNull(message = "The invoice dueDate is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDate;

}
