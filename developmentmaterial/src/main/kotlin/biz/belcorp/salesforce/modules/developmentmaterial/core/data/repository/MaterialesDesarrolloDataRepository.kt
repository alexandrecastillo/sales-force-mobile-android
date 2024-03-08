package biz.belcorp.salesforce.modules.developmentmaterial.core.data.repository

import biz.belcorp.salesforce.modules.developmentmaterial.core.domain.entities.DocumentoMaterialDesarrollo
import biz.belcorp.salesforce.modules.developmentmaterial.core.domain.repository.MaterialesDesarrolloRepository


class MaterialesDesarrolloDataRepository(
    private val materialesDesarrolloDataStore: MaterialesDesarrolloDataStore,
    private val materialesDesarrolloCloudStore: MaterialesDesarrolloCloudStore,
    private val materialesDesarrolloMapper: MaterialesDesarrolloMapper
) : MaterialesDesarrolloRepository {

    override suspend fun getDocuments(): List<DocumentoMaterialDesarrollo> {
        val list = materialesDesarrolloDataStore.getDocuments()
        return materialesDesarrolloMapper.map(list)
    }

    override suspend fun syncDocuments(rol: String) {
        val list = materialesDesarrolloCloudStore.downloadDocuments(rol)
        materialesDesarrolloDataStore.saveDocuments(list)
    }

}
