package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.novedades.documents

interface DocumentoView {
    fun pintarDocumentos(data: List<DocumentoModel>)
    fun pintarDocumentosVacio()
}
