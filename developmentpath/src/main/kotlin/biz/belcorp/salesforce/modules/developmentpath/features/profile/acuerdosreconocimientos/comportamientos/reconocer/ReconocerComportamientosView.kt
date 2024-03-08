package biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.comportamientos.reconocer

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.ConsultoraRdd

interface ReconocerComportamientosView {
    fun mostrarDatosBasicosPersona(nombreCompleto: String)
    fun mostrarDatosSocia(nivelProductividad: String, exito: Boolean)
    fun mostrarDatosConsultora(segmento: String, tipo: ConsultoraRdd.Tipo)
    fun pintarReconocimientos(modelos: List<ReconocimientoModel>)
    fun pintarRazonEnBoton(numerador: Int, denominador: Int)
    fun ocultarDialogoPrincipal()
    fun mostrarDialogoExito(alCerrarCallback: () -> Unit)
    fun cerrarDialogoPrincipal()
    fun notificarCambioenReconocimiento()
}
