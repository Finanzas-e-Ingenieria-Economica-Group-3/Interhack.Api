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
@RequestMapping("/api/v1/clients")
@RestController
public class ClientController {
    private final IClientService clientService;

    public ClientController(IClientService clientService) {
        this.clientService = clientService;
    }

    @Operation(summary = "Obtiene el cliente por id")
    @GetMapping("/{clientId}")
    public ResponseEntity<ApiResponse<ClientResponseDto>> getClientById(@PathVariable Long clientId) {
        var response = clientService.getClientById(clientId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Crea un cliente")
    @PostMapping
    public ResponseEntity<ApiResponse<ClientResponseDto>> postClient(@Valid @RequestBody ClientRequestDto request) {
        var response = clientService.addClient(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Actualiza un cliente")
    @PutMapping("/{clientId}")
    public ResponseEntity<ApiResponse<ClientResponseDto>> putClient(@PathVariable Long clientId, @Valid @RequestBody ClientRequestDto request) {
        var response = clientService.updateClient(clientId, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Elimina un cliente")
    @DeleteMapping("/{clientId}")
    public ResponseEntity<ApiResponse<ClientResponseDto>> deleteClient(@PathVariable Long clientId) {
        var response = clientService.deleteClient(clientId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene todos los clientes")
    @GetMapping
    public ResponseEntity<ApiResponse<List<ClientResponseDto>>> getAllClients() {
        var response = clientService.getAllClients();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
