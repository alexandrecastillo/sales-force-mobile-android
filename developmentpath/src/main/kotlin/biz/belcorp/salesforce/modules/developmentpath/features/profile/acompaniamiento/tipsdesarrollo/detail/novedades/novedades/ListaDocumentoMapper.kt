package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.novedades.novedades

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.novedades.Documento
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.novedades.documents.DocumentoModel

class ListaDocumentoMapper {

    fun map(incentivos: List<Documento>): List<DocumentoModel> {
        return incentivos.map { parse(it) }
    }

    private fun parse(documento: Documento): DocumentoModel {
        return DocumentoModel(
            documento.titulo,
            documento.nombreDocumento,
            documento.urlDocumento
        )
    }
}
