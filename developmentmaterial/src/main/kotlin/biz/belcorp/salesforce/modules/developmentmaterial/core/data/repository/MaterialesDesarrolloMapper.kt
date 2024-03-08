package biz.belcorp.salesforce.modules.developmentmaterial.core.data.repository

import biz.belcorp.salesforce.core.entities.sql.materialdesarrollo.MaterialDesarrolloEntity
import biz.belcorp.salesforce.modules.developmentmaterial.core.domain.entities.DocumentoMaterialDesarrollo


class MaterialesDesarrolloMapper {

    fun map(list: List<MaterialDesarrolloEntity>): List<DocumentoMaterialDesarrollo> {
        return list.asSequence().mapNotNull { map(it) }.toList()
    }

    private fun map(modelo: MaterialDesarrolloEntity): DocumentoMaterialDesarrollo? {
        return DocumentoMaterialDesarrollo(
            id = modelo.id ?: return null,
            titulo = modelo.nombreDocumento,
            url = modelo.url
        )
    }


}
