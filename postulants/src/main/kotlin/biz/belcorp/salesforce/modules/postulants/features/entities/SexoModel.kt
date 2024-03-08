package biz.belcorp.salesforce.modules.postulants.features.entities

class SexoModel {
    var codigo: String? = null
    var descripcion: String? = null

    override fun toString() = descripcion.orEmpty()
}
