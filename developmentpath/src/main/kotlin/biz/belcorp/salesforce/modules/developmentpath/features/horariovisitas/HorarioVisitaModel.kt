package biz.belcorp.salesforce.modules.developmentpath.features.horariovisitas

data class HorarioVisitaModel(
    var horarioVisitaId: Int = 0,
    var descripcion: String? = null,
    var horaInicio: String? = null,
    var horaFin: String? = null,
    var orden: Int = 0,
    var otroHorario: Boolean = false,
    var activo: Boolean = false,
    var seleccionado: Boolean = false
)
