package interhack.api.banking.services;

import interhack.api.banking.models.dtos.responses.BankResponse;
import interhack.api.shared.dto.response.ApiResponse;

import java.util.List;

public interface IBankService {
    ApiResponse<List<BankResponse>> getBanks();
    ApiResponse<BankResponse> getBankById(Long bankId);
}
