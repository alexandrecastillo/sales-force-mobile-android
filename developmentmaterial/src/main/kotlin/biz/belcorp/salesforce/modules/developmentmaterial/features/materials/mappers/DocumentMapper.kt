package biz.belcorp.salesforce.modules.developmentmaterial.features.materials.mappers

import biz.belcorp.salesforce.modules.developmentmaterial.core.domain.entities.DocumentoMaterialDesarrollo
import biz.belcorp.salesforce.modules.developmentmaterial.features.materials.model.DocumentModel


class DocumentMapper {

    fun map(list: List<DocumentoMaterialDesarrollo>): List<DocumentModel> {
        return list.asSequence().map { map(it) }.toList()
    }

    private fun map(documento: DocumentoMaterialDesarrollo): DocumentModel {
        return DocumentModel(
            id = documento.id,
            titulo = documento.titulo ?: "",
            url = documento.url ?: "",
            urlValido = documento.urlEsValido
        )
    }


}
