package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.focos

class DetalleFoco(
    val campania: String,
    val region: String?,
    val zona: String?,
    val seccion: String?,
    val usuario: String?,
    val focos: Array<Long>
)
