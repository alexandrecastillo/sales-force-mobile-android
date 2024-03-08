package biz.belcorp.salesforce.modules.developmentpath.features.profile.cobranzaestadodecuenta.cobranza

import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.widgets.etiqueta.model.ContenedorInfoBasica

interface DatosCobranzaContract {
    interface View {
        fun pintarContenedorInfo(modelo: ContenedorInfoBasica)
    }

    interface Presenter {
        fun obtener(personaId: Long, personaRol: Rol)
    }
}
