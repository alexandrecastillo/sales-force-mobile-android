package biz.belcorp.salesforce.modules.postulants.features.entities

class FiltroAprobadoUneteModel {
    var id: Int = 0
    var description: String? = null
    var campaign: Int? = 0

    override fun toString() = description.orEmpty()

}
