package biz.belcorp.salesforce.modules.postulants.features.indicador.components.listado.dialog

interface OnRechazoInteractionListener {
    fun postulanteRechazada() = Unit

    fun postulanteProactivaRechazada(motivo: String) = Unit
}
