package biz.belcorp.salesforce.modules.orders.features.results.mapper

import biz.belcorp.salesforce.modules.orders.core.domain.entities.orders.FiltroPedidosWeb
import biz.belcorp.salesforce.modules.orders.features.search.model.FiltroPedidosWebModel


class FiltroPedidosWebMapper {

    fun map(model: FiltroPedidosWebModel): FiltroPedidosWeb {
        return FiltroPedidosWeb().apply {
            tipoFiltro = model.tipoFiltro
            pais = model.pais
            campania = model.campania
            consultoraLiderId = model.consultoraLiderId
            seccion = model.seccion
            estadoPedido = model.estadoPedido
            fechaDesde = model.fechaDesde
            fechaHasta = model.fechaHasta
            codigoConsultora = model.codigoConsultora
            nombreConsultora = model.nombreConsultora
            segmentoId = model.segmentoId
            deudaId = model.deudaId
            origen = model.origen
        }
    }


}
