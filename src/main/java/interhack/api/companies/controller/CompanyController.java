package interhack.api.companies.controller;

import interhack.api.shared.dto.response.ApiResponse;
import interhack.api.companies.model.dto.CompanyResponseDto;
import interhack.api.companies.service.ICompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador para usuarios
 * @author Jamutaq Ortega
 */
@Tag(name = "Company")
@RequestMapping("/api/v1/companies")
@RestController
public class CompanyController {
    private final ICompanyService companyService;

    public CompanyController(ICompanyService companyService) {
        this.companyService = companyService;
    }

    @Operation(summary = "Obtiene el perfil de una empresa")
    @GetMapping("/profile/{companyId}")
    public ResponseEntity<ApiResponse<CompanyResponseDto>> profile(@PathVariable Long companyId) {
        var res = companyService.profile(companyId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Elimina una empresa (ADMIN)")
    @DeleteMapping("/delete/{companyId}")
    public ResponseEntity<ApiResponse<Object>> deleteById(@PathVariable Long companyId) {
        var res = companyService.deleteById(companyId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
