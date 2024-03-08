package biz.belcorp.salesforce.modules.postulants.features.entities

class TablaLogicaModel {
    var tablaLogicaDatosID: Int = 0
    var tablaLogicaID: Int = 0
    var codigo: String? = null
    var descripcion: String? = null

    override fun toString() = descripcion.orEmpty()
}
