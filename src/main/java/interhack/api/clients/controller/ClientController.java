package interhack.api.clients.controller;

import interhack.api.clients.models.dto.request.ClientRequestDto;
import interhack.api.clients.models.dto.response.ClientResponseDto;
import interhack.api.clients.services.IClientService;
import interhack.api.shared.dto.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Client")
@RequestMapping("/api/v1")
@RestController
public class ClientController {
    private final IClientService clientService;

    public ClientController(IClientService clientService) {
        this.clientService = clientService;
    }

    // URL: http://localhost:8080/api/v1/clients/{clientId}
    @Operation(summary = "Obtiene el cliente por id")
    @GetMapping("/clients/{clientId}")
    public ResponseEntity<ApiResponse<ClientResponseDto>> getClientById(@PathVariable Long clientId) {
        var response = clientService.getClientById(clientId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/v1/companies/{companyId}/clients
    @Operation(summary = "Crea un cliente")
    @PostMapping("/companies/{companyId}/clients")
    public ResponseEntity<ApiResponse<ClientResponseDto>> postClient(
            @PathVariable Long companyId, @Valid @RequestBody ClientRequestDto request) {
        var response = clientService.addClient(request, companyId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // URL: http://localhost:8080/api/v1/clients/{clientId}
    @Operation(summary = "Actualiza un cliente")
    @PutMapping("/clients/{clientId}")
    public ResponseEntity<ApiResponse<ClientResponseDto>> putClient(@PathVariable Long clientId, @Valid @RequestBody ClientRequestDto request) {
        var response = clientService.updateClient(clientId, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/v1/clients/{clientId}
    @Operation(summary = "Elimina un cliente")
    @DeleteMapping("/clients/{clientId}")
    public ResponseEntity<ApiResponse<ClientResponseDto>> deleteClient(@PathVariable Long clientId) {
        var response = clientService.deleteClient(clientId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/v1/clients
    @Operation(summary = "Obtiene todos los clientes")
    @GetMapping("/clients")
    public ResponseEntity<ApiResponse<List<ClientResponseDto>>> getAllClients() {
        var response = clientService.getAllClients();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/v1/companies/{companyId}/clients
    @Operation(summary = "Obtiene todos los clientes de una empresa")
    @GetMapping("/companies/{companyId}/clients")
    public ResponseEntity<ApiResponse<List<ClientResponseDto>>> getClientsByCompany(@PathVariable Long companyId) {
        var response = clientService.getClientsByCompanyId(companyId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
