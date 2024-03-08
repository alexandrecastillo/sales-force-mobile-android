package biz.belcorp.salesforce.modules.developmentmaterial.core.domain.usecases

import biz.belcorp.salesforce.modules.developmentmaterial.core.domain.entities.DocumentoMaterialDesarrollo
import biz.belcorp.salesforce.modules.developmentmaterial.core.domain.repository.MaterialesDesarrolloRepository

class GetDocumentsUseCase(
    private val materialesDesarrolloRepository: MaterialesDesarrolloRepository
) {

    suspend fun get(): List<DocumentoMaterialDesarrollo> {
        return materialesDesarrolloRepository.getDocuments()
    }

}
