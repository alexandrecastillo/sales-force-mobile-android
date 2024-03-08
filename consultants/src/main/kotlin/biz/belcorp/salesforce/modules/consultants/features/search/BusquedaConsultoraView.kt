package biz.belcorp.salesforce.modules.consultants.features.search

import biz.belcorp.salesforce.core.features.search.models.TipoEstadoModel
import biz.belcorp.salesforce.core.features.search.models.TipoSaldoModel
import biz.belcorp.salesforce.core.features.search.models.TipoSegmentoModel
import biz.belcorp.salesforce.modules.consultants.features.search.models.*


interface BusquedaConsultoraView {

    fun mostrarCampaniaVenta(nombreCorto: String)

    fun mostrarCampaniaFacturacion(nombreCorto: String)

    fun mostrarFiltroSeccion(items: List<SeccionModel>)

    fun mostrarFiltroEstados(items: List<TipoEstadoModel>)

    fun mostrarFiltroPedidos(items: List<TipoPedidoModel>)

    fun mostrarFiltroAutorizado(items: List<TipoAutorizadoModel>)

    fun mostrarFiltroSaldos(items: List<TipoSaldoModel>)

    fun mostrarFiltroSegmentos(items: List<TipoSegmentoModel>)

    fun mostrarFiltroTipo(items: List<TipoPaymentModel>)

    fun ocultarFiltroSeccion()

    fun mostrarMensajeError(id: Int)

    fun limpiarMensajeError()

    fun mostrarMensajeResultadosGZ(count: Int)

    fun mostrarMensajeResultadosSE(count: Int)

    fun mostrarResultados()

}
