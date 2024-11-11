package interhack.api.clients.services;

import interhack.api.clients.models.dto.request.ClientRequestDto;
import interhack.api.clients.models.dto.response.ClientResponseDto;
import interhack.api.shared.dto.response.ApiResponse;

import java.util.List;

public interface IClientService {
    ApiResponse<ClientResponseDto> addClient(ClientRequestDto request);
    ApiResponse<ClientResponseDto> updateClient(Long clientId, ClientRequestDto request);
    ApiResponse<ClientResponseDto> getClientById(Long clientId);
    ApiResponse<ClientResponseDto> deleteClient(Long clientId);
    ApiResponse<List<ClientResponseDto>> getAllClients();
}
