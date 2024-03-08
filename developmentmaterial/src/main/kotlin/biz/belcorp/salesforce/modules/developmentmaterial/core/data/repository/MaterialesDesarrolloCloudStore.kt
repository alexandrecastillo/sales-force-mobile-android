package biz.belcorp.salesforce.modules.developmentmaterial.core.data.repository

import biz.belcorp.salesforce.core.data.repository.auth.SafeApiCallHelper
import biz.belcorp.salesforce.core.entities.sql.materialdesarrollo.MaterialDesarrolloEntity
import biz.belcorp.salesforce.modules.developmentmaterial.core.data.network.DocumentsApi


class MaterialesDesarrolloCloudStore(
    private val documentsApi: DocumentsApi,
    private val apiCallHelper: SafeApiCallHelper
) {

    suspend fun downloadDocuments(rol: String): List<MaterialDesarrolloEntity> {
        return apiCallHelper.safeLegacyApiCall {
            documentsApi.download(rol)
        }?.resultado ?: emptyList()
    }

}
