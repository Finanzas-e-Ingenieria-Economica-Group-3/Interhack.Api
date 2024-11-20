package interhack.api.banking.controllers;

import interhack.api.banking.models.dtos.requests.BankRequest;
import interhack.api.banking.models.dtos.responses.BankResponse;
import interhack.api.banking.services.IBankService;
import interhack.api.shared.dto.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Bank")
@RequestMapping("/api/v1")
@RestController
public class BankController {
    private final IBankService bankService;

    public BankController(IBankService bankService) {
        this.bankService = bankService;
    }

    // URL: http://localhost:8080/api/v1/banks
    @Operation(summary = "Obtiene todos los bancos")
    @GetMapping("/banks")
    public ResponseEntity<ApiResponse<List<BankResponse>>> getBanks() {
        var response = bankService.getBanks();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/v1/banks/{bankId}
    @Operation(summary = "Obtiene el banco por id")
    @GetMapping("/banks/{bankId}")
    public ResponseEntity<ApiResponse<BankResponse>> getBankById(@PathVariable Long bankId) {
        var response = bankService.getBankById(bankId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/v1/banks
    @Operation(summary = "Crea un banco")
    @PostMapping("/banks")
    public ResponseEntity<ApiResponse<BankResponse>> createBank(@RequestBody BankRequest bankRequest) {
        var response = bankService.createBank(bankRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
