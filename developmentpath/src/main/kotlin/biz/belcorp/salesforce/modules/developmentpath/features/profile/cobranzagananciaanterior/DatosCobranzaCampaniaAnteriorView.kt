package biz.belcorp.salesforce.modules.developmentpath.features.profile.cobranzagananciaanterior

import biz.belcorp.salesforce.modules.developmentpath.features.profile.data.gz.CobranzaCampaniaAnteriorModel

interface DatosCobranzaCampaniaAnteriorView {

    fun pintarFechaSincronizacion(fecha: String)
    fun pintarCampaniaAnterior(campaniaAnterior: String)
    fun pintarDatos(datos: CobranzaCampaniaAnteriorModel)
}
