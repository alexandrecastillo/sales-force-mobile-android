package biz.belcorp.salesforce.modules.developmentmaterial.core.domain.repository

import biz.belcorp.salesforce.modules.developmentmaterial.core.domain.entities.DocumentoMaterialDesarrollo


interface MaterialesDesarrolloRepository {

    suspend fun getDocuments(): List<DocumentoMaterialDesarrollo>

    suspend fun syncDocuments(rol: String)

}
