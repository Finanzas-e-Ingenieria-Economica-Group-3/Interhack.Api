package interhack.api.invoice.services;

import interhack.api.invoice.models.dtos.request.ReportRequestDto;
import interhack.api.invoice.models.dtos.responses.ReportResponseDto;
import interhack.api.shared.dto.response.ApiResponse;

import java.util.List;

public interface IReportService {
    ApiResponse<ReportResponseDto> createReport(ReportRequestDto request);
    ApiResponse<ReportResponseDto> updateReport(ReportRequestDto request);
    ApiResponse<?> deleteReport(Long reportId);

    ApiResponse<ReportResponseDto> getReportById(Long reportId);
    ApiResponse<ReportResponseDto> getReportByInvoiceId(Long invoiceId);
    ApiResponse<List<ReportResponseDto>> getReports();
    ApiResponse<List<ReportResponseDto>> getReportsByCompanyId(Long invoiceId);
}
