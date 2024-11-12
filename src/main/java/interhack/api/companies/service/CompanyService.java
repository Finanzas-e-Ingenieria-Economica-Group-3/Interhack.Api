package interhack.api.companies.service;

import interhack.api.shared.dto.enums.EStatus;
import interhack.api.shared.dto.response.ApiResponse;
import interhack.api.shared.exception.ResourceNotFoundException;
import interhack.api.companies.model.dto.CompanyResponseDto;
import interhack.api.companies.repository.ICompanyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

/**
 * Servicio para usuarios
 */
@Service
public class CompanyService implements ICompanyService {
    private final ICompanyRepository companyRepository;
    private final ModelMapper modelMapper;

    public CompanyService(ICompanyRepository companyRepository, ModelMapper modelMapper) {
        this.companyRepository = companyRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ApiResponse<CompanyResponseDto> profile(Long companyId) {
       var user = companyRepository.findById(companyId)
               .orElseThrow(() -> new ResourceNotFoundException("No se encontró la empresa con id " + companyId));

       //se mapea el usuario a un DTO
       var userDto = modelMapper.map(user, CompanyResponseDto.class);

       return new ApiResponse<>("Empresa encontrado", EStatus.SUCCESS, userDto);
    }

    @Override
    public ApiResponse<Object> deleteById(Long companyId) {
        var user = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la empresa con id " + companyId));

        //elimina al usuario
        companyRepository.delete(user);

        return new ApiResponse<>("Empresa eliminada correctamente", EStatus.SUCCESS, null);
    }

}
