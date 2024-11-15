package interhack.api.invoice.services;

import interhack.api.banking.models.entities.Rate;
import interhack.api.banking.models.enums.ERateType;
import interhack.api.banking.repositories.IBankRepository;
import interhack.api.companies.repository.ICompanyRepository;
import interhack.api.invoice.models.dtos.request.ReportRequestDto;
import interhack.api.invoice.models.dtos.responses.ReportResponseDto;
import interhack.api.invoice.models.entities.Invoice;
import interhack.api.invoice.models.entities.Report;
import interhack.api.invoice.repositories.IInvoiceRepository;
import interhack.api.invoice.repositories.IReportRepository;
import interhack.api.shared.dto.enums.EStatus;
import interhack.api.shared.dto.response.ApiResponse;
import interhack.api.shared.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static java.lang.Math.pow;

@Service
public class ReportService implements IReportService {

    private final IInvoiceRepository invoiceRepository;
    private final IBankRepository bankRepository;
    private final IReportRepository reportRepository;
    private final ModelMapper modelMapper;
    private final ICompanyRepository companyRepository;

    public ReportService(IInvoiceRepository invoiceRepository, IBankRepository bankRepository, IReportRepository reportRepository, ModelMapper modelMapper, ICompanyRepository companyRepository) {
        this.invoiceRepository = invoiceRepository;
        this.bankRepository = bankRepository;
        this.reportRepository = reportRepository;
        this.modelMapper = modelMapper;
        this.companyRepository = companyRepository;
    }

    private int calculateDays(Invoice invoice) {
        return (int)ChronoUnit.DAYS.between(invoice.getIssueDate(), invoice.getDueDate());
    }

    private double convertEffectiveRateValueToAnotherTimeFactor(Rate currentRate, int days) {
        return pow(1+currentRate.getValue(), (double) days /360)-1;
    }

    private double convertFromNominalToEffectiveRateValue(Rate nominalRate, int conversionDays) {
        int nominalCapitalizationsFactor = switch (nominalRate.getPeriod()) {
            case ANUAL -> 360;
            case SEMESTRAL -> 180;
            case CUATRIMESTRAL -> 120;
            case TRIMESTRAL -> 90;
            case BIMESTRAL -> 60;
            case MENSUAL -> 30;
            case QUINCENAL -> 15;
            case DIARIA -> 1;
        };

        return pow(1+nominalRate.getValue()/nominalCapitalizationsFactor, conversionDays)-1;
    }

    private double calculateDiscountFactor(double rateValue) {
        return rateValue/(1+rateValue);
    }

    private double calculateDiscount(double rateValue, double nominalValue) {
        return rateValue*nominalValue;
    }

    private double calculateNetValue(double nominalValue, double discount) {
        return nominalValue-discount;
    }

    private double calculateTCEA(double invoiceValue, double netValue, int days) {
        return pow((invoiceValue/netValue), (360.0/(double)days))-1;
    }

    @Override
    public ApiResponse<ReportResponseDto> createReport(ReportRequestDto request) {
        var invoice = invoiceRepository.findById(request.getInvoiceId())
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la factura con id " + request.getInvoiceId()));

        var bank = bankRepository.findById(request.getBankId())
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el banco con id " + request.getBankId()));

        var existingReport = reportRepository.findByInvoiceId(invoice.getInvoiceId());
        if (existingReport.isPresent()) {
            return new ApiResponse<>("La factura ya tiene un reporte", EStatus.ERROR, null);
        }

        int days = calculateDays(invoice);

        double effectiveRateValue = bank.getRate().getType().equals(ERateType.NOMINAL) ?
                convertFromNominalToEffectiveRateValue(bank.getRate(), days) :
                convertEffectiveRateValueToAnotherTimeFactor(bank.getRate(), days);

        double discountFactor = calculateDiscountFactor(effectiveRateValue);

        double discount = calculateDiscount(discountFactor, invoice.getAmount());

        double netAmount = calculateNetValue(invoice.getAmount(), discount);

        double tcea = calculateTCEA(invoice.getAmount(), netAmount, days);

        Report report = new Report(discount, netAmount, tcea, invoice, bank);

        var newReport = reportRepository.save(report);

        invoice.setReport(newReport);
        invoiceRepository.save(invoice);

        var invoiceResponse = modelMapper.map(newReport, ReportResponseDto.class);

        return new ApiResponse<>("Factura creada", EStatus.SUCCESS, invoiceResponse);
    }

    @Override
    public ApiResponse<ReportResponseDto> updateReport(ReportRequestDto request) {
        var invoice = invoiceRepository.findById(request.getInvoiceId())
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la factura con id " + request.getInvoiceId()));

        var bank = bankRepository.findById(request.getBankId())
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el banco con id " + request.getBankId()));

        var existingReport = reportRepository.findByInvoiceId(invoice.getInvoiceId());

        if (existingReport.isEmpty()) {
            return new ApiResponse<>("La factura no tiene un reporte", EStatus.ERROR, null);
        }

        int days = calculateDays(invoice);

        double effectiveRateValue = bank.getRate().getType().equals(ERateType.NOMINAL) ?
                convertFromNominalToEffectiveRateValue(bank.getRate(), days) :
                convertEffectiveRateValueToAnotherTimeFactor(bank.getRate(), days);

        double discountFactor = calculateDiscountFactor(effectiveRateValue);

        double discount = calculateDiscount(discountFactor, invoice.getAmount());

        double netAmount = calculateNetValue(invoice.getAmount(), discount);

        double tcea = calculateTCEA(invoice.getAmount(), netAmount, days);

        Report updateReport = existingReport.get();
        updateReport.UpdateReport(discount, netAmount, tcea, invoice, bank);

        var updatedReport = reportRepository.save(updateReport);

        var reportResponse = modelMapper.map(updatedReport, ReportResponseDto.class);

        return new ApiResponse<>("Reporte actualizado", EStatus.SUCCESS, reportResponse);
    }

    @Override
    public ApiResponse<?> deleteReport(Long reportId) {
        var report = reportRepository.findById(reportId)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el reporte con id " + reportId));

        var invoice = invoiceRepository.findById(report.getInvoice().getInvoiceId()).get();
        invoice.setReport(null);
        invoiceRepository.save(invoice);

        reportRepository.delete(report);

        return new ApiResponse<>("Reporte eliminado", EStatus.SUCCESS, null);
    }

    @Override
    public ApiResponse<ReportResponseDto> getReportById(Long reportId) {
        var report = reportRepository.findById(reportId)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el reporte con id " + reportId));

        var reportResponse = modelMapper.map(report, ReportResponseDto.class);

        return new ApiResponse<>("Reporte encontrado", EStatus.SUCCESS, reportResponse);
    }

    @Override
    public ApiResponse<List<ReportResponseDto>> getReports() {
        var reports = reportRepository.findAll();

        List<ReportResponseDto> reportResponses = reports.stream()
                .map(report -> modelMapper.map(report, ReportResponseDto.class))
                .toList();

        return new ApiResponse<>("Reportes encontrados", EStatus.SUCCESS, reportResponses);
    }

    @Override
    public ApiResponse<List<ReportResponseDto>> getReportsByCompanyId(Long companyId) {
        var reports = reportRepository.findByInvoiceCompanyCompanyId(companyId);

        companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la empresa con id " + companyId));

        List<ReportResponseDto> reportResponses = reports.stream()
                .map(report -> modelMapper.map(report, ReportResponseDto.class))
                .toList();

        return new ApiResponse<>("Reportes encontrados", EStatus.SUCCESS, reportResponses);
    }
}
