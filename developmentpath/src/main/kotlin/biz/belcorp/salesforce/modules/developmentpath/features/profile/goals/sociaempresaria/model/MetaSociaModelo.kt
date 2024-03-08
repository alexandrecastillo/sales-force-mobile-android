package biz.belcorp.salesforce.modules.developmentpath.features.profile.goals.sociaempresaria.model

import java.util.*

data class MetaSociaModelo(
    var id: Long,
    var descripcion: String,
    var fecha: Date
) {

    val esValido: Boolean get() = esDescripcionValida()

    private fun esDescripcionValida() = descripcion.isNotBlank()

    fun esModoEdicion() = id > MODO_EDICION

    fun crearFecha() {
        fecha = Date()
    }

    companion object {
        private const val MODO_EDICION = 0L

        fun crearModelo() = MetaSociaModelo(
            id = MODO_EDICION,
            descripcion = "",
            fecha = Date()
        )
    }
}
