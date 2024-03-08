package biz.belcorp.salesforce.modules.orders.core.domain.entities.filters

import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.search.TipoEstado
import biz.belcorp.salesforce.core.domain.entities.search.TipoSegmento
import biz.belcorp.salesforce.core.domain.entities.ua.Seccion


data class FiltrosBusqueda(
    var tipoEstadoList: List<TipoEstado>,
    var tipoSaldoList: List<TipoSaldo>,
    var tipoSegmentoList: List<TipoSegmento>,
    var seccionesList: List<Seccion>,
    var campaniasList: List<Campania>
)
