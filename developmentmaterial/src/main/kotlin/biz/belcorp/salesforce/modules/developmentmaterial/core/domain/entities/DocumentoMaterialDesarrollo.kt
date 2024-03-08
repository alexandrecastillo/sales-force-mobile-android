package biz.belcorp.salesforce.modules.developmentmaterial.core.domain.entities

data class DocumentoMaterialDesarrollo(
    val id: Long,
    val titulo: String?,
    val url: String?
) {

    val urlEsValido get() = url != null
    val urlValidado get() = checkNotNull(url)

}
