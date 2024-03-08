package biz.belcorp.salesforce.modules.postulants.core.domain.entities.crediticio

class ValidacionCrediticiaExterna {

    var esApta: Boolean = false
    var enumEstadoCrediticio: Int = 1
    var bloqueosExternos: Boolean? = null
    var mensaje: String = ""
    var explicaciones: List<String> = emptyList()
    var requiereAprobacionSAC: Boolean = false

    var primerNombre: String = ""
    var segundoNombre: String = ""
    var primerApellido: String = ""
    var segundoApellido: String = ""
    var fechaNacimiento: String = ""

    var respuestaServicio: String = ""

}
