package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.novedades.documents

interface ClickEnArchivoListener {
    fun clickEnCompartir(archivo: DocumentoModel)
    fun clickEnVerPDF(archivo: DocumentoModel)
    fun clickEnDescargarPDF(archivo: DocumentoModel)
}
