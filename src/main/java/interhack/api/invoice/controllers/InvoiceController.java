package interhack.api.invoice.controllers;

import interhack.api.invoice.models.dtos.request.InvoiceRequestDto;
import interhack.api.invoice.models.dtos.responses.InvoiceResponse;
import interhack.api.invoice.services.IInvoiceService;
import interhack.api.shared.dto.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Invoice")
@RequestMapping("/api/v1")
@RestController
public class InvoiceController {
    private final IInvoiceService invoiceService;

    public InvoiceController(IInvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    // URL: http://localhost:8080/api/v1/invoices
    @Operation(summary = "Obtiene todas las facturas")
    @GetMapping("/invoices")
    public ResponseEntity<ApiResponse<List<InvoiceResponse>>> getInvoices() {
        var response = invoiceService.getInvoices();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/v1/invoices/{invoiceId}
    @Operation(summary = "Obtiene la factura por id")
    @GetMapping("/invoices/{invoiceId}")
    public ResponseEntity<ApiResponse<InvoiceResponse>> getInvoiceById(@PathVariable Long invoiceId) {
        var response = invoiceService.getInvoiceById(invoiceId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Crea la factura")
    @PostMapping("/invoices")
    public ResponseEntity<ApiResponse<InvoiceResponse>> createInvoice(
            @Valid @RequestBody InvoiceRequestDto requestDto
    ) {
        var response = invoiceService.createInvoice(requestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // URL: http://localhost:8080/api/v1/companies/{companyId}/invoices
    @Operation(summary = "Obtiene las facturas por id de empresa")
    @GetMapping("/companies/{companyId}/invoices")
    public ResponseEntity<ApiResponse<List<InvoiceResponse>>> getInvoiceByCompanyId(@PathVariable Long companyId) {
        var response = invoiceService.getInvoiceByCompanyId(companyId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
