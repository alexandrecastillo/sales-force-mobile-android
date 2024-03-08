package biz.belcorp.salesforce.modules.consultants.core.domain.entities

open class Consultora {

    companion object {
        const val ID_NO_ASIGNADO = -1
    }

    var consultorasId: Int = ID_NO_ASIGNADO
    var zona: String? = null
    var seccion: String? = null
    var nombre: String? = null
    var codigo: String? = null
    var telefonoCasa: String? = null
    var telefonoCelular: String? = null
    var constancia: String? = null
    var segmento: String? = null
    var saldoPendiente: String? = null
    var primerNombre: String? = null
    var segundoNombre: String? = null
    var primerApellido: String? = null
    var segundoApellido: String? = null
    var latitud: Double? = null
    var longitud: Double? = null
    var nivel: String? = null
    var digitoVerificador: String? = null
    var nivelInt: Int? = 0

    var monto: String? = null
    var nivelActual: String? = null
    var nivelSiguiente: String? = null

}

fun String?.inicial(): String {
    var inicial = ""
    this?.let {
        if (this.length > 1) {
            inicial = this.subSequence(0, 1).toString().toUpperCase()
        }
    }
    return inicial
}
