package interhack.api.banking.services;

import interhack.api.banking.models.dtos.responses.BankResponse;
import interhack.api.banking.repositories.IBankRepository;
import interhack.api.shared.dto.enums.EStatus;
import interhack.api.shared.dto.response.ApiResponse;
import interhack.api.shared.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BankService implements IBankService {
    private final IBankRepository bankRepository;
    private final ModelMapper modelMapper;

    public BankService(IBankRepository bankRepository, ModelMapper modelMapper) {
        this.bankRepository = bankRepository;
        this.modelMapper = modelMapper;
    }

    public ApiResponse<List<BankResponse>> getBanks() {
        var banks = bankRepository.findAll();

        var banksResponse = banks.stream()
                .map(bank -> modelMapper.map(bank, BankResponse.class))
                .collect(Collectors.toList());

        return new ApiResponse<>("Bancos encontrados", EStatus.SUCCESS, banksResponse);
    }

    public ApiResponse<BankResponse> getBankById(Long bankId) {
        var bank = bankRepository.findById(bankId)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el banco con id " + bankId));

        var bankResponse = modelMapper.map(bank, BankResponse.class);

        return new ApiResponse<>("Banco encontrado", EStatus.SUCCESS, bankResponse);
    }
}
