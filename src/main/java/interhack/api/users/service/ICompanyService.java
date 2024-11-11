package interhack.api.users.service;

import interhack.api.shared.dto.response.ApiResponse;
import interhack.api.users.model.dto.CompanyResponseDto;
import interhack.api.users.model.entity.Company;


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

}
