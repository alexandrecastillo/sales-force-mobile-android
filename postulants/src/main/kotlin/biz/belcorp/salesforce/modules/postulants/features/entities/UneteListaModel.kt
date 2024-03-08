package biz.belcorp.salesforce.modules.postulants.features.entities

import biz.belcorp.salesforce.modules.postulants.utils.Constant

class UneteListaModel {
    var id: Int? = null
    var descripcion: String? = null
    var descripcionAnalitycs: String = Constant.EMPTY_STRING

    override fun toString() = descripcion.orEmpty()
}
