package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo

class TipsNegocio<T>(
    var personaId: Long = 0L,
    var region: String = "-",
    var zona: String = "-",
    var seccion: String = "-",
    var opcion: String = "",
    var data: T?
)
