package biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.comportamientos.actual.detalle

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.ConsultoraRdd
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.comportamientos.reconocer.ReconocimientoModel

interface ReconocimientoCampaniaActualView {
    fun mostrarDatosBasicosPersona(nombreCompleto: String)
    fun mostrarDatosSocia(nivelProductividad: String, exito: Boolean)
    fun mostrarDatosConsultora(segmento: String, tipo: ConsultoraRdd.Tipo)
    fun pintarReconocimientos(modelos: List<ReconocimientoModel>)
}
