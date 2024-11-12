package interhack.api.invoice.services;

import interhack.api.clients.repository.IClientRepository;
import interhack.api.companies.repository.ICompanyRepository;
import interhack.api.invoice.models.dtos.request.InvoiceRequestDto;
import interhack.api.invoice.models.dtos.responses.InvoiceResponse;
import interhack.api.invoice.models.entities.Invoice;
import interhack.api.invoice.repositories.IInvoiceRepository;
import interhack.api.shared.dto.enums.EStatus;
import interhack.api.shared.dto.response.ApiResponse;
import interhack.api.shared.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceService implements IInvoiceService{

    private final IInvoiceRepository invoiceRepository;
    private final ICompanyRepository companyRepository;
    private final IClientRepository clientRepository;
    private final ModelMapper modelMapper;

    public InvoiceService(IInvoiceRepository invoiceRepository, ICompanyRepository companyRepository, IClientRepository clientRepository, ModelMapper modelMapper) {
        this.invoiceRepository = invoiceRepository;
        this.companyRepository = companyRepository;
        this.clientRepository = clientRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ApiResponse<List<InvoiceResponse>> getInvoices() {
        var invoices = invoiceRepository.findAll();

        var invoicesResponse = invoices.stream()
                .map(invoice -> modelMapper.map(invoice, InvoiceResponse.class))
                .collect(Collectors.toList());

        return new ApiResponse<>("Facturas encontradas", EStatus.SUCCESS, invoicesResponse);
    }

    @Override
    public ApiResponse<InvoiceResponse> getInvoiceById(Long invoiceId) {
        var invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la factura con id " + invoiceId));

        var invoiceResponse = modelMapper.map(invoice, InvoiceResponse.class);

        return new ApiResponse<>("Factura encontrada", EStatus.SUCCESS, invoiceResponse);
    }

    @Override
    public ApiResponse<List<InvoiceResponse>> getInvoiceByCompanyId(Long companyId) {

        var invoices = invoiceRepository.findByCompanyCompanyId(companyId);

        var invoicesResponse = invoices.stream()
                .map(invoice -> modelMapper.map(invoice, InvoiceResponse.class))
                .collect(Collectors.toList());

        return new ApiResponse<>("Facturas encontradas", EStatus.SUCCESS, invoicesResponse);
    }

    @Override
    public ApiResponse<InvoiceResponse> createInvoice(InvoiceRequestDto request) {
        var clientEntity = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el cliente con id " + request.getClientId()));

        var companyEntity = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la empresa con id " + request.getCompanyId()));

        if (request.getAmount() <= 0) {
            return new ApiResponse<>("El monto de la factura debe ser mayor a 0", EStatus.ERROR, null);
        }

        if (request.getIssueDate().isAfter(request.getDueDate())) {
            return new ApiResponse<>("La fecha de emisión no puede ser mayor a la fecha de vencimiento", EStatus.ERROR, null);
        }
        var invoice = new Invoice(clientEntity, companyEntity, request.getAmount(), request.getCurrencyType(), request.getIssueDate(), request.getDueDate());

        var newInvoice = invoiceRepository.save(invoice);

        var invoiceResponse = modelMapper.map(newInvoice, InvoiceResponse.class);

        return new ApiResponse<>("Factura creada", EStatus.SUCCESS, invoiceResponse);
    }
}
