package biz.belcorp.salesforce.modules.developmentpath.features.profile.cobranzaganancia

import biz.belcorp.salesforce.modules.developmentpath.widgets.etiqueta.model.ContenedorInfoBasica

interface DatosCobranzaGananciaContract {
    interface View {
        fun mostrarProgreso()

        fun ocultarProgreso(model: CobranzaYGananciaModel)

        fun pintarContenedorInfo(modelo: ContenedorInfoBasica, model: CobranzaYGananciaModel)

        fun cobranzaGananciaError()
    }
}
