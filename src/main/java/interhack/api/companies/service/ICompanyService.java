package interhack.api.companies.service;

import interhack.api.shared.dto.response.ApiResponse;
import interhack.api.companies.model.dto.CompanyResponseDto;


public interface ICompanyService {
    /**
     * Obtiene los datos de un usuario por su id
     * @param companyId id de la compañia
     * @return Datos del usuario
     */
    ApiResponse<CompanyResponseDto> profile(Long companyId);

    /**
     * Elimina un usuario por su id
     * @param companyId id de la compañia
     * @return Respuesta de la operación
     */
    ApiResponse<Object> deleteById(Long companyId);

    ApiResponse<CompanyResponseDto> getCompanyByEmail(String email);
}
