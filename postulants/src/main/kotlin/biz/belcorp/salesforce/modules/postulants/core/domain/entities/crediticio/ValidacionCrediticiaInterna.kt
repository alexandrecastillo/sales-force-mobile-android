package biz.belcorp.salesforce.modules.postulants.core.domain.entities.crediticio

class ValidacionCrediticiaInterna {

    var deudaBelcorp: String? = null
    var motivoBloqueo: String? = null
    var valoracionBelcorp: String? = null
    var bloqueosInternos: Boolean? = null
    var bloqueos: List<Bloqueo>? = null

}
