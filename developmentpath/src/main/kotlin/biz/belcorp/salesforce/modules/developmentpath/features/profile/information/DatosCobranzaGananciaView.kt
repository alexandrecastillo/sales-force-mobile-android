package biz.belcorp.salesforce.modules.developmentpath.features.profile.information

import biz.belcorp.salesforce.modules.developmentpath.features.profile.cobranzaganancia.CobranzaYGananciaModel

interface DatosCobranzaGananciaView {
    fun pintarDatosCobranzaGanancia(modelo: CobranzaYGananciaModel)
    fun cobranzaGananciaError()
}
