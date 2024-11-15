package interhack.api.invoice.controllers;

import interhack.api.invoice.models.dtos.request.ReportRequestDto;
import interhack.api.invoice.services.IReportService;
import interhack.api.shared.dto.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Report")
@RequestMapping("/api/v1")
@RestController
public class ReportController {
    private final IReportService reportService;

    public ReportController(IReportService reportService) {
        this.reportService = reportService;
    }

    // URL: http://localhost:8080/api/v1/reports
    @Operation(summary = "Obtiene todos los reportes")
    @GetMapping("/reports")
    public ResponseEntity<ApiResponse<?>> getReports() {
        var response = reportService.getReports();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/v1/reports/{reportId}
    @Operation(summary = "Obtiene el reporte por id")
    @GetMapping("/reports/{reportId}")
    public ResponseEntity<ApiResponse<?>> getReportById(@PathVariable Long reportId) {
        var response = reportService.getReportById(reportId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/v1/create_report/invoices/{invoiceId}/banks/{bankId}
    @Operation(summary = "Genera reporte de factura")
    @PostMapping("/invoices/{invoiceId}/banks/{bankId}/reports")
    public ResponseEntity<ApiResponse<?>> postReport(
            @PathVariable Long invoiceId,
            @PathVariable Long bankId
    ) {
        var request = new ReportRequestDto(invoiceId, bankId);
        var response = reportService.createReport(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // URL: http://localhost:8080/api/v1/update_report/invoices/{invoiceId}/banks/{bankId}
    @Operation(summary = "Actualiza reporte de factura")
    @PutMapping("/invoices/{invoiceId}/banks/{bankId}/reports")
    public ResponseEntity<ApiResponse<?>> putReport(
            @PathVariable Long invoiceId,
            @PathVariable Long bankId
    ) {
        var request = new ReportRequestDto(invoiceId, bankId);
        var response = reportService.updateReport(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/v1/delete_report/{reportId}
    @Operation(summary = "Elimina reporte de factura")
    @DeleteMapping("/reports/{reportId}")
    public ResponseEntity<ApiResponse<?>> deleteReport(@PathVariable Long reportId) {
        var response = reportService.deleteReport(reportId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/v1/reports/companies/{companyId}
    @Operation(summary = "Obtiene los reportes por id de empresa")
    @GetMapping("/reports/companies/{companyId}")
    public ResponseEntity<ApiResponse<?>> getReportsByCompanyId(@PathVariable Long companyId) {
        var response = reportService.getReportsByCompanyId(companyId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
