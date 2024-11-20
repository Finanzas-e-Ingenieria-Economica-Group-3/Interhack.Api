package interhack.api.invoice.services;

import interhack.api.invoice.models.dtos.request.InvoiceRequestDto;
import interhack.api.invoice.models.dtos.responses.InvoiceResponse;
import interhack.api.shared.dto.response.ApiResponse;

import java.util.List;

public interface IInvoiceService {
    ApiResponse<List<InvoiceResponse>> getInvoices();

    ApiResponse<InvoiceResponse> getInvoiceById(Long invoiceId);

    ApiResponse<List<InvoiceResponse>> getInvoiceByCompanyId(Long companyId);

    ApiResponse<List<InvoiceResponse>> getInvoiceByCompanyIdAndClientId(Long companyId, Long ClientId);

    ApiResponse<InvoiceResponse> createInvoice(InvoiceRequestDto request);
}
