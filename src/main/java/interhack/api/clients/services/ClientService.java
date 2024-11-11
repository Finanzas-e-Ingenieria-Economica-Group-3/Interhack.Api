package interhack.api.clients.services;

import interhack.api.clients.models.dto.request.ClientRequestDto;
import interhack.api.clients.models.dto.response.ClientResponseDto;
import interhack.api.clients.models.entities.Client;
import interhack.api.clients.repository.IClientRepository;
import interhack.api.shared.dto.enums.EStatus;
import interhack.api.shared.dto.response.ApiResponse;
import interhack.api.shared.exception.ConflictException;
import interhack.api.shared.exception.ResourceNotFoundException;
import interhack.api.users.repository.ICompanyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService implements IClientService {
    private final IClientRepository clientRepository;
    private final ICompanyRepository companyRepository;
    private final ModelMapper modelMapper;

    public ClientService(IClientRepository clientRepository, ICompanyRepository companyRepository, ModelMapper modelMapper) {
        this.clientRepository = clientRepository;
        this.companyRepository = companyRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ApiResponse<ClientResponseDto> addClient(ClientRequestDto request, Long companyId) {
        var companyEntity = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la empresa con id " + companyId));

        if (clientRepository.existsByEmail(request.getEmail())) {
            throw new ConflictException("El email '" + request.getEmail() + "' ya está registrado");
        }

        if (clientRepository.existsByDni(request.getDni())) {
            throw new ConflictException("El DNI '" + request.getDni() + "' ya está registrado");
        }

        var client = new Client(request, companyEntity);

        var newClient = clientRepository.save(client);

        var clientResponse = modelMapper.map(newClient, ClientResponseDto.class);

        return new ApiResponse<>("Cliente creado", EStatus.SUCCESS, clientResponse);
    }

    @Override
    public ApiResponse<ClientResponseDto> updateClient(Long clientId, ClientRequestDto request) {

        var clientEntity = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el cliente con id " + clientId));

        if (!request.getEmail().equals(clientEntity.getEmail()) && clientRepository.existsByEmail(request.getEmail())) {
            throw new ConflictException("El email '" + request.getEmail() + "' ya está registrado");
        }

        if (!request.getDni().equals(clientEntity.getDni()) && clientRepository.existsByDni(request.getDni())) {
            throw new ConflictException("El DNI '" + request.getDni() + "' ya está registrado");
        }

        clientEntity.update(request);

        var updatedClient = clientRepository.save(clientEntity);

        var clientResponse = modelMapper.map(updatedClient, ClientResponseDto.class);

        return new ApiResponse<>("Cliente actualizado", EStatus.SUCCESS, clientResponse);
    }

    @Override
    public ApiResponse<ClientResponseDto> getClientById(Long clientId) {
        var client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el cliente con id " + clientId));

        var clientResponse = modelMapper.map(client, ClientResponseDto.class);

        return new ApiResponse<>("Cliente encontrado", EStatus.SUCCESS, clientResponse);
    }

    @Override
    public ApiResponse<ClientResponseDto> deleteClient(Long clientId) {
        var client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el cliente con id " + clientId));

        clientRepository.delete(client);

        return new ApiResponse<>("Cliente eliminado", EStatus.SUCCESS, null);
    }

    @Override
    public ApiResponse<List<ClientResponseDto>> getAllClients() {
        var clients = clientRepository.findAll();

        if (clients.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron clientes");
        }

        var clientResponse = modelMapper.map(clients, List.class);

        return new ApiResponse<>("Clientes encontrados", EStatus.SUCCESS, clientResponse);
    }

    @Override
    public ApiResponse<List<ClientResponseDto>> getClientsByCompanyId(Long companyId) {
        var clients = clientRepository.findByCompanyId(companyId);

        if (clients.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron clientes para la empresa con id " + companyId);
        }

        var clientResponse = modelMapper.map(clients, List.class);

        return new ApiResponse<>("Clientes encontrados", EStatus.SUCCESS, clientResponse);
    }
}
