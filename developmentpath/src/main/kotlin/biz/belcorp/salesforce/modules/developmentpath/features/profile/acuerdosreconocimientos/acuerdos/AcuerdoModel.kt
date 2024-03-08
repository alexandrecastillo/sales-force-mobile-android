package biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.acuerdos

class AcuerdoModel(
    val id: Long,
    var descripcion: String,
    val fecha: String,
    var mostrarEditarAcuerdo: Boolean,
    var mostrarEliminarAcuerdo: Boolean,
    var mostrarGuardarAcuerdo: Boolean,
    var mostrarCancelar: Boolean,
    var mostrarTextoAcuerdo: Boolean,
    var mostrarFechaAcuerdo: Boolean,
    var mostrarEdicionTextoAcuerdo: Boolean,
    var mostrarLineaInferior: Boolean
)
