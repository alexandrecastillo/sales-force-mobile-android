package biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso2.entities

import biz.belcorp.salesforce.modules.postulants.utils.Constant

class PlaceModel(
    val id: String = Constant.EMPTY_STRING,
    val descripcion: String = Constant.EMPTY_STRING
) {
    override fun toString() = descripcion
}
