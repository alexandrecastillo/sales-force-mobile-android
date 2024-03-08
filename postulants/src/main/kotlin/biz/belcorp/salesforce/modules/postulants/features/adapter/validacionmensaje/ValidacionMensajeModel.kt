package biz.belcorp.salesforce.modules.postulants.features.adapter.validacionmensaje

data class ValidacionMensajeModel(
    val title: Text,
    val subtitle: Text,
    val description: Text
) {

    data class Text(
        val value: String? = null,
        val isVisible: Boolean,
        val color: Int? = null
    )

}
