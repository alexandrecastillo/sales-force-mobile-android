package biz.belcorp.salesforce.modules.developmentpath.features.horariovisitas

import biz.belcorp.salesforce.core.constants.Constant

data class HorarioVisitaConsultoraModel(
    var horarioVisitaId: Int = 0,
    var consultoraId: Long = 0,
    var region: String = Constant.EMPTY_STRING,
    var zona: String = Constant.EMPTY_STRING,
    var seccion: String = Constant.EMPTY_STRING,
    var activo: Boolean = false,
    var enviado: Boolean = false
)
